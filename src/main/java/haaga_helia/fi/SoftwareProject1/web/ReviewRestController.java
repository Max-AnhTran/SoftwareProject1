package haaga_helia.fi.SoftwareProject1.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import haaga_helia.fi.SoftwareProject1.domain.*;

@RestController
@RequestMapping("/api/quizzes/{quizId}/reviews")
public class ReviewRestController {
    @Autowired
    private QuizRepository quizRepo;
    @Autowired
    private ReviewRepository reviewRepo;

    // GET /api/quizzes/{quizId}/reviews
    @GetMapping
    public ResponseEntity<List<Review>> list(@PathVariable Long quizId) {
        if (!quizRepo.existsById(quizId))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reviewRepo.findByQuizId(quizId));
    }

    // POST /api/quizzes/{quizId}/reviews
    @PostMapping
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
