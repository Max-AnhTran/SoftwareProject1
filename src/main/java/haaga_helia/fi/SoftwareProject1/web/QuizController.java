package haaga_helia.fi.SoftwareProject1.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import haaga_helia.fi.SoftwareProject1.domain.QuizRepository;
import haaga_helia.fi.SoftwareProject1.domain.CategoryRepository;
import haaga_helia.fi.SoftwareProject1.domain.Quiz;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class QuizController {
    //soon to be added
    @Autowired
    private QuizRepository qrepository;

    @Autowired
    private CategoryRepository crepository;


    @RequestMapping(value = {"/quizzes", "/",})
    public String quizList(Model model) { 
        model.addAttribute("quizzes", qrepository.findAll());
        return "quizlist";
    }

    @RequestMapping("/create") //alter later
    public String createQuiz(Model model) {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("categories", crepository.findAll());
        //UNFINISHED, IGNORE UNTIL THYMELEAF TEMPLATES ADDED
        return "createquiz";
    }
    @PostMapping("/save")
    public String saveQuiz(Quiz quiz) {
        qrepository.save(quiz);
        return "redirect:quizzes";
        //should redirect to the default page
    }
    //experimental
    @GetMapping("/delete/{id}")
    public String deleteQuiz(@PathVariable("id") Long id, Model model) {
        qrepository.deleteById(id);
        return "redirect:../quizzes";
    }
    @GetMapping("/edit/{id}")
    public String editQuiz(@PathVariable("id") Long id, Model model) {
        model.addAttribute("quiz", qrepository.findById(id));
        model.addAttribute("categories", crepository.findAll());
        return "editquiz";
    }
    
    
    

    
    

    
}
