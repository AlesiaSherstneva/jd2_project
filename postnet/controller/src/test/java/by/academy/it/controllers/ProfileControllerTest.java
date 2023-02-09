package by.academy.it.controllers;

import by.academy.it.pojo.User;
import by.academy.it.pojo.UserDetails;
import by.academy.it.pojo.UserJob;
import by.academy.it.util.TestControllerInit;
import by.academy.it.validators.UniqueEmailValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@Import(UniqueEmailValidator.class)
public class ProfileControllerTest extends TestControllerInit {
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProfileController(userService, mock(UniqueEmailValidator.class)))
                .setViewResolvers(viewResolver)
                .build();

        testUser = new User("Nametest", "Surnametest", "женский",
                "somename@mail.com", "1234asdF");
        testUserJob = new UserJob("Минск-50 Главпочтамт", "оператор связи");
        testUserDetails = new UserDetails(new Date(), "test", "test");
    }

    @Test
    public void firstEditTest() throws Exception {
        when(principal.getName()).thenReturn("somename@mail.com");
        when(userService.changeUsersPassword(anyString())).thenReturn(testUser);
        mockMvc.perform(get("/edit-1")
                        .principal(principal))
                .andExpectAll(
                        model().size(1),
                        model().attribute("user", testUser),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/registered/edit-1.jsp"),
                        redirectedUrl(null)
                );
        verify(userService, times(1)).changeUsersPassword(anyString());
        verify(principal, times(2)).getName();
    }

    @Test
    public void editEmptyUserTest() throws Exception {
        User emptyUser = new User();
        mockMvc.perform(post("/confirm-1")
                        .flashAttr("user", emptyUser)
                        .principal(principal))
                .andExpectAll(
                        model().size(1),
                        model().attribute("user", emptyUser),
                        model().attributeErrorCount("user", 5),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/registered/edit-1.jsp"),
                        redirectedUrl(null)
                );
        verify(principal, times(1)).getName();
    }

    @Test
    public void editNotValidUserTest() throws Exception {
        testUser.setName("wrong name");
        testUser.setSurname("1234pass");
        mockMvc.perform(post("/confirm-1")
                        .flashAttr("user", testUser)
                        .principal(principal))
                .andExpectAll(
                        model().size(1),
                        model().attribute("user", testUser),
                        model().attributeErrorCount("user", 2),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/registered/edit-1.jsp"),
                        redirectedUrl(null)
                );
        verify(principal, times(1)).getName();
    }

    @Test
    public void editUserEmailTest() throws Exception {
        when(principal.getName()).thenReturn("anothername@mail.com");
        mockMvc.perform(post("/confirm-1")
                        .flashAttr("user", testUser)
                        .principal(principal))
                .andExpectAll(
                        model().size(1),
                        model().attribute("user", testUser),
                        model().attributeHasNoErrors("user"),
                        status().is3xxRedirection(),
                        forwardedUrl(null),
                        redirectedUrl("/login?logout")
                );
        verify(userService, times(1)).updateUser(any(User.class));
        verify(principal, times(2)).getName();
    }

    @Test
    public void firstConfirmTest() throws Exception {
        when(principal.getName()).thenReturn("somename@mail.com");
        mockMvc.perform(post("/confirm-1")
                        .flashAttr("user", testUser)
                        .principal(principal))
                .andExpectAll(
                        model().size(1),
                        model().attribute("user", testUser),
                        model().attributeHasNoErrors("user"),
                        status().is3xxRedirection(),
                        forwardedUrl(null),
                        redirectedUrl("/")
                );
        verify(userService, times(1)).updateUser(any(User.class));
        verify(principal, times(2)).getName();
    }

    @Test
    public void secondEditTest() throws Exception {
        when(principal.getName()).thenReturn("somename@mail.com");
        when(userService.getUserJob(anyString())).thenReturn(testUserJob);
        mockMvc.perform(get("/edit-2")
                        .principal(principal))
                .andExpectAll(
                        model().size(1),
                        model().attribute("userjob", testUserJob),
                        model().attributeHasNoErrors("userjob"),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/registered/edit-2.jsp")
                );
        verify(userService, times(1)).getUserJob(anyString());
        verify(principal, times(2)).getName();
    }

    @Test
    public void editEmptyUserJobTest() throws Exception {
        UserJob emptyUserJob = new UserJob();
        mockMvc.perform(patch("/confirm-2")
                        .flashAttr("userjob", emptyUserJob)
                        .principal(principal))
                .andExpectAll(
                        model().size(1),
                        model().attribute("userjob", emptyUserJob),
                        model().attributeErrorCount("userjob", 2),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/registered/edit-2.jsp"),
                        redirectedUrl(null)
                );
        verify(principal, times(1)).getName();
    }

    @Test
    public void secondConfirmTest() throws Exception {
        when(principal.getName()).thenReturn("somename@mail.com");
        when(userService.getUserByEmail(anyString())).thenReturn(testUser);
        assertNull(testUserJob.getUser());
        mockMvc.perform(patch("/confirm-2")
                        .flashAttr("userjob", testUserJob)
                        .principal(principal))
                .andExpectAll(
                        model().size(1),
                        model().attribute("userjob", testUserJob),
                        model().attributeHasNoErrors("userjob"),
                        status().is3xxRedirection(),
                        forwardedUrl(null),
                        redirectedUrl("/")
                );
        assertEquals(testUser, testUserJob.getUser());
        verify(userService, times(1)).getUserByEmail(anyString());
        verify(userService, times(1)).updateUserJob(any(UserJob.class));
        verify(principal, times(2)).getName();
    }

    @Test
    public void thirdEditTest() throws Exception {
        when(principal.getName()).thenReturn("somename@mail.com");
        when(userService.getUserDetails(anyString())).thenReturn(testUserDetails);
        mockMvc.perform(get("/edit-3")
                        .principal(principal))
                .andExpectAll(
                        model().size(1),
                        model().attribute("userdetails", testUserDetails),
                        model().attributeHasNoErrors("userdetails"),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/registered/edit-3.jsp")
                );
        verify(userService, times(1)).getUserDetails(anyString());
        verify(principal, times(2)).getName();
    }

    @Test
    public void editEmptyUserDetailsTest() throws Exception {
        UserDetails emptyUserDetails = new UserDetails();
        mockMvc.perform(patch("/confirm-3")
                        .flashAttr("userdetails", emptyUserDetails)
                        .principal(principal))
                .andExpectAll(
                        model().size(1),
                        model().attribute("userdetails", emptyUserDetails),
                        model().attributeErrorCount("userdetails", 1),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/registered/edit-3.jsp"),
                        redirectedUrl(null)
                );
        verify(principal, times(1)).getName();
    }

    @Test
    public void thirdConfirmTest() throws Exception {
        when(principal.getName()).thenReturn("somename@mail.com");
        when(userService.getUserByEmail(anyString())).thenReturn(testUser);
        assertNull(testUserDetails.getUser());
        mockMvc.perform(patch("/confirm-3")
                        .flashAttr("userdetails", testUserDetails)
                        .principal(principal))
                .andExpectAll(
                        model().size(1),
                        model().attribute("userdetails", testUserDetails),
                        model().attributeHasNoErrors("userdetails"),
                        status().is3xxRedirection(),
                        forwardedUrl(null),
                        redirectedUrl("/")
                );
        assertEquals(testUser, testUserDetails.getUser());
        verify(userService, times(1)).getUserByEmail(anyString());
        verify(userService, times(1)).updateUserDetails(any(UserDetails.class));
        verify(principal, times(2)).getName();
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(userService, principal);
    }
}