package by.academy.it.controllers;

import by.academy.it.dao.UserDao;
import by.academy.it.pojo.User;
import by.academy.it.pojo.UserDetails;
import by.academy.it.pojo.UserJob;
import by.academy.it.validators.UniqueEmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class UserController {

    private final UserDao userDao;

    private final UniqueEmailValidator validator;

    User registeredUser = new User();

    @Autowired
    public UserController(UserDao userDao, UniqueEmailValidator validator) {
        this.userDao = userDao;
        this.validator = validator;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor editor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, editor);
    }

    @GetMapping(value = "/register-step-1")
    public String firstRegister(Model model) {
        model.addAttribute("user", new User());
        return "/unregistered/register-step-1";
    }

    @PostMapping(value = "/register-step-2")
    public String secondRegister(@Valid @ModelAttribute("user") User user,
                                 BindingResult bindingResult,
                                 Model model) {
        if(validator.validateEmail(user.getEmail())) {
            return "/unregistered/wrong-email";
        }
        if(bindingResult.hasErrors()) {
            return "/unregistered/register-step-1";
        }
        user.setPassword("{noop}" + user.getPassword());
        registeredUser = user;
        model.addAttribute("userjob", new UserJob());
        return "/unregistered/register-step-2";
    }

    @PostMapping(value = "/register-step-3")
    public String thirdRegister(@Valid @ModelAttribute("userjob") UserJob userJob,
                                 BindingResult bindingResult,
                                 Model model) {
        if(bindingResult.hasErrors()) {
            return "/unregistered/register-step-2";
        }
        registeredUser.setUserJob(userJob);
        model.addAttribute("userdetails", new UserDetails());
        return "/unregistered/register-step-3";
    }

    @PostMapping(value = "/confirm")
    public String finishRegister(@Valid @ModelAttribute("userdetails") UserDetails userDetails,
                                 BindingResult bindingResult,
                                 Model model) {
        if(bindingResult.hasErrors()) {
            return "/unregistered/register-step-3";
        }
        if(userDetails.getAbout() == null) {
            userDetails.setAbout("не указано");
        }
        if(userDetails.getHobby() == null) {
            userDetails.setHobby("не указано");
        }
        registeredUser.setUserDetails(userDetails);
        userDao.setUser(registeredUser);
        model.addAttribute("user", registeredUser);
        return "/unregistered/confirm";
    }
}
