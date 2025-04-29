package haaga_helia.fi.SoftwareProject1.web;

import haaga_helia.fi.SoftwareProject1.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/answers")

public class AnswerSubmissionRestController {
    @Autowired
    private QuizRepository quizRepo;

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private AnswerOptionRepository answerRepo;

    @Autowired
    private AnswerSubmissionRepository submissionRepo;

    // DTO to receive JSON from frontend
    public static class AnswerRequest {
        public Long quizId;
        public Long questionId;
        public Long answerOptionId;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitAnswer(@RequestBody AnswerRequest request) {
        Optional<Quiz> quizOpt = quizRepo.findById(request.quizId);
        Optional<Question> questionOpt = questionRepo.findById(request.questionId);
        Optional<AnswerOption> selectedAnswerOpt = answerRepo.findById(request.answerOptionId);

        if (quizOpt.isEmpty() || questionOpt.isEmpty() || selectedAnswerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid quiz, question, or answer option");
        }

        AnswerOption selectedAnswer = selectedAnswerOpt.get();
        boolean isCorrect = selectedAnswer.getCorrect();

        // Save submission
        AnswerSubmission submission = new AnswerSubmission(quizOpt.get(), questionOpt.get(), isCorrect);
        submissionRepo.save(submission);

        // Return result to frontend
        return ResponseEntity.ok().body(Map.of("correct", isCorrect));
    }
}
