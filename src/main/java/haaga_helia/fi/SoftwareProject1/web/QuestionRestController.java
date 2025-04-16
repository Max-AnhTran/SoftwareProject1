package haaga_helia.fi.SoftwareProject1.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import haaga_helia.fi.SoftwareProject1.domain.Quiz;
import haaga_helia.fi.SoftwareProject1.domain.QuizRepository;
import haaga_helia.fi.SoftwareProject1.domain.AnswerOption;
import haaga_helia.fi.SoftwareProject1.domain.Question;
import haaga_helia.fi.SoftwareProject1.domain.QuestionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class QuestionRestController {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/quizzes/{id}")
    public List<Question> getQuestionsByQuiz(@PathVariable("id") Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
    return quiz.getQuestions();
    }

    
}