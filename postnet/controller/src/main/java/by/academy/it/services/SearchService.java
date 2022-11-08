package by.academy.it.services;

import by.academy.it.dao.UserDao;
import by.academy.it.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    private final UserDao userDao;

    @Autowired
    public SearchService(UserDao userDao) {
        this.userDao = userDao;
    }

    public int pagesCount(String searchString) {
        List<User> users = userDao.searchUsers(searchString);
        if (users.isEmpty()) {
            users = userDao.getAllUsers();
        }
        return users.size();
    }

    public List<User> pageUsers(String searchString, int page) {
        return userDao.pageUsers(searchString, page);
    }
}
