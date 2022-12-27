package by.academy.it.dao;

import by.academy.it.config.TestSpringConfig;
import by.academy.it.pojo.User;
import by.academy.it.pojo.UserDetails;
import by.academy.it.pojo.UserJob;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringConfig.class)
@Transactional
@FixMethodOrder(MethodSorters.JVM)
public class UserDaoImplTest {
    @Autowired
    private SessionFactory sessionFactory;

    private UserDao testUserDao;

    @Before
    public void setUp() {
        testUserDao = new UserDaoImpl(sessionFactory);
    }

    @Test
    public void setUserTest() {
        User testUser = new User("testName", "testSurname", "женский", "test@test.test", "Test1234");
        testUser.setUserJob(new UserJob("Минск-50, Главпочтамт", "почтальон"));
        testUser.setUserDetails(new UserDetails(new java.sql.Date(594667996870L), "test user", "testing"));
        testUserDao.setUser(testUser);

        User gotUser = testUserDao.getUserByEmail(testUser.getEmail());
        assertNotNull(gotUser);
        assertSame(testUser, gotUser);
    }

    @Test
    public void getUserTest() {
        User gotUser = testUserDao.getUser(3);
        assertEquals("ТестФамилия3", gotUser.getSurname());
    }

    @Test
    public void getAllUsersTest() {
        List<User> testUsers = new ArrayList<>(testUserDao.getAllUsers());
        assertEquals(4, testUsers.size());
    }

    @Test
    public void getUserByEmailTest() {
        User gotUser = testUserDao.getUserByEmail("test1@test.test");
        assertEquals("TestName1", gotUser.getName());
    }

    @Test
    public void searchUsersTest() {
        List<User> users = new ArrayList<>(testUserDao.searchUsers("Test"));
        assertEquals(2, users.size());

        users = testUserDao.searchUsers("БИЗНЕС");
        assertEquals("ТестИмя4", users.get(0).getName());

        users = testUserDao.searchUsers("спец");
        assertEquals(2, users.size());
    }

    @Test
    public void pageUsersTest() {
        List<User> users = new ArrayList<>(testUserDao.pageUsers("", 0));
        assertEquals(3, users.size());

        users = testUserDao.pageUsers("Test", 1);
        assertEquals(0, users.size());
    }

    @Test
    public void updateUserTest() {
        User gotUser = testUserDao.getUserByEmail("test3@test.test");
        assertNotNull(gotUser);
        assertEquals("женский", gotUser.getGender());

        gotUser.setGender("мужской");
        testUserDao.updateUser(gotUser);

        gotUser = testUserDao.getUserByEmail("test3@test.test");
        assertNotNull(gotUser);
        assertEquals("мужской", gotUser.getGender());
    }

    @Test
    public void updateUserJobTest() {
        User gotUser = testUserDao.getUser(2);
        UserJob gotUserJob = gotUser.getUserJob();
        assertEquals("оператор связи", gotUserJob.getRole());

        gotUserJob.setRole("почтальон");
        testUserDao.updateUserJob(gotUserJob);
        assertEquals("почтальон", testUserDao.getUser(2).getUserJob().getRole());
    }

    @Test
    public void updateUserDetailsTest() {
        User gotUser = testUserDao.getUser(1);
        UserDetails gotUserDetails = gotUser.getUserDetails();
        gotUserDetails.setHobby("Меняю пол без хирургического вмешательства");
        testUserDao.updateUserDetails(gotUserDetails);
        assertEquals("Меняю пол без хирургического вмешательства",
                testUserDao.getUser(1).getUserDetails().getHobby());
    }

    @Test
    public void updateUserStatusTest() {
        testUserDao.updateUserStatus((byte) 0, 2);
        User gotUser = testUserDao.getUser(2);
        assertEquals(0, (byte) gotUser.getEnabled());
    }

    @Test
    public void deleteUserTest() {
        testUserDao.deleteUser(4);
        assertNull(testUserDao.getUser(4));
        assertEquals(3, testUserDao.getAllUsers().size());
    }
}