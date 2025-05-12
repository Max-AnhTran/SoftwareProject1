// src/main/java/haaga_helia/fi/SoftwareProject1/web/ReviewRestController.java
package haaga_helia.fi.SoftwareProject1.web;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import haaga_helia.fi.SoftwareProject1.domain.Quiz;
import haaga_helia.fi.SoftwareProject1.domain.Review;
import haaga_helia.fi.SoftwareProject1.domain.QuizRepository;
import haaga_helia.fi.SoftwareProject1.domain.ReviewRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of reviews"),
            @ApiResponse(responseCode = "404", description = "Quiz not found")
    })
    public ResponseEntity<List<Review>> list(@PathVariable Long quizId) {
        if (!quizRepo.existsById(quizId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reviewRepo.findByQuizId(quizId));
    }

    // POST /api/quizzes/{quizId}/reviews
    @PostMapping
    @Operation(summary = "Create a new review", description = "Creates and returns a new review for the specified quiz.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully created review"),
            @ApiResponse(responseCode = "400", description = "Validation failed or quiz not found")
    })
    public ResponseEntity<?> create(
            @PathVariable Long quizId,
            @Valid @RequestBody Review r,
            BindingResult binding) {
        // 1) fail fast on validation errors
        if (binding.hasErrors()) {
            return ResponseEntity.badRequest().body(binding.getAllErrors());
        }
        // 2) quiz must exist
        Optional<Quiz> quizOpt = quizRepo.findById(quizId);
        if (quizOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Quiz not found");
        }
        // 3) persist
        r.setQuiz(quizOpt.get());
        Review saved = reviewRepo.save(r);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/quizzes/{quizId}/reviews/{reviewId}
    @PutMapping("/{reviewId}")
    @Operation(summary = "Update a review", description = "Updates the content of an existing review.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated review"),
            @ApiResponse(responseCode = "400", description = "Validation failed or not found")
    })
    public ResponseEntity<?> update(
            @PathVariable Long quizId,
            @PathVariable Long reviewId,
            @Valid @RequestBody Review updated,
            BindingResult binding) {
        if (binding.hasErrors()) {
            return ResponseEntity.badRequest().body(binding.getAllErrors());
        }
        if (!quizRepo.existsById(quizId)) {
            return ResponseEntity.badRequest().body("Quiz not found");
        }
        Optional<Review> existingOpt = reviewRepo.findById(reviewId);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Review not found");
        }
        Review existing = existingOpt.get();
        existing.setAuthor(updated.getAuthor());
        existing.setContent(updated.getContent());
        existing.setRating(updated.getRating());
        Review saved = reviewRepo.save(existing);
        return ResponseEntity.ok(saved);
    }

    // DELETE /api/quizzes/{quizId}/reviews/{reviewId}
    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Delete a review", description = "Deletes a review associated with the quiz.")
    @ApiResponses({
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
