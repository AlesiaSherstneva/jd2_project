package by.academy.it.services;

import by.academy.it.dao.UserDao;
import by.academy.it.pojo.User;
import by.academy.it.pojo.UserDetails;
import by.academy.it.pojo.UserJob;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

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
    public void registerUserWithEmptyDetailsTest() {
        // given
        testUser.setUserDetails(testUserDetails);
        // when
        userService.registerUser(testUser);
        // then
        assertEquals("не указано", testUser.getUserDetails().getAbout());
        assertEquals("не указано", testUser.getUserDetails().getHobby());
        verify(userDao, times(1)).setUser(testUser);
    }

    @Test
    public void registerUserWithNotEmptyDetailsTest() {
        // given
        testUserDetails.setAbout("test about");
        testUserDetails.setHobby("test hobby");
        testUser.setUserDetails(testUserDetails);
        // when
        userService.registerUser(testUser);
        // then
        assertNotEquals("не указано", testUser.getUserDetails().getAbout());
        assertNotEquals("не указано", testUser.getUserDetails().getHobby());
        verify(userDao, times(1)).setUser(testUser);
    }

    @Test
    public void getUserByIdTest() {
        // given
        when(userDao.getUser(anyInt())).thenReturn(testUser);
        // when
        User receivedUser = userService.getUserById(new Random().nextInt(100));
        for (int i = 1; i <= 5; i++) {
            userService.getUserById(i);
        }
        // then
        assertSame(testUser, receivedUser);
        verify(userDao, times(6)).getUser(anyInt());
    }

    @Test
    public void getUserByEmailTest() {
        // given
        testUser.setEmail("test@test.test");
        when(userDao.getUserByEmail(anyString())).thenReturn(testUser);
        // when
        User receivedUser = userService.getUserByEmail(anyString());
        // then
        assertEquals("test@test.test", receivedUser.getEmail());
        verify(userDao, times(1)).getUserByEmail(anyString());
    }

    @Test
    public void changeUsersPasswordTest() {
        // given
        testUser.setPassword("{noop}password");
        when(userDao.getUserByEmail(anyString())).thenReturn(testUser);
        // when
        User receivedUser = userService.changeUsersPassword(anyString());
        // then
        assertEquals("password", receivedUser.getPassword());
        verify(userDao, times(1)).getUserByEmail(anyString());
    }

    @Test
    public void updateUserTest() {
        // given
        testUser.setPassword("password");
        // when
        userService.updateUser(testUser);
        // then
        assertEquals("{noop}password", testUser.getPassword());
        verify(userDao, times(1)).updateUser(any(User.class));
    }

    @Test
    public void getUserJobTest() {
        // given
        testUserJob.setPostoffice("220136");
        testUserJob.setRole("оператор связи");
        testUser.setUserJob(testUserJob);
        when(userDao.getUserByEmail(anyString())).thenReturn(testUser);
        // when
        UserJob receivedUserJob = userService.getUserJob(anyString());
        // then
        assertEquals(testUserJob.getPostoffice(), receivedUserJob.getPostoffice());
        assertEquals(testUserJob.getRole(), receivedUserJob.getRole());
        assertEquals(testUserJob.getUser(), receivedUserJob.getUser());
        verify(userDao, times(1)).getUserByEmail(anyString());
    }

    @Test
    public void updateUserJobTest() {
        // given, when
        for(int i = 0; i < 5; i++) {
            userService.updateUserJob(new UserJob());
        }
        // then
        verify(userDao, times(5)).updateUserJob(any(UserJob.class));
    }

    @Test
    public void getUserWithEmptyDetailsTest() {
        // given
        testUserDetails.setAbout("не указано");
        testUserDetails.setHobby("не указано");
        testUser.setUserDetails(testUserDetails);
        when(userDao.getUserByEmail(anyString())).thenReturn(testUser);
        // when
        UserDetails receivedUserDetails = userService.getUserDetails(anyString());
        // then
        assertEquals("", receivedUserDetails.getAbout());
        assertEquals("", receivedUserDetails.getHobby());
        verify(userDao, times(1)).getUserByEmail(anyString());
    }

    @Test
    public void getUserWithNotEmptyDetailsTest() {
        // given
        testUserDetails.setAbout("test about");
        testUserDetails.setHobby("test hobby");
        testUser.setUserDetails(testUserDetails);
        when(userDao.getUserByEmail(anyString())).thenReturn(testUser);
        // when
        UserDetails receivedUserDetails = userService.getUserDetails(anyString());
        // then
        assertNotEquals("", receivedUserDetails.getAbout());
        assertNotEquals("", receivedUserDetails.getHobby());
        verify(userDao, times(1)).getUserByEmail(anyString());
    }

    @Test
    public void updateUserWithEmptyDetailsTest() {
        // given, when
        userService.updateUserDetails(testUserDetails);
        // then
        assertEquals("не указано", testUserDetails.getAbout());
        assertEquals("не указано", testUserDetails.getHobby());
        verify(userDao, times(1)).updateUserDetails(any(UserDetails.class));
    }

    @Test
    public void updateUserWithNotEmptyDetailsTest() {
        // given
        testUserDetails.setAbout("test about");
        testUserDetails.setHobby("test hobby");
        // when
        userService.updateUserDetails(testUserDetails);
        // then
        assertNotEquals("не указано", testUserDetails.getAbout());
        assertNotEquals("не указано", testUserDetails.getHobby());
        verify(userDao, times(1)).updateUserDetails(any(UserDetails.class));
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(userDao);
    }
}