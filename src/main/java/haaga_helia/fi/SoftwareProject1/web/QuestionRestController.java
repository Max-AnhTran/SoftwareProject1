package haaga_helia.fi.SoftwareProject1.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import haaga_helia.fi.SoftwareProject1.domain.*;

@RestController
@RequestMapping("/api/quizzes/{quizId}/questions")
public class QuestionRestController {
    @Autowired private QuizRepository quizRepo;
    @Autowired private QuestionRepository questionRepo;

    // GET /api/quizzes/{quizId}/questions
    @GetMapping
    public ResponseEntity<List<Question>> getQuestions(@PathVariable Long quizId) {
        return quizRepo.findById(quizId)
            .filter(Quiz::isPublished)
            .map(quiz -> ResponseEntity.ok(questionRepo.findByQuiz(quiz)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

