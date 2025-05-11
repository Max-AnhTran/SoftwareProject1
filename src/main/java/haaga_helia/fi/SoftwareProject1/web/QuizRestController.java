package haaga_helia.fi.SoftwareProject1.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import haaga_helia.fi.SoftwareProject1.domain.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/quizzes")
@Tag(name = "Quiz API", description = "Operations related to quizzes")
public class QuizRestController {
    @Autowired private QuizRepository quizRepository;

    // GET /api/quizzes/published
    @GetMapping("/published")
    @Operation(summary = "Get all published quizzes", description = "Returns a list of all quizzes that are published.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of published quizzes")
    })
    public ResponseEntity<List<Quiz>> getPublishedQuizzes() {
        return ResponseEntity.ok(quizRepository.findByPublished(true));
    }

    // GET /api/quizzes/published/{quizId}
    @GetMapping("/published/{quizId}")
    @Operation(summary = "Get a published quiz by ID", description = "Returns a specific published quiz by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the quiz"),
        @ApiResponse(responseCode = "404", description = "Quiz not found or not published")
    })
    public ResponseEntity<Quiz> getPublishedQuizById(@PathVariable Long quizId) {
        return quizRepository.findById(quizId)
            .filter(Quiz::isPublished)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
