package haaga_helia.fi.SoftwareProject1.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import haaga_helia.fi.SoftwareProject1.domain.CategoryRepository;
import haaga_helia.fi.SoftwareProject1.domain.Quiz;
import haaga_helia.fi.SoftwareProject1.domain.QuizRepository;

@Controller
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String home(Model model) {
        // Get all quizzes sorted by category name
        List<Quiz> quizzes = quizRepository.findAllByOrderByCategory_NameAsc();
        model.addAttribute("quizzes", quizzes);
        
        // Get all categories
        model.addAttribute("categories", categoryRepository.findAll());
        return "homepage";
    }

    // Quiz CRUD methods
    @GetMapping("/addquiz")
    public String addQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addquiz";
    }

    @GetMapping("/editquiz/{id}")
    public String editQuizForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("quiz", quizRepository.findById(id).orElse(null));
        model.addAttribute("categories", categoryRepository.findAll());
        return "editquiz";
    }

    @PostMapping("/savequiz")
    public String saveQuiz(@ModelAttribute Quiz quiz) {
        quizRepository.save(quiz);
        return "redirect:/";
    }

    @GetMapping("/deletequiz/{id}")
    public String deleteQuiz(@PathVariable("id") Long id) {
        quizRepository.deleteById(id);
        return "redirect:/";
    }
}