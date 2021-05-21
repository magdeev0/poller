package net.magdeev.poller.service;

import net.magdeev.poller.entity.Poll;
import net.magdeev.poller.entity.Question;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface PollService {
    Optional<Poll> save(Poll poll, Long id, int countOfQuestions);

    void send(Long id, HttpServletRequest request);

    void fill(Long id, List<String> question, List<String> answers);

    void linkToUser(Long userId, Long pollId);

    void delete(Long id);

    void restore(Long id);

    List<String> extractAnswers(HttpServletRequest request);

    Long getVotes(Long id);

    Iterable<Poll> getAll();

    List<Question> findQuestionsByPollId(Long pollId);

    Optional<Poll> findById(Long id);

    boolean isOwner(Long pollId, Long ownerId);
}
