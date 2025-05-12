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

    public static class AnswerRequest {
        public Long quizId;
        public Long questionId;
        public Long answerOptionId;
    }

    @PostMapping("/submit")
    @Operation(summary = "Submit answer for a quiz question", description = "Submits a selected answer option for a given quiz and question. Returns whether the answer is correct.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answer submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid quiz, question, or answer option")
    })
    public ResponseEntity<?> submitAnswer(@RequestBody AnswerRequest request) {
        // 1) All IDs must be non-null
        if (request.quizId == null || request.questionId == null || request.answerOptionId == null) {
            return ResponseEntity.badRequest().body("quizId, questionId and answerOptionId must be provided");
        }

        // 2) Quiz must exist and be published
        Optional<Quiz> quizOpt = quizRepo.findById(request.quizId);
        if (quizOpt.isEmpty() || !quizOpt.get().isPublished()) {
            return ResponseEntity.badRequest().body("Quiz not found or not published");
        }

        // 3) Question must exist
        Optional<Question> questionOpt = questionRepo.findById(request.questionId);
        if (questionOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Question not found");
        }

        // 4) AnswerOption must exist
        Optional<AnswerOption> selectedAnswerOpt = answerRepo.findById(request.answerOptionId);
        if (selectedAnswerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Answer option not found");
        }

        // 5) Evaluate correctness
        boolean isCorrect = selectedAnswerOpt.get().getCorrect();

        // 6) Persist
        AnswerSubmission submission = new AnswerSubmission(
                quizOpt.get(),
                questionOpt.get(),
                isCorrect);
        submissionRepo.save(submission);

        // 7) Return JSON { "correct": true/false }
        return ResponseEntity.ok(Map.of("correct", isCorrect));
    }
}
