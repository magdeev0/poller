package net.magdeev.poller.controller;

import net.magdeev.poller.entity.Poll;
import net.magdeev.poller.service.PollServiceImpl;
import net.magdeev.poller.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PollsController {
    private final UserServiceImpl userService;
    private final PollServiceImpl pollService;

    public PollsController(UserServiceImpl userService, PollServiceImpl pollService) {
        this.userService = userService;
        this.pollService = pollService;
    }

    @GetMapping("/polls")
    public String getUserPolls(Model model) {
        final var user = userService.findAuthorizedByUsername();
        model.addAttribute("user", userService.findById(user.get().getId()));
        model.addAttribute("polls", userService.findById(user.get().getId()).get().getPolls());
        return "users/polls";
    }

    @GetMapping("/polls/create")
    public String createPoll(Model model) {
        model.addAttribute("user", userService.findAuthorizedByUsername());
        return "polls/create";
    }

    @PostMapping("/polls/create")
    public String createPoll(@RequestParam int countOfQuestions, Poll poll) {
        final var user = userService.findAuthorizedByUsername();
        final var saved = pollService.save(poll, user.get().getId(), countOfQuestions);
        return "redirect:/polls/create-confirm/" + saved.get().getId();
    }

    @GetMapping("/polls/create-confirm/{id}")
    public String fillPoll(@PathVariable Long id, Model model) {
        if (pollService.findById(id).isPresent() && pollService.findById(id).get().isConfirmed()) {
            return "redirect:/poll/" + id;
        }
        final var user = userService.findAuthorizedByUsername();
        if (!pollService.isOwner(id, user.get().getId())) {
            return "redirect:/polls";
        }
        model.addAttribute("questions", pollService.findQuestionsByPollId(id));
        return "polls/create-confirm";
    }

    @PostMapping("/polls/create-confirm/{id}")
    public String fillPoll(@PathVariable Long id,
                           @RequestParam(value = "question") List<String> question,
                           @RequestParam(value = "answers") List<String> answers) {
        pollService.fill(id, question, answers);
        return "redirect:/poll/" + id;
    }

    @GetMapping("/poll/{id}")
    public String getPoll(@PathVariable Long id, Model model) {
        if (pollService.findById(id).isEmpty()
                || pollService.findById(id).get().isDeleted()
                || !pollService.findById(id).get().isConfirmed()) {
            return "redirect:/";
        }
        model.addAttribute("poll", pollService.findById(id));
        return "polls/poll";
    }

    @PostMapping("/poll/{id}")
    public String sendPoll(@PathVariable Long id, HttpServletRequest request) {
        pollService.send(id, request);
        return "redirect:/poll/result/" + id;
    }

    @GetMapping("/poll/results/{id}")
    public String getResults(@PathVariable Long id, Model model) {
        if (pollService.findById(id).isEmpty()
                || pollService.findById(id).get().isDeleted()
                || !pollService.findById(id).get().isConfirmed()) {
            return "redirect:/";
        }
        model.addAttribute("poll", pollService.findById(id));
        model.addAttribute("votes", pollService.getVotes(id));
        return "polls/results";
    }

    @GetMapping("/poll/{id}/delete")
    public String deletePoll(@PathVariable Long id) {
        final var user = userService.findAuthorizedByUsername();
        if (!pollService.isOwner(id, user.get().getId())) {
            return "redirect:/polls";
        }
        pollService.delete(id);
        return "redirect:/polls";
    }
}