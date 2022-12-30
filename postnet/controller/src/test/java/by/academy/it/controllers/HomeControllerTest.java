package by.academy.it.controllers;

import by.academy.it.config.WebAppConfig;
import by.academy.it.services.AdminService;
import by.academy.it.services.UserService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class)
/*@DirtiesContext*/
public class HomeControllerTest {
    private static MockMvc mockMvc;

    @Mock
    private static UserService userService;

    @Mock
    private static AdminService adminService;

    @BeforeClass
    public static void beforeClass() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(new HomeController(userService, adminService))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void login() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/login.jsp"));
    }

    @Test
    public void profile() {
    }
}