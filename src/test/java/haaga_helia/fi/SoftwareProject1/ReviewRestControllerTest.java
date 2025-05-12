package haaga_helia.fi.SoftwareProject1;

import com.fasterxml.jackson.databind.ObjectMapper;
import haaga_helia.fi.SoftwareProject1.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewRestControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private QuizRepository quizRepo;
    @Autowired private ReviewRepository reviewRepo;
    @Autowired private ObjectMapper objectMapper;

    private Quiz quiz;

    @BeforeEach
    public void setup() {
        reviewRepo.deleteAll();
        quizRepo.deleteAll();

        quiz = new Quiz();
        quiz.setName("RevQuiz");
        quiz.setCourseCode("R101");
        quiz.setPublished(true);
        quiz = quizRepo.save(quiz);
    }

    @Test
    public void listReviewsReturnsEmptyWhenNone() throws Exception {
        mockMvc.perform(get("/api/quizzes/" + quiz.getId() + "/reviews")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void createReviewSucceedsWithValidPayload() throws Exception {
        Review r = new Review();
        r.setAuthor("Alice");
        r.setContent("Great quiz!");
        r.setRating(5);

        String json = objectMapper.writeValueAsString(r);

        mockMvc.perform(post("/api/quizzes/" + quiz.getId() + "/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.author").value("Alice"))
            .andExpect(jsonPath("$.content").value("Great quiz!"))
            .andExpect(jsonPath("$.rating").value(5));

        assertThat(reviewRepo.findAll())
            .hasSize(1)
            .first()
            .satisfies(saved -> {
               assertThat(saved.getQuiz().getId()).isEqualTo(quiz.getId());
               assertThat(saved.getAuthor()).isEqualTo("Alice");
               assertThat(saved.getContent()).isEqualTo("Great quiz!");
               assertThat(saved.getRating()).isEqualTo(5);
            });
    }

    @Test
    public void createReviewFailsWhenMissingFields() throws Exception {
        // missing author and rating
        String missingFields = "{\"content\":\"No author or rating\"}";
        mockMvc.perform(post("/api/quizzes/" + quiz.getId() + "/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(missingFields))
            .andExpect(status().isBadRequest());

        assertThat(reviewRepo.count()).isZero();
    }

    @Test
    public void updateReviewSucceedsWithValidData() throws Exception {
        Review saved = new Review();
        saved.setQuiz(quiz);
        saved.setAuthor("Bob");
        saved.setContent("OK");
        saved.setRating(3);
        saved = reviewRepo.save(saved);

        Review update = new Review();
        update.setAuthor("Bobby");
        update.setContent("Actually pretty good");
        update.setRating(4);

        mockMvc.perform(put("/api/quizzes/" + quiz.getId() + "/reviews/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.author").value("Bobby"))
            .andExpect(jsonPath("$.content").value("Actually pretty good"))
            .andExpect(jsonPath("$.rating").value(4));

        Review reloaded = reviewRepo.findById(saved.getId()).get();
        assertThat(reloaded.getAuthor()).isEqualTo("Bobby");
        assertThat(reloaded.getContent()).isEqualTo("Actually pretty good");
        assertThat(reloaded.getRating()).isEqualTo(4);
    }

    @Test
    public void deleteReviewRemovesIt() throws Exception {
        Review saved = new Review();
        saved.setQuiz(quiz);
        saved.setAuthor("Carl");
        saved.setContent("Delete me");
        saved.setRating(2);
        saved = reviewRepo.save(saved);

        mockMvc.perform(delete("/api/quizzes/" + quiz.getId() + "/reviews/" + saved.getId()))
            .andExpect(status().isNoContent());

        assertThat(reviewRepo.existsById(saved.getId())).isFalse();
    }
}

