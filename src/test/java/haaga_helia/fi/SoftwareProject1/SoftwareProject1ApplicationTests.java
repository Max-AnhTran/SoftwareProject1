package haaga_helia.fi.SoftwareProject1;

import haaga_helia.fi.SoftwareProject1.web.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SoftwareProject1ApplicationTests {

	@Autowired
	private CategoryController categoryController;
	@Autowired
	private AnswerOptionController answerOptionController;
	@Autowired
	private AnswerSubmissionRestController answerSubmissionRestController;
	@Autowired
	private CategoryRestController categoryRestController;
	@Autowired
	private QuestionController questionController;
	@Autowired
	private QuizController quizController;
	@Autowired
	private QuizRestController quizRestController;
	@Autowired
	private QuizResultsRestController quizResultsRestController;
	@Autowired
	private ReviewRestController reviewRestController;

	@Test
	void contextLoads_CategoryController() {
		assertThat(categoryController).isNotNull();
	}

	@Test
	void contextLoads_AnswerOptionController() {
		assertThat(answerOptionController).isNotNull();
	}

	@Test
	void contextLoads_AnswerSubmissionRestController() {
		assertThat(answerSubmissionRestController).isNotNull();
	}

	@Test
	void contextLoads_CategoryRestController() {
		assertThat(categoryRestController).isNotNull();
	}

	@Test
	void contextLoads_QuestionController() {
		assertThat(questionController).isNotNull();
	}

	@Test
	void contextLoads_QuizController() {
		assertThat(quizController).isNotNull();
	}

	@Test
	void contextLoads_QuizRestController() {
		assertThat(quizRestController).isNotNull();
	}

	@Test
	void contextLoads_QuizResultsRestController() {
		assertThat(quizResultsRestController).isNotNull();
	}

	@Test
	void contextLoads_ReviewRestController() {
		assertThat(reviewRestController).isNotNull();
	}
}
