package haaga_helia.fi.SoftwareProject1.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerSubmissionRepository extends JpaRepository<AnswerSubmission, Long> {
    List<AnswerSubmission> findByQuizId(Long quizId);
}
