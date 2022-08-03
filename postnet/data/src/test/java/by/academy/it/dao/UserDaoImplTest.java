package by.academy.it.dao;

import by.academy.it.pojo.User;
import by.academy.it.pojo.UserDetails;
import by.academy.it.pojo.UserJob;
import by.academy.it.util.HibernateUtilTest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Component
public class UserDaoImplTest extends HibernateUtilTest {

    UserDaoImpl testUserDao = new UserDaoImpl();
    User testUser = new User();
    UserJob testUserJob = new UserJob();
    UserDetails testUserDetails = new UserDetails();

    @Before
    public void setUp() {
        testUserDao.sessionFactory = HibernateUtilTest.sessionFactory;

        testUser.setEmail("test@test.test");
        testUser.setPassword("Test1234");
        testUser.setName("testName");
        testUser.setSurname("testSurname");
        testUser.setGender("женский");

        testUserJob.setPostoffice("Минск-50, Главпочтамт");
        testUserJob.setRole("почтальон");
        testUser.setUserJob(testUserJob);

        testUserDetails.setBirthday(new java.sql.Date(594667996870L));
        testUserDetails.setAbout("test user");
        testUserDetails.setHobby("testing");
        testUser.setUserDetails(testUserDetails);

    }

    @BeforeClass
    public static void beforeClass() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/postnet-test",
                    "root", "root");
            Statement statement = connection.createStatement();

            String query = "SET FOREIGN_KEY_CHECKS = 0";
            statement.executeUpdate(query);
            query = "TRUNCATE TABLE user";
            statement.executeUpdate(query);
            query = "TRUNCATE TABLE user_job";
            statement.executeUpdate(query);
            query = "TRUNCATE TABLE user_details";
            statement.executeUpdate(query);
            query = "SET FOREIGN_KEY_CHECKS = 1";
            statement.executeUpdate(query);

            query = "INSERT IGNORE INTO user (email, name, gender, surname, password) VALUES " +
                    "('test1@test.test', 'TestName1', 'женский', 'TestSurname1', 'Test1234'), " +
                    "('test2@test.test', 'TestName2', 'мужской', 'TestSurname2', 'Test1234'), " +
                    "('test3@test.test', 'ТестИмя3', 'женский', 'ТестФамилия3', 'Test1234'), " +
                    "('test4@test.test', 'ТестИмя4', 'мужской', 'ТестФамилия4', 'Test1234') ";
            statement.executeUpdate(query);

            query = "INSERT IGNORE INTO user_job (postoffice, role) VALUES " +
                    "('Минск-1', 'почтальон'), " +
                    "('Минск-2', 'оператор связи'), " +
                    "('Минск Пункт выдачи 4', 'специалист по почтовой деятельности'), " +
                    "('Минск Бизнес-почта 120', 'специалист по почтовой деятельности') ";
            statement.executeUpdate(query);

            query = "INSERT IGNORE INTO user_details (birthday, about, hobby) VALUES " +
                    "('1984-01-18', 'testabout1', 'testhobby1'), " +
                    "('1990-12-08', 'testabout2', 'testhobby2'), " +
                    "('1989-05-31', 'testabout3', 'testhobby3'), " +
                    "('2001-08-11', 'testabout4', 'testhobby4') ";
            statement.executeUpdate(query);

            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void setUserTest() {
        testUserDao.setUser(testUser);
        User gotUser = testUserDao.getUserByEmail("test@test.test");
        assertEquals("testName", gotUser.getName());
        testUserDao.deleteUser(5);
    }

    @Test
    public void getUserTest() {
        User gotUser = testUserDao.getUser(3);
        assertEquals("ТестФамилия3", gotUser.getSurname());
    }

    @Test
    public void getAllUsersTest() {
        List<User> testUsers = testUserDao.getAllUsers();
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
        User gotUser = testUserDao.getUser(1);
        gotUser.setGender("мужской");
        testUserDao.updateUser(gotUser);

        assertEquals("мужской", testUserDao.getUser(1).getGender());
    }

    @Test
    public void updateUserJobTest() {
        User gotUser = testUserDao.getUser(2);
        UserJob gotUserJob = gotUser.getUserJob();
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
        testUser.setEmail("тест@тест.тест");
        testUserDao.setUser(testUser);

        assertNotNull(testUserDao.getUser(6));

        testUserDao.deleteUser(6);

        assertNull(testUserDao.getUser(6));
    }

    @AfterClass
    public static void afterClass() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/postnet-test",
                    "root", "root");
            Statement statement = connection.createStatement();

            String query = "SET FOREIGN_KEY_CHECKS = 0";
            statement.executeUpdate(query);
            query = "TRUNCATE TABLE user";
            statement.executeUpdate(query);
            query = "TRUNCATE TABLE user_job";
            statement.executeUpdate(query);
            query = "TRUNCATE TABLE user_details";
            statement.executeUpdate(query);
            query = "SET FOREIGN_KEY_CHECKS = 1";
            statement.executeUpdate(query);

            connection.close();
        } catch (
                SQLException exception) {
            exception.printStackTrace();
        }
    }
}
