package com.killua.quiz.app.controller;


import com.killua.quiz.app.DTO.QuizDTO;
import com.killua.quiz.app.DTO.UserAnswer;
import com.killua.quiz.app.entity.Quiz;
import com.killua.quiz.app.DTO.Result;
import com.killua.quiz.app.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public List<QuizDTO> retrieveAllQuizzes() {
        return quizService.retrieveAllQuizzes();
    }

    @GetMapping("{id}")
    public QuizDTO retrieveQuiz(@PathVariable int id) {
        return quizService.retrieveQuiz(id);
    }

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestParam @Valid String category,
                                           @RequestParam @Valid int numberOfQuestions,
                                           @RequestParam @Valid String title) {
        return quizService.createQuiz(category, numberOfQuestions, title);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable int id) {
        return quizService.deleteQuiz(id);
    }

    @PostMapping("submit/{id}")
    public Result submitQuiz(@PathVariable int id, @RequestBody List<UserAnswer> userAnswers) {
        return quizService.calculateResult(id, userAnswers);
    }
}
