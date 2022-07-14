package by.academy.it.dao;

import by.academy.it.pojo.User;
import by.academy.it.pojo.UserDetails;
import by.academy.it.pojo.UserJob;


import java.util.List;

public interface UserDao {

    void setUser(User user);

    User getUser(Integer id);

    List<User> getAllUsers();

    User getUserByEmail(String email);

    List<User> searchUsers(String searchString);

    List<User> pageUsers(String searchString, int page);

    void updateUser(User user);

    void updateUserJob(UserJob userJob);

    void updateUserDetails(UserDetails userDetails);

    void updateUserStatus(Byte enabled, Integer id);

    void deleteUser(Integer id);
}
