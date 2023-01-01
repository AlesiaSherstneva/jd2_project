package by.academy.it.services;

import by.academy.it.dao.UserDao;
import by.academy.it.pojo.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class AdminServiceTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private AdminService adminService;

    private User testUser;
    private List<User> testUserList;
    private byte enabled;

    @Before
    public void setUp() {
        adminService = new AdminService(userDao);

        testUser = new User();
        testUser.setId(25);

        testUserList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            testUserList.add(new User());
        }
    }

    @Test
    public void getAllUsersTest() {
        when(userDao.getAllUsers()).thenReturn(testUserList);
        assertNotNull(adminService.getAllUsers());
        assertEquals(5, adminService.getAllUsers().size());
        assertSame(testUserList, adminService.getAllUsers());
        verify(userDao, times(3)).getAllUsers();
    }

    @Test
    public void banUserTest() {
        adminService.banUser(testUser.getId());
        verify(userDao, times(1)).updateUserStatus(enabled, testUser.getId());
    }

    @Test
    public void unbanUserTest() {
        enabled = 1;
        adminService.unbanUser(testUser.getId());
        verify(userDao, times(1)).updateUserStatus(enabled, testUser.getId());
    }

    @Test
    public void deleteUserTest() {
        for (int i = 1; i <= 10; i++) {
            adminService.deleteUser(i);
        }
        verify(userDao, times(10)).deleteUser(anyInt());
    }
}