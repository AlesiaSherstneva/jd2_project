package by.academy.it.controllers;

import by.academy.it.dao.UserDao;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ProfileController {

    private final UserDao userDao;
    private final UniqueEmailValidator uniqueEmailValidator;
    private final UserService userService;

    @Autowired
    public ProfileController(UserDao userDao, UniqueEmailValidator uniqueEmailValidator, UserService userService) {
        this.userDao = userDao;
        this.uniqueEmailValidator = uniqueEmailValidator;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor editor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, editor);
    }

    @GetMapping("/edit-1")
    public String firstEdit(Principal principal,
                            Model model) {
        model.addAttribute("user", userService.changeUsersPassword(principal.getName()));
        return "/registered/edit-1";
    }

    @SuppressWarnings("SpringMVCViewInspection")
    @PostMapping("/confirm-1")
    public String firstConfirm(@ModelAttribute("user") @Valid User user,
                               BindingResult bindingResult,
                               Principal principal) {
        uniqueEmailValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/registered/edit-1";
        }
        userService.updateUser(user);
        if (!user.getEmail().equals(principal.getName())) {
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
    public String secondConfirm(@ModelAttribute("userjob") @Valid UserJob userJob,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/registered/edit-2";
        }
        userDao.updateUserJob(userJob);
        return "redirect:/";
    }

    @GetMapping("/edit-3")
    public String thirdEdit(Principal principal,
                            Model model) {
        UserDetails userDetails = userDao.getUserByEmail(principal.getName()).getUserDetails();
        if (userDetails.getAbout().equals("не указано")) {
            userDetails.setAbout(" ");
        }
        if (userDetails.getHobby().equals("не указано")) {
            userDetails.setHobby(" ");
        }
        model.addAttribute("userdetails", userDetails);
        return "/registered/edit-3";
    }

    @PatchMapping("/confirm-3")
    public String thirdConfirm(@ModelAttribute("userdetails") @Valid UserDetails userDetails,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/registered/edit-3";
        }
        if (userDetails.getAbout() == null) {
            userDetails.setAbout("не указано");
        }
        if (userDetails.getHobby() == null) {
            userDetails.setHobby("не указано");
        }
        userDao.updateUserDetails(userDetails);
        return "redirect:/";
    }
}
