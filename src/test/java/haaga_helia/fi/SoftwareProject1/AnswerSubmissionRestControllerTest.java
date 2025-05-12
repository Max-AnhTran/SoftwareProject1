package haaga_helia.fi.SoftwareProject1;

import com.fasterxml.jackson.databind.ObjectMapper;
import haaga_helia.fi.SoftwareProject1.domain.*;
import haaga_helia.fi.SoftwareProject1.web.AnswerSubmissionRestController.AnswerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AnswerSubmissionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private QuizRepository quizRepo;
    @Autowired
    private QuestionRepository questionRepo;
    @Autowired
    private AnswerOptionRepository optionRepo;
    @Autowired
    private AnswerSubmissionRepository submissionRepo;
    @Autowired
    private ObjectMapper objectMapper;

    private Quiz publishedQuiz;
    private Question question;
    private AnswerOption option;

    @BeforeEach
    public void setup() {
        submissionRepo.deleteAll();
        optionRepo.deleteAll();
        questionRepo.deleteAll();
        quizRepo.deleteAll();

        // prepare a published quiz, question & answer option
        publishedQuiz = new Quiz();
        publishedQuiz.setName("Test Quiz");
        publishedQuiz.setCourseCode("C123");
        publishedQuiz.setPublished(true);
        publishedQuiz = quizRepo.save(publishedQuiz);

        question = new Question();
        question.setContent("Q?");
        question.setDifficulty(Question.Difficulty.EASY);
        question.setQuiz(publishedQuiz);
        question = questionRepo.save(question);

        option = new AnswerOption();
        option.setContent("Yes");
        option.setCorrect(true);
        option.setQuestion(question);
        option = optionRepo.save(option);
    }

    @Test
    public void createAnswerSavesAnswerForPublishedQuiz() throws Exception {
        AnswerRequest req = new AnswerRequest();
        req.quizId = publishedQuiz.getId();
        req.questionId = question.getId();
        req.answerOptionId = option.getId();

        mockMvc.perform(post("/api/answers/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correct").value(true));

        assertThat(submissionRepo.findAll())
                .hasSize(1)
                .first()
                .satisfies(s -> {
                    assertThat(s.getQuiz().getId()).isEqualTo(publishedQuiz.getId());
                    assertThat(s.getQuestion().getId()).isEqualTo(question.getId());
                    assertThat(s.isCorrect()).isTrue();
                });
    }

    @Test
    public void createAnswerDoesNotSaveAnswerWithoutAnswerOption() throws Exception {
        AnswerRequest req = new AnswerRequest();
        req.quizId = publishedQuiz.getId();
        req.questionId = question.getId();
        req.answerOptionId = null;

        mockMvc.perform(post("/api/answers/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());

        assertThat(submissionRepo.count()).isZero();
    }

    @Test
    public void createAnswerDoesNotSaveAnswerWithNonExistingOption() throws Exception {
        AnswerRequest req = new AnswerRequest();
        req.quizId = publishedQuiz.getId();
        req.questionId = question.getId();
        req.answerOptionId = 9999L;

        mockMvc.perform(post("/api/answers/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());

        assertThat(submissionRepo.count()).isZero();
    }

    @Test
    public void createAnswerDoesNotSaveAnswerForNonPublishedQuiz() throws Exception {
        // make a non-published quiz
        Quiz draft = new Quiz();
        draft.setName("Draft Quiz");
        draft.setCourseCode("C999");
        draft.setPublished(false);
        draft = quizRepo.save(draft);

        AnswerRequest req = new AnswerRequest();
        req.quizId = draft.getId();
        req.questionId = question.getId();
        req.answerOptionId = option.getId();

        mockMvc.perform(post("/api/answers/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());

        assertThat(submissionRepo.count()).isZero();
    }
}
