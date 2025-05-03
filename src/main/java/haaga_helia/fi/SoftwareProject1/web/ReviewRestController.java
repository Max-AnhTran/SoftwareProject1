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
@RequestMapping("/api/quizzes/{quizId}/reviews")
@Tag(name = "Review API", description = "Operations related to quiz reviews")
public class ReviewRestController {
    @Autowired
    private QuizRepository quizRepo;
    @Autowired
    private ReviewRepository reviewRepo;

    // GET /api/quizzes/{quizId}/reviews
    @GetMapping
    @Operation(summary = "List reviews for a quiz", description = "Returns all reviews associated with the specified quiz.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of reviews"),
        @ApiResponse(responseCode = "404", description = "Quiz not found")
    })
    public ResponseEntity<List<Review>> list(@PathVariable Long quizId) {
        if (!quizRepo.existsById(quizId))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reviewRepo.findByQuizId(quizId));
    }

    // POST /api/quizzes/{quizId}/reviews
    @PostMapping
    @Operation(summary = "Create a new review", description = "Creates and returns a new review for the specified quiz.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created review"),
        @ApiResponse(responseCode = "404", description = "Quiz not found")
    })
    public ResponseEntity<Review> create(
            @PathVariable Long quizId,
            @RequestBody Review r) {
        return quizRepo.findById(quizId).map(q -> {
            r.setQuiz(q);
            return ResponseEntity.ok(reviewRepo.save(r));
        }).orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/quizzes/{quizId}/reviews/{reviewId}
    @PutMapping("/{reviewId}")
    @Operation(summary = "Update a review", description = "Updates the content of an existing review.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated review"),
        @ApiResponse(responseCode = "404", description = "Quiz or review not found")
    })
    public ResponseEntity<Review> update(
            @PathVariable Long quizId,
            @PathVariable Long reviewId,
            @RequestBody Review updated) {
        if (!quizRepo.existsById(quizId))
            return ResponseEntity.notFound().build();
        return reviewRepo.findById(reviewId).map(r -> {
            r.setAuthor(updated.getAuthor());
            r.setContent(updated.getContent());
            return ResponseEntity.ok(reviewRepo.save(r));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/quizzes/{quizId}/reviews/{reviewId}
    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Delete a review", description = "Deletes a review associated with the quiz.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted review"),
        @ApiResponse(responseCode = "404", description = "Quiz or review not found")
    })
    public ResponseEntity<Void> delete(
            @PathVariable Long quizId,
            @PathVariable Long reviewId) {
        if (!quizRepo.existsById(quizId) || !reviewRepo.existsById(reviewId)) {
            return ResponseEntity.notFound().build();
        }
        reviewRepo.deleteById(reviewId);
        return ResponseEntity.noContent().build();
    }
}
