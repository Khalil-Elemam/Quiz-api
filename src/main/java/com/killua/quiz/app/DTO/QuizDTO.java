package com.killua.quiz.app.DTO;


import java.util.List;

public record QuizDTO(int id, String title, List<QuestionDTO> questions, int numberOfQuestions) {
}
