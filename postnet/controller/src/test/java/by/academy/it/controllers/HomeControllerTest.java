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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest extends TestControllerInit {
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new HomeController(userService, adminService))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpectAll(
                        model().size(0),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/login.jsp"),
                        redirectedUrl(null)
                );
    }

    @Test
    public void userProfileTest() throws Exception {
        when(principal.getName()).thenReturn("Some Name");
        when(userService.getUserByEmail(anyString())).thenReturn(testUser);

        mockMvc.perform(get("/")
                        .principal(principal))
                .andExpectAll(
                        model().size(1),
                        model().attribute("user", testUser),
                        model().attributeDoesNotExist("users"),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/registered/user-profile.jsp"),
                        redirectedUrl(null)
                );

        verify(userService, times(1)).getUserByEmail(anyString());
        verify(principal, times(3)).getName();
    }

    @Test
    public void adminProfileTest() throws Exception {
        List<User> testUsers = new ArrayList<>();
        when(principal.getName()).thenReturn("admin");
        when(adminService.getAllUsers()).thenReturn(testUsers);

        mockMvc.perform(get("/")
                        .principal(principal))
                .andExpectAll(
                        model().size(1),
                        model().attribute("users", testUsers),
                        model().attributeDoesNotExist("user"),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/administrate/admin-page.jsp"),
                        redirectedUrl(null)
                );

        verify(adminService, times(1)).getAllUsers();
        verify(principal, times(2)).getName();
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(userService, adminService, principal);
    }
}