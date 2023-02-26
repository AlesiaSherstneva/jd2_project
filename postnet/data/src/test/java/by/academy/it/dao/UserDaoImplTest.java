package by.academy.it.dao;

import by.academy.it.config.TestSpringConfig;
import by.academy.it.pojo.User;
import by.academy.it.pojo.UserDetails;
import by.academy.it.pojo.UserJob;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringConfig.class)
@Transactional
public class UserDaoImplTest {
    @Autowired
    private SessionFactory sessionFactory;

    private UserDao userDao;

    @Before
    public void setUp() {
        userDao = new UserDaoImpl(sessionFactory);
    }

    @Test
    public void setUserTest() {
        // given
        User testUser = new User("Testname", "Testsurname", "женский",
                "test@test.test", "Test1234");
        testUser.setUserJob(new UserJob("Минск-50, Главпочтамт", "почтальон"));
        testUser.setUserDetails(new UserDetails(new Date(987654321000L),
                "test user", "testing"));
        // when
        userDao.setUser(testUser);
        // then
        User receivedUser = userDao.getUserByEmail(testUser.getEmail());
        assertSame(testUser, receivedUser);
    }

    @Test
    public void getUserTest() {
        // given, when
        User gotUser = userDao.getUser(3);
        // then
        assertEquals("ТестФамилия", gotUser.getSurname());
    }

    @Test
    public void getAllUsersTest() {
        // given, when
        List<User> testUsers = new ArrayList<>(userDao.getAllUsers());
        // then
        assertEquals(4, testUsers.size());
    }

    @Test
    public void getUserByEmailTest() {
        // given, when
        User gotUser = userDao.getUserByEmail("test1@test.test");
        // then
        assertEquals("TestName", gotUser.getName());
    }

    @Test
    public void searchUsersTest() {
        // given, when
        List<User> usersPattern = new ArrayList<>(userDao.searchUsers("Test"));
        List<User> upperPattern = new ArrayList<>(userDao.searchUsers("БИЗНЕС"));
        List<User> lowerPattern = userDao.searchUsers("спец");
        // then
        assertEquals(2, usersPattern.size());
        assertEquals("ТестИмя", upperPattern.get(0).getName());
        assertEquals(2, lowerPattern.size());
    }

    @Test
    public void pageUsersTest() {
        // given, when
        List<User> firstPageUsers = new ArrayList<>(userDao.pageUsers("", 0));
        List<User> emptySecondPage = userDao.pageUsers("Test", 1);
        // then
        assertEquals(3, firstPageUsers.size());
        assertEquals(0, emptySecondPage.size());
    }

    @Test
    public void updateUserTest() {
        // given
        User receivedUser = userDao.getUserByEmail("test3@test.test");
        assertEquals("женский", receivedUser.getGender());
        // when
        receivedUser.setGender("мужской");
        userDao.updateUser(receivedUser);
        // then
        receivedUser = userDao.getUserByEmail("test3@test.test");
        assertEquals("мужской", receivedUser.getGender());
    }

    @Test
    public void updateUserJobTest() {
        // given
        UserJob receivedUserJob = userDao.getUser(2).getUserJob();
        assertEquals("Минск-2", receivedUserJob.getPostoffice());
        assertEquals("оператор связи", receivedUserJob.getRole());
        // when
        receivedUserJob.setPostoffice("Минск-4");
        receivedUserJob.setRole("почтальон");
        userDao.updateUserJob(receivedUserJob);
        // then
        receivedUserJob = userDao.getUser(2).getUserJob();
        assertEquals("Минск-4", receivedUserJob.getPostoffice());
        assertEquals("почтальон", receivedUserJob.getRole());
    }

    @Test
    public void updateUserDetailsTest() {
        // given
        UserDetails receivedUserDetails = userDao.getUser(1).getUserDetails();
        assertEquals("testabout1", receivedUserDetails.getAbout());
        assertEquals("testhobby1", receivedUserDetails.getHobby());
        // when
        receivedUserDetails.setAbout("another test about");
        receivedUserDetails.setHobby("another test hobby");
        userDao.updateUserDetails(receivedUserDetails);
        // then
        receivedUserDetails = userDao.getUser(1).getUserDetails();
        assertEquals("another test about", receivedUserDetails.getAbout());
        assertEquals("another test hobby", receivedUserDetails.getHobby());
    }

    @Test
    public void updateUserStatusTest() {
        // given
        User receivedUser = userDao.getUser(2);
        assertEquals(1, (byte) receivedUser.getEnabled());
        // when
        userDao.updateUserStatus((byte) 0, 2);
        // then
        assertEquals(0, (byte) receivedUser.getEnabled());
    }

    @Test
    public void deleteUserTest() {
        // given
        User receivedUser = userDao.getUser(4);
        assertNotNull(receivedUser);
        // when
        userDao.deleteUser(4);
        // then
        receivedUser = userDao.getUser(4);
        assertNull(receivedUser);
    }
}