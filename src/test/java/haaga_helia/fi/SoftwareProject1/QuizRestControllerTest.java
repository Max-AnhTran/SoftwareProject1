package haaga_helia.fi.SoftwareProject1;

import haaga_helia.fi.SoftwareProject1.domain.Quiz;
import haaga_helia.fi.SoftwareProject1.domain.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class QuizRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuizRepository quizRepository;

    @BeforeEach
    public void setUp() {
        quizRepository.deleteAll();
    }

    private Quiz createQuiz(String name, String description, String courseCode, boolean published) {
        Quiz quiz = new Quiz();
        quiz.setName(name);
        quiz.setDescription(description);
        quiz.setCourseCode(courseCode);
        quiz.setPublished(published);
        return quizRepository.save(quiz);
    }

    @Test
    public void getAllQuizzesReturnsEmptyListWhenNoQuizzesExist() throws Exception {
        mockMvc.perform(get("/api/quizzes/published")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getAllQuizzesReturnsListOfQuizzesWhenPublishedQuizzesExist() throws Exception {
        createQuiz("Published Quiz 1", "description 1", "CODE101", true);
        createQuiz("Published Quiz 2", "description 2", "CODE102", true);

        mockMvc.perform(get("/api/quizzes/published")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].published", is(true)))
                .andExpect(jsonPath("$[1].published", is(true)));
    }

    @Test
    public void getAllQuizzesDoesNotReturnUnpublishedQuizzes() throws Exception {
        createQuiz("Unpublished Quiz", "description for unpublished", "CODE103", false);
        createQuiz("Published Quiz", "description for published", "CODE104", true);

        mockMvc.perform(get("/api/quizzes/published")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].published", is(true)))
                .andExpect(jsonPath("$[0].name", is("Published Quiz")));
    }
}
