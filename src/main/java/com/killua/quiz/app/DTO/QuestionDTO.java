package com.killua.quiz.app.DTO;

import java.util.List;

public record QuestionDTO(int id, String title, List<AnswerDTO> answers) {
}
