package haaga_helia.fi.SoftwareProject1;

import haaga_helia.fi.SoftwareProject1.domain.*;
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
public class QuestionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    @BeforeEach
    public void setup() {
        answerOptionRepository.deleteAll();
        questionRepository.deleteAll();
        quizRepository.deleteAll();
    }

    private Quiz createQuiz(String name, String courseCode, boolean published) {
        Quiz quiz = new Quiz();
        quiz.setName(name);
        quiz.setCourseCode(courseCode);
        quiz.setPublished(published);
        return quizRepository.save(quiz);
    }

    private Question createQuestion(String content, Question.Difficulty difficulty, Quiz quiz) {
        Question question = new Question();
        question.setContent(content);
        question.setDifficulty(difficulty);
        question.setQuiz(quiz);
        return questionRepository.save(question);
    }

    private AnswerOption createAnswerOption(String text, boolean correct, Question question) {
        AnswerOption option = new AnswerOption();
        option.setContent(text);
        option.setCorrect(correct);
        option.setQuestion(question);
        return answerOptionRepository.save(option);
    }

    @Test
    public void getQuestionsByQuizIdReturnsEmptyListWhenQuizDoesNotHaveQuestions() throws Exception {
        Quiz quiz = createQuiz("Empty Quiz", "CODE001", true);

        mockMvc.perform(get("/api/quizzes/" + quiz.getId() + "/questions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.questions", hasSize(0)));
    }

    @Test
    public void getQuestionsByQuizIdReturnsListOfQuestionsWhenQuizHasQuestions() throws Exception {
        Quiz quiz = createQuiz("Quiz with Questions", "CODE002", true);
        Question q1 = createQuestion("What is Java?", Question.Difficulty.EASY, quiz);
        Question q2 = createQuestion("Explain polymorphism.", Question.Difficulty.NORMAL, quiz);

        createAnswerOption("Programming language", true, q1);
        createAnswerOption("Coffee", false, q1);

        createAnswerOption("OOP concept", true, q2);

        mockMvc.perform(get("/api/quizzes/" + quiz.getId() + "/questions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.questions", hasSize(2)))
                .andExpect(jsonPath("$._embedded.questions[0].content", is("What is Java?")))
                .andExpect(jsonPath("$._embedded.questions[1].content", is("Explain polymorphism.")));
    }

    @Test
    public void getQuestionsByQuizIdReturnsErrorWhenQuizDoesNotExist() throws Exception {
        Long nonExistentQuizId = 9999L;

        mockMvc.perform(get("/api/quizzes/" + nonExistentQuizId + "/questions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
