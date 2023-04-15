package by.academy.it.controllers;

import by.academy.it.util.TestControllerInit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Random;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest extends TestControllerInit {
    private Random random;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AdminController(adminService))
                .setViewResolvers(viewResolver)
                .build();
        random = new Random();
    }

    @Test
    public void banUser() throws Exception {
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(get("/admin/ban")
                            .param("id", String.valueOf(random.nextInt(1000))))
                    .andExpectAll(
                            model().size(0),
                            status().is3xxRedirection(),
                            forwardedUrl(null),
                            redirectedUrl("/")
                    );
        }
        verify(adminService, times(3)).banUser(anyInt());
    }

    @Test
    public void unbanUser() throws Exception {
        for (int i = 0; i < 4; i++) {
            mockMvc.perform(get("/admin/unban")
                            .param("id", String.valueOf(random.nextInt(1000))))
                    .andExpectAll(
                            model().size(0),
                            status().is3xxRedirection(),
                            forwardedUrl(null),
                            redirectedUrl("/")
                    );
        }
        verify(adminService, times(4)).unbanUser(anyInt());
    }

    @Test
    public void deleteUser() throws Exception {
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(get("/admin/delete")
                            .param("id", String.valueOf(random.nextInt(1000))))
                    .andExpectAll(
                            model().size(0),
                            status().is3xxRedirection(),
                            forwardedUrl(null),
                            redirectedUrl("/")
                    );
        }
        verify(adminService, times(5)).deleteUser(anyInt());
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(adminService);
    }
}