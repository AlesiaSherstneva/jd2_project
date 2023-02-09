package by.academy.it.util;

import by.academy.it.pojo.User;
import by.academy.it.pojo.UserDetails;
import by.academy.it.pojo.UserJob;
import by.academy.it.services.AdminService;
import by.academy.it.services.SearchService;
import by.academy.it.services.UserService;
import by.academy.it.validators.UniqueEmailValidator;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.security.Principal;

public class TestControllerInit {
    protected MockMvc mockMvc;
    protected ViewResolver viewResolver;

    @Mock
    protected UserService userService;

    @Mock
    protected AdminService adminService;

    @Mock
    protected SearchService searchService;

    @Mock
    protected Principal principal;

    protected User testUser;
    protected UserJob testUserJob;
    protected UserDetails testUserDetails;

    protected TestControllerInit() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view");
        viewResolver.setSuffix(".jsp");
        this.viewResolver = viewResolver;
    }
}