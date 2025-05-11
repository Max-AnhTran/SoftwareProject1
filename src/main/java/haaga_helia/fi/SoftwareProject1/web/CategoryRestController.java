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
@RequestMapping("/api/categories")
@Tag(name = "Category API", description = "Operations related to quiz categories")
public class CategoryRestController {
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private QuizRepository quizRepo;

    // GET /api/categories
    @GetMapping
    @Operation(summary = "Get all categories", description = "Returns a list of all available quiz categories.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of categories")
    })
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryRepo.findAll());
    }

    // GET /api/categories/{categoryId}/quizzes
    @GetMapping("/{categoryId}/quizzes")
    @Operation(summary = "Get published quizzes by category", description = "Returns a list of published quizzes for a specific category.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved quizzes for the category"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<List<Quiz>> getQuizzesByCategory(@PathVariable Long categoryId) {
        return categoryRepo.findById(categoryId)
                .map(cat -> ResponseEntity.ok(
                        // you’ll need this method:
                        // List<Quiz> findByCategoryAndPublished(Category c, boolean published)
                        quizRepo.findByCategoryAndPublished(cat, true)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
