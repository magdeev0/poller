package net.magdeev.poller.repository;

import net.magdeev.poller.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query
    List<Question> findQuestionsByPollId(Long pollId);

    @Modifying
    @Transactional
    @Query("update Question q set q.question = :question where q.id = :id")
    void updateQuestionById(@Param("id") Long id,
                            @Param("question") String question);
}
