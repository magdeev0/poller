package net.magdeev.poller.controller;

import net.magdeev.poller.service.PollServiceImpl;
import net.magdeev.poller.service.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final PollServiceImpl pollService;

    public AdminController(UserServiceImpl userService, PollServiceImpl pollService) {
        this.userService = userService;
        this.pollService = pollService;
    }

    @GetMapping()
    public String mainPanel() {
        return "admin/admin";
    }

    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "admin/allUsers";
    }

    @PostMapping("/users")
    public String findUsers(@RequestParam String username, Model model) {
        model.addAttribute("foundUser", userService.findByUsername(username));
        return "admin/allUsers";
    }

    @GetMapping("/polls")
    public String allPolls(Model model) {
        model.addAttribute("polls", pollService.getAll());
        model.addAttribute("size", pollService.getAll().size());
        return "admin/allPolls";
    }

    @GetMapping("/polls/{id}/delete")
    public String deletePoll(@PathVariable Long id) {
        pollService.delete(id);
        return "redirect:/admin/polls";
    }

    @GetMapping("/polls/{id}/restore")
    public String restorePoll(@PathVariable Long id) {
        pollService.restore(id);
        return "redirect:/admin/polls";
    }
}
