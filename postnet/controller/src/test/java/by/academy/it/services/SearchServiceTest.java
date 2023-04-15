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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private SearchService searchService;

    private List<User> testUsers;

    @Before
    public void setUp() {
        testUsers = new ArrayList<>();
        for (int i = 0; i < 7; i++) testUsers.add(new User());
    }

    @Test
    public void pagesCountWithEmptyStringTest() {
        // given
        when(userDao.searchUsers(anyString())).thenReturn(new ArrayList<>());
        when(userDao.getAllUsers()).thenReturn(testUsers);
        // when
        int pagedUsersSize = searchService.pagesCount(anyString());
        // then
        assertEquals(7, pagedUsersSize);
        verify(userDao, times(1)).searchUsers(anyString());
        verify(userDao, times(1)).getAllUsers();
    }

    @Test
    public void pagesCountWithNotEmptyStringTest() {
        // given
        testUsers.add(new User());
        when(userDao.searchUsers(anyString())).thenReturn(testUsers);
        // when
        int pagedUsersSize = searchService.pagesCount(anyString());
        // then
        assertEquals(8, pagedUsersSize);
        verify(userDao, times(1)).searchUsers(anyString());
    }

    @Test
    public void pageUsersTest() {
        // given
        when(userDao.pageUsers(anyString(), anyInt())).thenReturn(testUsers);
        // when
        List<User> pagedUsers = searchService.pageUsers(anyString(), anyInt());
        // then
        assertSame(testUsers, pagedUsers);
        verify(userDao, times(1)).pageUsers(anyString(), anyInt());
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(userDao);
    }
}