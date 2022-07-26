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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ProfileController {

    private final UserDao userDao;

    private final UniqueEmailValidator validator;

    @Autowired
    public ProfileController(UserDao userDao, UniqueEmailValidator validator) {
        this.userDao = userDao;
        this.validator = validator;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor editor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, editor);
    }

    @GetMapping("/edit-1")
    public String firstEdit(Principal principal,
                            Model model) {
        User user = userDao.getUserByEmail(principal.getName());
        user.setPassword(user.getPassword().substring(6));
        model.addAttribute("user", user);
        return "/registered/edit-1";
    }

    @PatchMapping("/confirm-1")
    public String firstConfirm(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               Principal principal) {
        if(bindingResult.hasErrors()) {
            return "/registered/edit-1";
        }
        if(validator.validateEmail(user.getEmail()) && !user.getEmail().equals(principal.getName())) {
            return "/unregistered/wrong-email";
        }
        user.setPassword("{noop}" + user.getPassword());
        userDao.updateUser(user);
        if(!user.getEmail().equals(principal.getName())) {
            return "redirect:/login?logout";
        }
        return "redirect:/";
    }

    @GetMapping("/edit-2")
    public String secondEdit(Principal principal,
                            Model model) {
        model.addAttribute("userjob", userDao.getUserByEmail(principal.getName()).getUserJob());
        return "/registered/edit-2";
    }

    @PatchMapping("/confirm-2")
    public String secondConfirm(@Valid @ModelAttribute("userjob") UserJob userJob,
                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/registered/edit-2";
        }
        userDao.updateUserJob(userJob);
        return "redirect:/";
    }

    @GetMapping("/edit-3")
    public String thirdEdit(Principal principal,
                             Model model) {
        model.addAttribute("userdetails", userDao.getUserByEmail(principal.getName()).getUserDetails());
        return "/registered/edit-3";
    }

    @PatchMapping("/confirm-3")
    public String thirdConfirm(@Valid @ModelAttribute("userdetails") UserDetails userDetails,
                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/registered/edit-3";
        }
        userDao.updateUserDetails(userDetails);
        return "redirect:/";
    }
}
