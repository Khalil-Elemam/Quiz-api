package com.killua.quiz.app.controller;

import com.killua.quiz.app.entity.Question;
import com.killua.quiz.app.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }


    @GetMapping
    public List<Question> retrieveQuestionsByCategory(@RequestParam(required = false) @Valid String category,
                                                      @RequestParam(required = false) @Valid String level) {
        return questionService.retrieveQuestionsByCategoryAndLevel(category, level);
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody @Valid Question question) {
        return questionService.createQuestion(question);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable int id) {
        return questionService.deleteQuestion(id);
    }


}
