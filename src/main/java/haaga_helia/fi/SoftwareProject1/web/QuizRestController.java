package haaga_helia.fi.SoftwareProject1.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import haaga_helia.fi.SoftwareProject1.domain.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizRestController {
    @Autowired private QuizRepository quizRepository;

    // GET /api/quizzes/published
    @GetMapping("/published")
    public ResponseEntity<List<Quiz>> getPublishedQuizzes() {
        return ResponseEntity.ok(quizRepository.findByPublished(true));
    }

    // GET /api/quizzes/published/{quizId}
    @GetMapping("/published/{quizId}")
    public ResponseEntity<Quiz> getPublishedQuizById(@PathVariable Long quizId) {
        return quizRepository.findById(quizId)
            .filter(Quiz::isPublished)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
