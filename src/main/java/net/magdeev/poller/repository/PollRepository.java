package net.magdeev.poller.repository;

import net.magdeev.poller.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into usr_polls values (:usr_id, :polls_id)", nativeQuery = true)
    void linkPollToUser(@Param("usr_id") Long usr_id,
                        @Param("polls_id") Long polls_id);

    @Modifying
    @Transactional
    @Query(value = "insert into polls_question values (:poll_id, :question_id)", nativeQuery = true)
    void linkQuestionsToPoll(@Param("poll_id") Long poll_id,
                             @Param("question_id") Long question_id);

    @Modifying
    @Transactional
    @Query(value = "update Poll p set p.isDeleted = true where p.id = :id")
    void deletePollById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "update Poll p set p.isDeleted = false where p.id = :id")
    void restorePollById(@Param("id") Long id);

    @Query(value = "select count(*) from usr_polls up where up.polls_id = :pollId and up.user_id = :userId", nativeQuery = true)
    int isPollOwner(@Param("pollId") Long pollId,
                    @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "update Poll p set p.isConfirmed = true where p.id = :id")
    void confirmPoll(@Param("id") Long id);

    @Query (value = "select votes from polls p where p.id = :id", nativeQuery = true)
    Long getVotesByPollId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "update Poll p set p.votes = :votes where p.id = :id")
    void updateVotes(@Param("id") Long id, @Param("votes") Long votes);

    @Modifying
    @Transactional
    @Query(value = "update Poll p set p.name = :name where p.id = :id")
    void updateName(@Param("id") Long id, @Param("name") String name);
}