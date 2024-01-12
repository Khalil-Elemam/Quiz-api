package com.killua.quiz.app.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

public record UserAnswer(int questionId, int selectedAnswerId) {
}
