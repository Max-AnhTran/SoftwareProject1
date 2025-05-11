package haaga_helia.fi.SoftwareProject1.web;

import haaga_helia.fi.SoftwareProject1.domain.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/answers")
@Tag(name = "Answer Submissions", description = "Endpoints for submitting answers to quiz questions")
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
    @Operation(
        summary = "Submit answer for a quiz question",
        description = "Submits a selected answer option for a given quiz and question. Returns whether the answer is correct."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Answer submitted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid quiz, question, or answer option")
    })
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
