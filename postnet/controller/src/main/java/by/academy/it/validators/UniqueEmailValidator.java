package by.academy.it.validators;

import by.academy.it.dao.UserDao;
import by.academy.it.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UniqueEmailValidator {

    @Autowired
    UserDao userDao;

    public boolean validateEmail(String email) {
        List<User> users = userDao.getAllUsers();
        List<String> emails = new ArrayList<>();
        for (User user: users) {
            emails.add(user.getEmail());
        }
        return emails.contains(email);
    }
}
