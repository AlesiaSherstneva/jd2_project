package by.academy.it.services;

import by.academy.it.dao.UserDao;
import by.academy.it.pojo.User;
import by.academy.it.pojo.UserDetails;
import by.academy.it.pojo.UserJob;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserJob testUserJob;
    private UserDetails testUserDetails;

    @Before
    public void setUp() {
        testUser = new User();
        testUserJob = new UserJob();
        testUserDetails = new UserDetails();
    }

    @Test
    public void registerUser() {
        testUser.setUserDetails(new UserDetails());
        userService.registerUser(testUser);
        assertEquals("не указано", testUser.getUserDetails().getAbout());
        assertEquals("не указано", testUser.getUserDetails().getHobby());

        testUserDetails.setAbout("test about");
        testUserDetails.setHobby("test hobby");
        testUser.setUserDetails(testUserDetails);
        userService.registerUser(testUser);
        assertNotEquals("не указано", testUser.getUserDetails().getAbout());
        assertNotEquals("не указано", testUser.getUserDetails().getHobby());

        verify(userDao, times(2)).setUser(any(User.class));
    }

    @Test
    public void getUserById() {
        when(userDao.getUser(anyInt())).thenReturn(testUser);
        assertSame(testUser, userService.getUserById(100));
        for (int i = 1; i <= 15; i++) {
            userService.getUserById(i);
        }
        verify(userDao, times(16)).getUser(anyInt());
    }

    @Test
    public void getUserByEmail() {
        when(userDao.getUserByEmail(anyString())).thenReturn(testUser);
        assertNull(userService.getUserByEmail(anyString()).getEmail());

        testUser.setEmail("test@test.test");
        assertEquals("test@test.test", userService.getUserByEmail(anyString()).getEmail());

        verify(userDao, times(2)).getUserByEmail(anyString());
    }

    @Test
    public void changeUsersPassword() {
        when(userDao.getUserByEmail(anyString())).thenReturn(testUser);
        assertThrows(NullPointerException.class, () -> userService.changeUsersPassword(anyString()));

        testUser.setPassword("{noop}password");
        assertEquals("password", userService.changeUsersPassword(anyString()).getPassword());

        verify(userDao, times(2)).getUserByEmail(anyString());
    }

    @Test
    public void updateUser() {
        userService.updateUser(testUser);
        assertEquals("{noop}null", testUser.getPassword());

        testUser.setPassword("password");
        userService.updateUser(testUser);
        assertEquals("{noop}password", testUser.getPassword());

        verify(userDao, times(2)).updateUser(any(User.class));
    }

    @Test
    public void getUserJob() {
        when(userDao.getUserByEmail(anyString())).thenReturn(testUser);
        assertNull(userService.getUserJob(anyString()));

        testUser.setUserJob(testUserJob);
        assertNotNull(userService.getUserJob(anyString()));
        assertEquals(testUserJob, userService.getUserJob(anyString()));

        verify(userDao, times(3)).getUserByEmail(anyString());
    }

    @Test
    public void updateUserJob() {
        for(int i = 0; i < 5; i++) {
            userService.updateUserJob(new UserJob());
        }
        verify(userDao, times(5)).updateUserJob(any(UserJob.class));
    }

    @Test
    public void getUserDetails() {
        when(userDao.getUserByEmail(anyString())).thenReturn(testUser);
        assertThrows(NullPointerException.class, () -> userService.getUserDetails(anyString()));

        testUserDetails.setAbout("не указано");
        testUserDetails.setHobby("не указано");
        testUser.setUserDetails(testUserDetails);
        assertEquals("", userService.getUserDetails(anyString()).getAbout());
        assertEquals("", userService.getUserDetails(anyString()).getHobby());

        testUserDetails.setAbout("test about");
        testUserDetails.setHobby("test hobby");
        testUser.setUserDetails(testUserDetails);
        assertNotEquals("", userService.getUserDetails(anyString()).getAbout());
        assertNotEquals("", userService.getUserDetails(anyString()).getHobby());

        verify(userDao, times(5)).getUserByEmail(anyString());
    }

    @Test
    public void updateUserDetails() {
        userService.updateUserDetails(testUserDetails);
        assertEquals("не указано", testUserDetails.getAbout());
        assertEquals("не указано", testUserDetails.getHobby());

        testUserDetails.setAbout("test about");
        testUserDetails.setHobby("test hobby");
        userService.updateUserDetails(testUserDetails);
        assertNotEquals("не указано", testUserDetails.getAbout());
        assertNotEquals("не указано", testUserDetails.getHobby());

        verify(userDao, times(2)).updateUserDetails(any(UserDetails.class));
    }
}