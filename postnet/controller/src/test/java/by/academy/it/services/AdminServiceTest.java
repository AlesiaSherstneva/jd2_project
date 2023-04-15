package by.academy.it.services;

import by.academy.it.dao.UserDao;
import by.academy.it.pojo.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private AdminService adminService;

    private User testUser;
    private List<User> testUsers;

    @Before
    public void setUp() {
        testUser = new User();
        testUser.setId(new Random().nextInt(100));

        testUsers = new ArrayList<>();
    }

    @Test
    public void getAllUsersTest() {
        // given
        for (int i = 0; i < 5; i++) testUsers.add(new User());
        when(userDao.getAllUsers()).thenReturn(testUsers);
        // when
        List<User> receivedUsers = adminService.getAllUsers();
        // then
        assertEquals(5, receivedUsers.size());
        assertSame(testUsers, receivedUsers);
        verify(userDao, times(1)).getAllUsers();
    }

    @Test
    public void banUserTest() {
        // given, when
        adminService.banUser(testUser.getId());
        // then
        verify(userDao, times(1)).updateUserStatus((byte) 0, testUser.getId());
    }

    @Test
    public void unbanUserTest() {
        // given, when
        adminService.unbanUser(testUser.getId());
        // then
        verify(userDao, times(1)).updateUserStatus((byte) 1, testUser.getId());
    }

    @Test
    public void deleteUserTest() {
        // given, when
        for (int i = 1; i <= 10; i++) adminService.deleteUser(i);
        // then
        verify(userDao, times(10)).deleteUser(anyInt());
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(userDao);
    }
}