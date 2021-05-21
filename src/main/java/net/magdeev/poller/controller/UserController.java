package net.magdeev.poller.controller;

import net.magdeev.poller.entity.User;
import net.magdeev.poller.repository.UserRepository;
import net.magdeev.poller.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class UserController {
    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    public UserController(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String registration() {
        if (userService.isLogged()) {
            return "redirect:/";
        }
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(User user, @RequestParam String confirmPassword, Model model) {
        if (userService.isLogged()) {
            return "redirect:/";
        }
        final var message = userService.registration(user, confirmPassword);
        if (!message.equals("success")) {
            model.addAttribute("message", message);
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/restore")
    public String restorePassword() {
        if (userService.isLogged()) {
            return "redirect:/";
        }
        return "restorePassword";
    }

    @PostMapping("/restore")
    public String restorePassword(@RequestParam String username, @RequestParam String secret, Model model) {
        if (userService.isLogged()) {
            return "redirect:/";
        }
        final var message = userService.restore(username, secret);
        model.addAttribute("message", message);
        return "restorePassword";
    }

    @GetMapping("/user")
    public String getUser(Model model) {
        final var user = userService.findAuthorizedByUsername();
        model.addAttribute("user", userService.findById(user.get().getId()));
        return "users/user";
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "users/user";
    }

    @GetMapping("/user/edit")
    public String editUser(Model model) {
        final var user = userService.findAuthorizedByUsername();
        model.addAttribute("user", user);
        return "users/edit";
    }

    @PostMapping("/user/edit")
    public String editUser(@RequestParam String password,
                           @RequestParam String confirmPassword,
                           Model model) {
        final var user = userService.findAuthorizedByUsername();
        final var message = userService.update(user, password, confirmPassword);
        model.addAttribute("user", user);
        model.addAttribute("message", message);
        return "users/edit";
    }

    @GetMapping("/user/uploadAvatar")
    public String uploadAvatar(Model model) {
        model.addAttribute("user", userService.findAuthorizedByUsername());
        return "users/uploadAvatar";
    }

    @PostMapping("/user/uploadAvatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        final var user = userService.findAuthorizedByUsername();
        InputStream is = file.getInputStream();
        userRepository.updateAvatar(is.readAllBytes(), user.get().getId());
        return "redirect:/user/" + user.get().getId();
    }
}
