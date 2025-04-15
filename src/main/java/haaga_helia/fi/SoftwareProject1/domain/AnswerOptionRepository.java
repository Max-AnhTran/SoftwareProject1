package haaga_helia.fi.SoftwareProject1.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {
    List<AnswerOption> findByQuestion(Question question);
    List<AnswerOption> findByCorrect(boolean correct);
    List<AnswerOption> findByQuestionAndCorrect(Question question, boolean correct);
    AnswerOption findByQuestionIdAndCorrectTrue(Long id);
}
