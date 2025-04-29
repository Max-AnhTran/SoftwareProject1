package haaga_helia.fi.SoftwareProject1.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import haaga_helia.fi.SoftwareProject1.domain.*;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class AnswerOptionRestController {
    @Autowired
    private QuestionRepository questionRepo;
    @Autowired
    private AnswerOptionRepository answerRepo;

    // GET /api/questions/{questionId}/answers
    @GetMapping
    public ResponseEntity<List<AnswerOption>> getAnswers(@PathVariable Long questionId) {
        return questionRepo.findById(questionId)
                .map(q -> ResponseEntity.ok(answerRepo.findByQuestion(q)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
