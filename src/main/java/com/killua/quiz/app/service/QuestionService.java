package com.killua.quiz.app.service;


import com.killua.quiz.app.entity.Answer;
import com.killua.quiz.app.entity.Question;
import com.killua.quiz.app.repository.AnswerRepository;
import com.killua.quiz.app.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public List<Question> retrieveAllQuestions() {
        return questionRepository.findAll();
    }

    public ResponseEntity<Question> createQuestion(Question question) {
        List<Answer> answers = question.getAnswers();
        questionRepository.save(question);
        answerRepository.saveAll(answers);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public List<Question> retrieveQuestionsByCategoryAndLevel(String category, String level) {
        if(category == null && level == null)
            return questionRepository.findAll();
        else if(category == null)
            return questionRepository.findByLevel(level);
        else if(level == null)
            return questionRepository.findByCategory(category);
        return questionRepository.findByCategoryAndLevel(category, level);
    }

    public ResponseEntity<Void> deleteQuestion(int id) {
        questionRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
