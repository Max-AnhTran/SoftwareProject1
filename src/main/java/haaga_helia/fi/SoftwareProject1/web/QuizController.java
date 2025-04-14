package haaga_helia.fi.SoftwareProject1.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import haaga_helia.fi.SoftwareProject1.domain.QuizRepository;
import haaga_helia.fi.SoftwareProject1.domain.CategoryRepository;
import haaga_helia.fi.SoftwareProject1.domain.AnswerOptionRepository;
import haaga_helia.fi.SoftwareProject1.domain.Category;
import haaga_helia.fi.SoftwareProject1.domain.Quiz;
import haaga_helia.fi.SoftwareProject1.domain.Question;
import haaga_helia.fi.SoftwareProject1.domain.QuestionRepository;
import haaga_helia.fi.SoftwareProject1.domain.AnswerOption;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuizController {
    //soon to be added
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    @RequestMapping(value = {"/quizzes", "/", "/index"})
    public String quizList(Model model) { 
        model.addAttribute("quizzes", quizRepository.findAll());
        return "quizlist";
    }

    @RequestMapping("quizzes/createquiz") 
    public String createQuiz(Model model) {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("categories", categoryRepository.findAll());
        return "createquiz";
    }
    @PostMapping("quizzes/savequiz")
    public String saveQuiz(Quiz quiz) {
        quizRepository.save(quiz);
        return "redirect:/quizzes";
    }
    
    @GetMapping("quizzes/delete/{id}") 
    public String deleteQuiz(@PathVariable("id") Long id, Model model) {
        quizRepository.deleteById(id);
        return "redirect:/quizzes";
    }
    @GetMapping("quizzes/edit/{id}")
    public String editQuiz(@PathVariable("id") Long id, Model model) {
        model.addAttribute("quiz", quizRepository.findById(id));
        model.addAttribute("categories", categoryRepository.findAll());
        return "editquiz";
    }
    @RequestMapping("/createcategory")
    public String addCategory(Model model) {
        model.addAttribute("category",new Category());
        return "createcategory";
    }
    
    @PostMapping("/savecategory")
    public String saveCategory(Category category) {
        categoryRepository.save(category);
        return "redirect:quizzes";
    }

    //BOTH CATEGORY END POINTS SHOULD BE CHANGED
    @RequestMapping("quizzes/{id}/questions")
    public String questionList(@PathVariable("id") Long quizId, Model model) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz == null) {
            return "redirect:/quizzes";
        }
        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questionRepository.findByQuiz(quiz));
        return "questionlist";
    }
    @GetMapping("quizzes/{id}/questions/createquestion")
    public String createQuestion(@PathVariable("id") Long quizId, Model model) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz == null) {
            return "redirect:/quizzes";
        }
        
        Question question = new Question();
        question.setQuiz(quiz);
        model.addAttribute("quiz", quiz);
        model.addAttribute("question", question);
        return "createquestion";
    }
    @PostMapping("/quizzes/{id}/questions/savequestion")
    public String postMethodName(@PathVariable("id") Long quizId, @ModelAttribute Question question) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz == null) {
            return "redirect:/quizzes";
        }
        question.setId(null);
        question.setQuiz(quiz);
        questionRepository.save(question);
        return "redirect:/quizzes/" + question.getQuiz().getId() + "/questions";
    }
    
    @GetMapping("/quizzes/{quizId}/questions/delete/{questionId}")
    public String deleteQuestion(@PathVariable("quizId") Long quizId, @PathVariable("questionId") Long questionId) {
        questionRepository.deleteById(questionId);
        return "redirect:/quizzes/" + quizId + "/questions";
    }
    @GetMapping("quizzes/{quizId}/questions/{questionId}/answers")
    public String answerList(@PathVariable("quizId") Long quizId, @PathVariable("questionId") Long questionId, Model model) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            return "redirect:/quizzes";}
        model.addAttribute("question", question);
        model.addAttribute("answerOptions", answerOptionRepository.findByQuestion(question));
        model.addAttribute("answerOption", new AnswerOption());
        return "answerlist";
    }
    
    @PostMapping("/quizzes/{quizId}/questions/{questionId}/answers/save")
    public String saveAnswerOption(@PathVariable Long quizId,
            @PathVariable Long questionId,
            @ModelAttribute AnswerOption answerOption) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null)
            return "redirect:/quizzes/" + quizId + "/questions";

        answerOption.setQuestion(question);
        answerOptionRepository.save(answerOption);
        return "redirect:/quizzes/" + quizId + "/questions/" + questionId + "/answers";
    }
    
    @GetMapping("/quizzes/{quizId}/questions/{questionId}/answers/delete/{answerId}")
    public String deleteAnswerOption(@PathVariable Long quizId,
            @PathVariable Long questionId,
            @PathVariable Long answerId) {
        answerOptionRepository.deleteById(answerId);
        return "redirect:/quizzes/" + quizId + "/questions/" + questionId + "/answers";
    }
    
    
}
