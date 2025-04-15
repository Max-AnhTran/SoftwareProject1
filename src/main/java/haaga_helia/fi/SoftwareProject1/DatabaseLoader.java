package haaga_helia.fi.SoftwareProject1;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import haaga_helia.fi.SoftwareProject1.domain.*;
import haaga_helia.fi.SoftwareProject1.domain.Question.Difficulty;

@Configuration
public class DatabaseLoader {
    @Bean
    public CommandLineRunner demo(
            QuizRepository quizRepository,
            QuestionRepository questionRepository,
            AnswerOptionRepository answerOptionRepository,
            CategoryRepository categoryRepository) {
        return (args) -> {
            // ========== CREATE CATEGORIES ==========
            Category programming = new Category("Programming", "Programming languages and concepts");
            Category math = new Category("Mathematics", "Math quizzes");
            Category general = new Category("General Knowledge", "General facts");
            categoryRepository.saveAll(List.of(programming, math, general));

            // ========== CREATE QUIZZES ==========
            Quiz javaQuiz = new Quiz();
            javaQuiz.setName("Java Basics");
            javaQuiz.setDescription("Basic concepts of Java programming");
            javaQuiz.setCourseCode("CS101");
            javaQuiz.setPublished(true);
            javaQuiz.setCategory(programming);
            quizRepository.save(javaQuiz);

            Quiz algebraQuiz = new Quiz();
            algebraQuiz.setName("Algebra Fundamentals");
            algebraQuiz.setDescription("Basic algebra concepts");
            algebraQuiz.setCourseCode("MATH101");
            algebraQuiz.setPublished(true);
            algebraQuiz.setCategory(math);
            quizRepository.save(algebraQuiz);

            // ========== CREATE QUESTIONS ==========
            // Questions for Java Quiz
            Question javaQ1 = new Question();
            javaQ1.setContent("What is the main purpose of the JVM?");
            javaQ1.setDifficulty(Difficulty.EASY);
            javaQ1.setQuiz(javaQuiz);
            questionRepository.save(javaQ1);

            Question javaQ2 = new Question();
            javaQ2.setContent("What keyword is used for inheritance in Java?");
            javaQ2.setDifficulty(Difficulty.EASY);
            javaQ2.setQuiz(javaQuiz);
            questionRepository.save(javaQ2);

            // Questions for Algebra Quiz
            Question mathQ1 = new Question();
            mathQ1.setContent("Solve for x: 2x + 5 = 15");
            mathQ1.setDifficulty(Difficulty.NORMAL);
            mathQ1.setQuiz(algebraQuiz);
            questionRepository.save(mathQ1);

            // ========== CREATE ANSWER OPTIONS ==========
            // Answers for Java Q1
            answerOptionRepository.save(new AnswerOption("To compile Java code", false, javaQ1));
            answerOptionRepository.save(new AnswerOption("To execute Java bytecode", true, javaQ1));
            answerOptionRepository.save(new AnswerOption("To write Java documentation", false, javaQ1));

            // Answers for Java Q2
            answerOptionRepository.save(new AnswerOption("implements", false, javaQ2));
            answerOptionRepository.save(new AnswerOption("extends", true, javaQ2));
            answerOptionRepository.save(new AnswerOption("inherits", false, javaQ2));

            // Answers for Math Q1
            answerOptionRepository.save(new AnswerOption("x = 5", true, mathQ1));
            answerOptionRepository.save(new AnswerOption("x = 10", false, mathQ1));
            answerOptionRepository.save(new AnswerOption("x = 7.5", false, mathQ1));

            // Answers for Math Q2 (not created, but you can add more questions and answers
            // as needed)
            for (int i = 3; i <= 20; i++) {
                Question q = new Question();
                q.setContent("Java Question " + i + ": What does this Java concept do?");
                q.setDifficulty(Difficulty.NORMAL);
                q.setQuiz(javaQuiz);
                questionRepository.save(q);

                int correctIndex = (int) Math.floor(Math.random() * 3); // Random number between 1 and 3

                // Create answer options for the question
                answerOptionRepository.save(new AnswerOption("Option A for Q" + i, 1 == correctIndex, q));
                answerOptionRepository.save(new AnswerOption("Option B for Q" + i, 2 == correctIndex, q));
                answerOptionRepository.save(new AnswerOption("Option C for Q" + i, 0 == correctIndex, q));
            }

            System.out.println("Sample data loaded successfully!");
        };
    };
}
