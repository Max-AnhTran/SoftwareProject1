package haaga_helia.fi.SoftwareProject1.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import haaga_helia.fi.SoftwareProject1.domain.*;

@Controller
public class AnswerOptionController {

    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    @GetMapping("/question/{questionId}/answers")
    public String showQuestionAnswers(@PathVariable("questionId") Long questionId, Model model) {
        Question question = questionRepository.findById(questionId).orElse(null);
        model.addAttribute("question", question);
        model.addAttribute("answers", answerOptionRepository.findByQuestion(question));
        model.addAttribute("newAnswer", new AnswerOption()); // Add newAnswer to the model
        return "questionanswers";
    }

    @PostMapping("/saveanswer")
    public String saveAnswer(@ModelAttribute AnswerOption answerOption) {
        answerOptionRepository.save(answerOption);
        return "redirect:/question/" + answerOption.getQuestion().getId() + "/answers";
    }

    @GetMapping("/deleteanswer/{id}")
    public String deleteAnswer(@PathVariable("id") Long id) {
        AnswerOption answer = answerOptionRepository.findById(id).orElse(null);
        Long questionId = answer.getQuestion().getId();
        answerOptionRepository.deleteById(id);
        return "redirect:/question/" + questionId + "/answers";
    }
}