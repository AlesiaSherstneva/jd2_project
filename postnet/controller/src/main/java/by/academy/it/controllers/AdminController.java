package by.academy.it.controllers;

import by.academy.it.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/ban")
    public String banUser(@RequestParam("id") int id) {
        adminService.banUser(id);
        return "redirect:/";
    }

    @GetMapping("/unban")
    public String unbanUser(@RequestParam("id") int id) {
        adminService.unbanUser(id);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") int id) {
        adminService.deleteUser(id);
        return "redirect:/";
    }
}