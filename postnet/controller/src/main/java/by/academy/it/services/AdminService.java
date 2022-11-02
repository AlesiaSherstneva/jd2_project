package by.academy.it.services;

import by.academy.it.dao.UserDao;
import by.academy.it.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UserDao userDao;

    @Autowired
    public AdminService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void banUser(int id) {
        userDao.updateUserStatus((byte) 0, id);
    }

    public void unbanUser(int id) {
        userDao.updateUserStatus((byte) 1, id);
    }

    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }
}
