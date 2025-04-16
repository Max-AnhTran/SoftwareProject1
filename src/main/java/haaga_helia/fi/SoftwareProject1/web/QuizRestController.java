package haaga_helia.fi.SoftwareProject1.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import haaga_helia.fi.SoftwareProject1.domain.Quiz;
import haaga_helia.fi.SoftwareProject1.domain.QuizRepository;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class QuizRestController {
    @Autowired
    private QuizRepository quizRepository;

    @GetMapping("/quizzes")
    public List<Quiz> getPublishedQuizzes() {
        return quizRepository.findByPublished(true);
    }
    
}
