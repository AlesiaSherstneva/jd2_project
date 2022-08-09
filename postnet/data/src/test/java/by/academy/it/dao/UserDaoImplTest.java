package by.academy.it.dao;

import by.academy.it.pojo.User;
import by.academy.it.pojo.UserDetails;
import by.academy.it.pojo.UserJob;
import by.academy.it.pojo.UserMapper;
import by.academy.it.util.HibernateUtilTest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserDaoImplTest extends HibernateUtilTest {

    UserDaoImpl testUserDao = new UserDaoImpl();
    User testUser;
    UserJob testUserJob;
    UserDetails testUserDetails;

    public static JdbcTemplate jdbcTemplate;

    static {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/postnet-test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Before
    public void setUp() {
        testUserDao.sessionFactory = HibernateUtilTest.sessionFactory;

        testUser = new User("testName", "testSurname", "женский", "test@test.test", "Test1234");

        testUserJob = new UserJob("Минск-50, Главпочтамт", "почтальон");
        testUser.setUserJob(testUserJob);

        testUserDetails = new UserDetails(new java.sql.Date(594667996870L), "test user", "testing");
        testUser.setUserDetails(testUserDetails);
    }

    @BeforeClass
    public static void beforeClass() {
        jdbcTemplate.execute("TRUNCATE TABLE user");
        jdbcTemplate.execute("TRUNCATE TABLE user_job");
        jdbcTemplate.execute("TRUNCATE TABLE user_details");

        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");

        jdbcTemplate.update("INSERT IGNORE INTO user (name, surname, gender, email, password) VALUES " +
                "('TestName1', 'TestSurname1', 'женский', 'test1@test.test', 'Test1234'), " +
                "('TestName2', 'TestSurname2', 'мужской', 'test2@test.test', 'Test1234'), " +
                "('ТестИмя3', 'ТестФамилия3', 'женский', 'test3@test.test', 'Test1234'), " +
                "('ТестИмя4', 'ТестФамилия4', 'мужской', 'test4@test.test', 'Test1234') ");

        jdbcTemplate.update("INSERT IGNORE INTO user_job (postoffice, role) VALUES " +
                "('Минск-1', 'почтальон'), " +
                "('Минск-2', 'оператор связи'), " +
                "('Минск Пункт выдачи 4', 'специалист по почтовой деятельности'), " +
                "('Минск Бизнес-почта 120', 'специалист по почтовой деятельности') ");

        jdbcTemplate.update("INSERT IGNORE INTO user_details (birthday, about, hobby) VALUES " +
                "('1984-01-18', 'testabout1', 'testhobby1'), " +
                "('1990-12-08', 'testabout2', 'testhobby2'), " +
                "('1989-05-31', 'testabout3', 'testhobby3'), " +
                "('2001-08-11', 'testabout4', 'testhobby4') ");
    }

    @Test
    public void setUserTest() {
        testUserDao.setUser(testUser);

        User gotUser = jdbcTemplate.queryForObject("SELECT * FROM user WHERE email = ?",
                new BeanPropertyRowMapper<>(User.class), "test@test.test");
        assertNotNull(gotUser);
        assertEquals("testName", gotUser.getName());

        jdbcTemplate.update("DELETE FROM user WHERE id = ?", gotUser.getId());
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
        User gotUser = jdbcTemplate.queryForObject("SELECT * FROM user WHERE id = ?", new UserMapper(), 1);
        assertNotNull(gotUser);
        assertEquals("женский", gotUser.getGender());

        gotUser.setGender("мужской");
        testUserDao.updateUser(gotUser);

        gotUser = jdbcTemplate.queryForObject("SELECT * FROM user WHERE id = ?", new UserMapper(), 1);
        assertNotNull(gotUser);
        assertEquals("мужской", gotUser.getGender());
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
        testUserDao.setUser(testUser);

        assertNotNull(testUserDao.getUser(6));

        testUserDao.deleteUser(6);

        assertNull(testUserDao.getUser(6));
    }

    @AfterClass
    public static void afterClass() {
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");

        jdbcTemplate.execute("TRUNCATE TABLE user");
        jdbcTemplate.execute("TRUNCATE TABLE user_job");
        jdbcTemplate.execute("TRUNCATE TABLE user_details");

        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
    }
}
