package net.magdeev.poller.service;

import net.magdeev.poller.entity.Answer;
import net.magdeev.poller.entity.Poll;
import net.magdeev.poller.entity.Question;
import net.magdeev.poller.repository.AnswerRepository;
import net.magdeev.poller.repository.PollRepository;
import net.magdeev.poller.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Service
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;
    private final UserServiceImpl userService;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public PollServiceImpl(PollRepository pollRepository, UserServiceImpl userService, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.pollRepository = pollRepository;
        this.userService = userService;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public Optional<Poll> save(Poll poll, Long id, int countOfQuestions) {
        poll.setOwner(userService.findById(id).get());
        poll.setDeleted(false);
        poll.setVotes(0L);
        final var savedPoll = pollRepository.save(poll);
        linkToUser(id, savedPoll.getId());
        for (int i = 0; i < countOfQuestions; i++) {
            final var question = new Question();
            question.setPoll(poll);
            final var savedQuestion = questionRepository.save(question);
            pollRepository.linkQuestionsToPoll(savedPoll.getId(), savedQuestion.getId());
        }
        return Optional.of(poll);
    }

    @Override
    public void linkToUser(Long userId, Long pollId) {
        pollRepository.linkPollToUser(userId, pollId);
    }

    @Override
    public List<Question> findQuestionsByPollId(Long pollId) {
        return questionRepository.findQuestionsByPollId(pollId);
    }

    @Override
    public void fill(Long id, List<String> question, List<String> answers) {
        final var questionsByPollId = findQuestionsByPollId(id);
        for (int i = 0; i < answers.size(); i++) {
            String[] splitAnswers = answers.get(i).split(";");
            for (String splitAnswer : splitAnswers) {
                final var answer = new Answer();
                answer.setAnswer(splitAnswer.trim());
                final var saved = answerRepository.save(answer);
                answerRepository.linkAnswerToQuestion(questionsByPollId.get(i).getId(), saved.getId());
            }
            questionRepository.updateQuestionById(questionsByPollId.get(i).getId(), question.get(i));
        }
        pollRepository.confirmPoll(id);
    }

    @Override
    public List<Poll> getAll() {
        return pollRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        pollRepository.deletePollById(id);
    }

    @Override
    public void restore(Long id) {
        pollRepository.restorePollById(id);
    }

    @Override
    public Optional<Poll> findById(Long id) {
        return pollRepository.findById(id);
    }

    @Override
    public boolean isOwner(Long pollId, Long ownerId) {
        return pollRepository.isPollOwner(pollId, ownerId) > 0;
    }

    @Override
    public List<String> extractAnswers(HttpServletRequest request) {
        List<String> values = new ArrayList<>();
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String parameterName = (String) enumeration.nextElement();
            if (parameterName.startsWith("answer")) {
                values.add(request.getParameter(parameterName));
            }
        }
        return values;
    }

    @Override
    public void send(Long id, HttpServletRequest request) {
        final var answers = extractAnswers(request);
        for (String answer : answers) {
            final var findById = answerRepository.findById(Long.parseLong(answer));
            answerRepository.save(new Answer(
                    Long.parseLong(answer),
                    findById.get().getAnswer(),
                    findById.get().getVotes() + 1
            ));
        }
        final var votesByPollId = pollRepository.getVotesByPollId(id);
        pollRepository.updateVotes(id, votesByPollId + 1);
    }

    @Override
    public Long getVotes(Long id) {
        return pollRepository.getVotesByPollId(id);
    }
}
