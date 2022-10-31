package by.academy.it.services;

import by.academy.it.dao.UserDao;
import by.academy.it.pojo.User;
import by.academy.it.pojo.UserDetails;
import by.academy.it.pojo.UserJob;
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

    public User changeUsersPassword(String email) {
        User user = userDao.getUserByEmail(email);
        user.setPassword(user.getPassword().substring(6));
        return user;
    }

    public void updateUser(User user) {
        user.setPassword("{noop}" + user.getPassword());
        userDao.updateUser(user);
    }

    public UserJob getUserJob(String email) {
        return userDao.getUserByEmail(email).getUserJob();
    }

    public void updateUserJob(UserJob userJob) {
        userDao.updateUserJob(userJob);
    }

    public UserDetails getUserDetails(String email) {
        UserDetails userDetails = userDao.getUserByEmail(email).getUserDetails();
        if (userDetails.getAbout().equals("не указано")) {
            userDetails.setAbout(" ");
        }
        if (userDetails.getHobby().equals("не указано")) {
            userDetails.setHobby(" ");
        }
        return userDetails;
    }

    public void updateUserDetails(UserDetails userDetails) {
        if (userDetails.getAbout() == null) {
            userDetails.setAbout("не указано");
        }
        if (userDetails.getHobby() == null) {
            userDetails.setHobby("не указано");
        }
        userDao.updateUserDetails(userDetails);
    }
}
