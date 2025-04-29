package haaga_helia.fi.SoftwareProject1.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import haaga_helia.fi.SoftwareProject1.domain.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private QuizRepository quizRepo;

    // GET /api/categories
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryRepo.findAll());
    }

    // GET /api/categories/{categoryId}/quizzes
    @GetMapping("/{categoryId}/quizzes")
    public ResponseEntity<List<Quiz>> getQuizzesByCategory(@PathVariable Long categoryId) {
        return categoryRepo.findById(categoryId)
                .map(cat -> ResponseEntity.ok(
                        // you’ll need this method:
                        // List<Quiz> findByCategoryAndPublished(Category c, boolean published)
                        quizRepo.findByCategoryAndPublished(cat, true)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
