package haaga_helia.fi.SoftwareProject1.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import haaga_helia.fi.SoftwareProject1.domain.*;

@Controller
public class QuestionController {

    @Autowired
    private QuizRepository quizRepository;
    
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/quiz/{quizId}/questions")
    public String showQuizQuestions(@PathVariable("quizId") Long quizId, Model model) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        model.addAttribute("currentQuiz", quiz);
        model.addAttribute("questions", questionRepository.findByQuiz(quiz));
        model.addAttribute("newQuestion", new Question()); // Initialize newQuestion
        return "quizquestions";
    }

    @PostMapping("/savequestion")
    public String saveQuestion(@ModelAttribute Question question) {
        // Validate that quiz.id is not null
        if (question.getQuiz() == null || question.getQuiz().getId() == null) {
            throw new IllegalArgumentException("Quiz ID must not be null" + question.toString());
        }
    
        // Fetch the Quiz from the database
        Quiz quiz = quizRepository.findById(question.getQuiz().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID: " + question.getQuiz().getId()));
    
        // Set the managed Quiz object on the Question
        question.setQuiz(quiz);
    
        // Save the Question
        questionRepository.save(question);
    
        return "redirect:/quiz/" + quiz.getId() + "/questions";
    }

    @GetMapping("/deletequestion/{id}")
    public String deleteQuestion(@PathVariable("id") Long id) {
        Question question = questionRepository.findById(id).orElse(null);
        Long quizId = question.getQuiz().getId();
        questionRepository.deleteById(id);
        return "redirect:/quiz/" + quizId + "/questions";
    }
}