package com.killua.quiz.app.service;

import com.killua.quiz.app.DTO.*;
import com.killua.quiz.app.entity.Answer;
import com.killua.quiz.app.entity.Question;
import com.killua.quiz.app.entity.Quiz;
import com.killua.quiz.app.exception.QuizNotFoundException;
import com.killua.quiz.app.repository.QuizRepository;
import com.killua.quiz.app.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.*;
import static java.util.stream.Collectors.*;

@Service
public class QuizService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;


    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    public List<QuizDTO> retrieveAllQuizzes() {
        List<QuizDTO> quizDTOS =  quizRepository.findAll()
                .stream()
                .map(quiz -> new QuizDTO(quiz.getId(), quiz.getTitle(), filterQuestions(quiz), quiz.getNumberOfQuestions()))
                .toList();
        quizDTOS.forEach(QuizService::formatQuiz);
        return quizDTOS;
    }


    public ResponseEntity<Quiz> createQuiz(String category, int numberOfQuestions, String title) {
        List<Question> questions = questionRepository.findRandomQuestions(category, numberOfQuestions);
        Quiz quiz = new Quiz(title, questions, questions.size());
        quizRepository.save(quiz);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteQuiz(int id) {
        quizRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public QuizDTO retrieveQuiz(int id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException("Quiz Not Found"));
        List<QuestionDTO> questions = filterQuestions(quiz);
        QuizDTO quizDTO =  new QuizDTO(id, quiz.getTitle(), questions, quiz.getNumberOfQuestions());
        formatQuiz(quizDTO);
        return quizDTO;
    }

    private static List<QuestionDTO> filterQuestions(Quiz quiz) {
        return quiz.getQuestions()
                .stream()
                .map (
                        question -> new QuestionDTO(question.getId(), question.getTitle(),
                                question.getAnswers()
                                    .stream()
                                    .map(
                                            answer -> new AnswerDTO(answer.getId(), answer.getContent())
                                    )
                                    .collect(toList()))
                )
                .collect(toList());
    }

    private static void formatQuiz(QuizDTO quiz) {
        quiz.questions()
                .forEach(question -> shuffle(question.answers()));
        shuffle(quiz.questions());
    }

    public Result calculateResult(int id, List<UserAnswer> userAnswers) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException("Quiz Not Found"));
        int score = 0;
        Map<Integer, Integer> questionResults = new HashMap<>();
        for(Question question: quiz.getQuestions()) {
            int answerId = question.getAnswers().stream()
                    .filter(Answer::isCorrect).findFirst().orElseThrow().getId();
            questionResults.put(question.getId(), answerId);
        }
        for (UserAnswer userAnswer: userAnswers) {
            if(questionResults.containsKey(userAnswer.questionId())) {
                int questionId = userAnswer.questionId();
                if(questionResults.get(questionId) == userAnswer.selectedAnswerId())
                    score++;
            }
        }
        return new Result(id, quiz.getNumberOfQuestions(), score);
    }
}
