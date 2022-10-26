package by.academy.it.services;

import by.academy.it.dao.UserDao;
import by.academy.it.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(User user) {
        user.setPassword("{noop}" + user.getPassword());
        if (user.getUserDetails().getAbout() == null) {
            user.getUserDetails().setAbout("не указано");
        }
        if (user.getUserDetails().getHobby() == null) {
            user.getUserDetails().setHobby("не указано");
        }
        userDao.setUser(user);
    }
}
