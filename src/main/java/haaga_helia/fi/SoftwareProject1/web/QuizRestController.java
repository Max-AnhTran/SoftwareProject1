package haaga_helia.fi.SoftwareProject1.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import haaga_helia.fi.SoftwareProject1.domain.Quiz;
import haaga_helia.fi.SoftwareProject1.domain.QuizRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin
public class QuizRestController {
    @Autowired
    private QuizRepository quizRepository;
    
    // Get published quizzes
    @GetMapping("/published")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<List<Quiz>> getPublishedQuizzes() {
        List<Quiz> quizzes = quizRepository.findByPublished(true);
        return ResponseEntity.ok(quizzes);
    }

    // Get published quiz by quiz ID
    @GetMapping("/published/{quizId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<Quiz> getPublishedQuizById(@PathVariable Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz == null || !quiz.isPublished()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quiz);
    }
}
