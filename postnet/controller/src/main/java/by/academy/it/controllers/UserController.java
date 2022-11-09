package by.academy.it.controllers;

import by.academy.it.pojo.User;
import by.academy.it.pojo.UserDetails;
import by.academy.it.pojo.UserJob;
import by.academy.it.services.UserService;
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
    private final UserService userService;
    private final UniqueEmailValidator validator;

    User registeredUser = new User();

    @Autowired
    public UserController(UserService userService, UniqueEmailValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor editor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, editor);
    }

    @GetMapping(value = "/register-step-1")
    public String firstRegister(@ModelAttribute("user") User user) {
        return "/unregistered/register-step-1";
    }

    @PostMapping(value = "/register-step-2")
    public String secondRegister(@ModelAttribute("user") @Valid User user,
                                 BindingResult bindingResult,
                                 Model model) {
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/unregistered/register-step-1";
        }
        registeredUser = user;
        model.addAttribute("userjob", new UserJob());
        return "/unregistered/register-step-2";
    }

    @PostMapping(value = "/register-step-3")
    public String thirdRegister(@ModelAttribute("userjob") @Valid UserJob userJob,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "/unregistered/register-step-2";
        }
        userJob.setUser(registeredUser);
        registeredUser.setUserJob(userJob);
        model.addAttribute("userdetails", new UserDetails());
        return "/unregistered/register-step-3";
    }

    @PostMapping(value = "/confirm")
    public String finishRegister(@ModelAttribute("userdetails") @Valid UserDetails userDetails,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            return "/unregistered/register-step-3";
        }
        userDetails.setUser(registeredUser);
        registeredUser.setUserDetails(userDetails);
        userService.registerUser(registeredUser);

        model.addAttribute("user", registeredUser);
        return "/unregistered/confirm";
    }
}
