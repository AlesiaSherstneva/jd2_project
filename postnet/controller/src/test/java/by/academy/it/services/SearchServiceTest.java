package by.academy.it.services;

import by.academy.it.dao.UserDao;
import by.academy.it.pojo.User;
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

    private List<User> testUserList;

    @Before
    public void setUp() {
        searchService = new SearchService(userDao);
        testUserList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            testUserList.add(new User());
        }
    }

    @Test
    public void pagesCountTest() {
        when(userDao.searchUsers(anyString())).thenReturn(testUserList);
        assertEquals(7, searchService.pagesCount(anyString()));

        when(userDao.searchUsers(anyString())).thenReturn(new ArrayList<>());
        when(userDao.getAllUsers()).thenReturn(testUserList);
        assertEquals(7, searchService.pagesCount(anyString()));

        verify(userDao, times(2)).searchUsers(anyString());
        verify(userDao, times(1)).getAllUsers();
    }

    @Test
    public void pageUsersTest() {
        when(userDao.pageUsers(anyString(), anyInt())).thenReturn(testUserList);
        assertSame(testUserList, searchService.pageUsers(anyString(), anyInt()));
        verify(userDao, times(1)).pageUsers(anyString(), anyInt());
    }
}