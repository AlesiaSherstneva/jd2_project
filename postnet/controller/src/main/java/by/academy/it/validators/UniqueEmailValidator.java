package by.academy.it.validators;

import by.academy.it.dao.UserDao;
import by.academy.it.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UniqueEmailValidator implements Validator {
    private final UserDao userDao;

    @Autowired
    public UniqueEmailValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        User userInBase = userDao.getUserByEmail(user.getEmail());
        if (userInBase != null && !user.getId().equals(userInBase.getId())) {
            errors.rejectValue("email", "", "Такой e-mail уже зарегистрирован!");
        }
    }
}
