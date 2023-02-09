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

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@Import(UniqueEmailValidator.class)
public class UserControllerTest extends TestControllerInit {
    private UserController userController;

    @Before
    public void setUp() {
        userController = new UserController(userService, mock(UniqueEmailValidator.class));
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setViewResolvers(viewResolver)
                .build();

        testUser = new User("Testname", "Testsurname", "мужской",
                "test@test.test", "Qwerty12");
    }

    @Test
    public void firstRegisterTest() throws Exception {
        mockMvc.perform(get("/register-step-1")
                        .flashAttr("user", testUser))
                .andExpectAll(
                        model().attribute("user", testUser),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/unregistered/register-step-1.jsp")
                );
    }

    @Test
    public void registerEmptyUserTest() throws Exception {
        User emptyUser = new User();
        mockMvc.perform(post("/register-step-2")
                        .flashAttr("user", emptyUser))
                .andExpectAll(
                        model().size(1),
                        model().attributeExists("user"),
                        model().attributeErrorCount("user", 5),
                        model().attributeDoesNotExist("userjob"),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/unregistered/register-step-1.jsp")
                );
        assertNotSame(userController.getRegisteredUser(), emptyUser);
    }

    @Test
    public void registerUserWithWrongNamesTest() throws Exception {
        testUser.setName("wRonGnaMe");
        testUser.setSurname("Wr0ngsurname");
        mockMvc.perform(post("/register-step-2")
                        .flashAttr("user", testUser))
                .andExpectAll(
                        model().size(1),
                        model().attribute("user", testUser),
                        model().attributeErrorCount("user", 2),
                        model().attributeDoesNotExist("userjob"),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/unregistered/register-step-1.jsp")
                );
        assertNotSame(userController.getRegisteredUser(), testUser);
    }

    @Test
    public void registerUserWithWrongPassportTest() throws Exception {
        testUser.setPassword("wrongPassword");
        mockMvc.perform(post("/register-step-2")
                        .flashAttr("user", testUser))
                .andExpectAll(
                        model().size(1),
                        model().attribute("user", testUser),
                        model().attributeErrorCount("user", 1),
                        model().attributeDoesNotExist("userjob"),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/unregistered/register-step-1.jsp")
                );
        assertNotSame(userController.getRegisteredUser(), testUser);
    }

    @Test
    public void secondRegisterTest() throws Exception {
        mockMvc.perform(post("/register-step-2")
                        .flashAttr("user", testUser))
                .andExpectAll(
                        model().size(2),
                        model().attribute("user", testUser),
                        model().attributeHasNoErrors("user"),
                        model().attributeExists("userjob"),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/unregistered/register-step-2.jsp")
                );
        assertSame(userController.getRegisteredUser(), testUser);
    }

    @Test
    public void registerEmptyUserJobTest() throws Exception {
        UserJob emptyUserJob = new UserJob();
        mockMvc.perform(post("/register-step-3")
                        .flashAttr("userjob", emptyUserJob))
                .andExpectAll(
                        model().size(1),
                        model().attribute("userjob", emptyUserJob),
                        model().attributeErrorCount("userjob", 2),
                        model().attributeDoesNotExist("userdetails"),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/unregistered/register-step-2.jsp")
                );
        assertNotSame(userController.getRegisteredUser().getUserJob(), emptyUserJob);
    }

    @Test
    public void thirdRegisterTest() throws Exception {
        testUserJob = new UserJob();
        testUserJob.setPostoffice("Минск-48");
        testUserJob.setRole("оператор связи");

        mockMvc.perform(post("/register-step-3")
                        .flashAttr("userjob", testUserJob))
                .andExpectAll(
                        model().size(2),
                        model().attribute("userjob", testUserJob),
                        model().attributeHasNoErrors("userjob"),
                        model().attributeExists("userdetails"),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/unregistered/register-step-3.jsp")
                );
        assertSame(userController.getRegisteredUser().getUserJob(), testUserJob);
    }

    @Test
    public void registerEmptyUserDetailsTest() throws Exception {
        UserDetails emptyUserDetails = new UserDetails();
        mockMvc.perform(post("/confirm")
                        .flashAttr("userdetails", emptyUserDetails))
                .andExpectAll(
                        model().size(1),
                        model().attribute("userdetails", emptyUserDetails),
                        model().attributeErrorCount("userdetails", 1),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/unregistered/register-step-3.jsp")
                );
        assertNotSame(userController.getRegisteredUser().getUserDetails(), emptyUserDetails);
    }

    @Test
    public void finishRegisterTest() throws Exception {
        testUserDetails = new UserDetails();
        testUserDetails.setBirthday(new Date());
        mockMvc.perform(post("/confirm")
                        .flashAttr("userdetails", testUserDetails))
                .andExpectAll(
                        model().size(2),
                        model().attribute("userdetails", testUserDetails),
                        model().attributeHasNoErrors("userdetails"),
                        model().attributeExists("user"),
                        status().isOk(),
                        forwardedUrl("/WEB-INF/view/unregistered/confirm.jsp")
                );
        assertSame(userController.getRegisteredUser().getUserDetails(), testUserDetails);
        verify(userService, times(1)).registerUser(any(User.class));
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(userService);
    }
}