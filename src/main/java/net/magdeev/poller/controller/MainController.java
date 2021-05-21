package net.magdeev.poller.controller;

import net.magdeev.poller.entity.Role;
import net.magdeev.poller.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final UserServiceImpl userService;

    public MainController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        if (userService.isFirstInit()) {
            userService.initAdmin();
        }
        if (userService.isLogged()) {
            model.addAttribute("user", userService.findAuthorizedByUsername());
            if (userService.findAuthorizedByUsername().get().getRoles().contains(Role.ADMIN)) {
                model.addAttribute("isAdmin", "true");
            }
        } else {
            model.addAttribute("isAnonymous", "anonymous");
        }
        return "index";
    }
}
