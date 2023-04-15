package by.academy.it.controllers;

import by.academy.it.pojo.User;
import by.academy.it.util.TestControllerInit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchControllerTest extends TestControllerInit {
    private Random random;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new SearchController(userService, searchService))
                .setViewResolvers(viewResolver)
                .build();

        random = new Random();
        testUser = new User();
    }

    @Test
    public void showUsersTest() throws Exception {
        int pagesCount = random.nextInt(100), page = random.nextInt(100);
        List<User> testUsers = new ArrayList<>();
        when(searchService.pagesCount(anyString())).thenReturn(pagesCount);
        when(searchService.pageUsers(anyString(), anyInt())).thenReturn(testUsers);

        mockMvc.perform(get("/users/{page}", page)
                        .param("searchString", anyString()))
                .andExpectAll(
                        model().size(5),
                        model().attribute("startpage", 1),
                        model().attribute("endpage", Math.ceil((double) pagesCount / 3)),
                        model().attributeExists("search"),
                        model().attribute("currentpage", page),
                        model().attribute("users", testUsers),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/search/search-all.jsp"),
                        redirectedUrl(null)
                );

        verify(searchService, times(1)).pagesCount(anyString());
        verify(searchService, times(1)).pageUsers(anyString(), anyInt());
    }

    @Test
    public void showOneUserToHimselfTest() throws Exception {
        int id = random.nextInt(20);
        testUser.setId(id);
        when(principal.getName()).thenReturn("somename@mail.com");
        when(userService.getUserById(anyInt())).thenReturn(testUser);
        when(userService.getUserByEmail(anyString())).thenReturn(testUser);

        mockMvc.perform(get("/users/user/{id}", id)
                        .principal(principal))
                .andExpectAll(
                        model().size(1),
                        model().attribute("user", testUser),
                        status().is3xxRedirection(),
                        forwardedUrl(null),
                        redirectedUrl("/")
                );

        verify(principal, times(3)).getName();
        verify(userService, times(1)).getUserById(anyInt());
        verify(userService, times(1)).getUserByEmail(anyString());
    }

    @Test
    public void showOneUserToAnotherUserTest() throws Exception {
        int id = random.nextInt(20);
        testUser.setId(id + 5);
        when(principal.getName()).thenReturn("somename@mail.com");
        when(userService.getUserById(anyInt())).thenReturn(testUser);
        when(userService.getUserByEmail(anyString())).thenReturn(testUser);

        mockMvc.perform(get("/users/user/{id}", id)
                        .principal(principal))
                .andExpectAll(
                        model().size(1),
                        model().attribute("user", testUser),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/search/search-one.jsp"),
                        redirectedUrl(null)
                );

        verify(principal, times(3)).getName();
        verify(userService, times(1)).getUserById(anyInt());
        verify(userService, times(1)).getUserByEmail(anyString());
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(userService, searchService, principal);
    }
}