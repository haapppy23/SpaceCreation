package com.aws.spacecreation.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = questionService.getAllQuestions();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(Model model) {
        model.addAttribute("question", new Question());
        return "question_form";
    }

    @PostMapping("/create")
    public String questionCreate(@ModelAttribute Question question) {
        questionService.create(question);
        return "redirect:/question/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        questionService.delete(id);
        return "redirect:/question/list";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable("id")Integer id) {
    	questionService.deleteQuestion(id);
    	return "redirect:/question/list";
    }
}
