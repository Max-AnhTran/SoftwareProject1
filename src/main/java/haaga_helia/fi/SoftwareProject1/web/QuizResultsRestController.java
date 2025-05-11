package haaga_helia.fi.SoftwareProject1.web;

import java.util.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import haaga_helia.fi.SoftwareProject1.domain.*;
import haaga_helia.fi.SoftwareProject1.domain.dto.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/quizzes/{quizId}/results")
@Tag(name = "Quiz Results API", description = "Operations related to quiz result statistics")
public class QuizResultsRestController {
    @Autowired
    private QuizRepository quizRepo;
    @Autowired
    private AnswerSubmissionRepository submissionRepo;
    @Autowired
    private QuestionRepository questionRepo;

    // GET /api/quizzes/{quizId}/results
    @GetMapping
    @Operation(summary = "Get quiz result statistics", description = "Returns statistics including total submissions, correctness percentage, and question-level stats for a specific quiz.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved quiz results"),
        @ApiResponse(responseCode = "404", description = "Quiz not found")
    })
    public ResponseEntity<QuizResultsDTO> getResults(@PathVariable Long quizId) {
        if (!quizRepo.existsById(quizId)) {
            return ResponseEntity.notFound().build();
        }

        List<AnswerSubmission> subs = submissionRepo.findByQuizId(quizId);
        long total = subs.size();
        long correct = subs.stream().filter(AnswerSubmission::isCorrect).count();
        double pct = total > 0 ? (correct * 100.0 / total) : 0.0;

        Map<Long, List<AnswerSubmission>> byQ = subs.stream()
                .collect(Collectors.groupingBy(s -> s.getQuestion().getId()));

        List<QuestionStatsDTO> stats = byQ.entrySet().stream()
                .map(e -> {
                    Long qId = e.getKey();
                    List<AnswerSubmission> list = e.getValue();
                    long c = list.stream().filter(AnswerSubmission::isCorrect).count();
                    long w = list.size() - c;
                    String text = questionRepo.findById(qId)
                            .map(Question::getContent)
                            .orElse("?");
                    return new QuestionStatsDTO(qId, text, c, w);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(new QuizResultsDTO(quizId, total, pct, stats));
    }
}
