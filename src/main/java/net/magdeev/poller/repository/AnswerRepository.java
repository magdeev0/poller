package net.magdeev.poller.repository;

import net.magdeev.poller.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into questions_answers values (:question_id, :answers_id)", nativeQuery = true)
    void linkAnswerToQuestion(@Param("question_id") Long question_id,
                        @Param("answers_id") Long answers_id);
}
