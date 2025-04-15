package haaga_helia.fi.SoftwareProject1.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findById(long id);
    List<Question> findByQuiz(Quiz quiz);
    List<Question> findByDifficulty(Question.Difficulty difficulty);
    List<Question> findByQuizAndDifficulty(Quiz quiz, Question.Difficulty difficulty);
    List<Question> findByQuizId(Long quizId);
}
