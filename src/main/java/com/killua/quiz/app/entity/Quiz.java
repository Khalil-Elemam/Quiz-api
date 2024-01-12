package com.killua.quiz.app.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Quiz {

    @Id
    @SequenceGenerator(name = "quiz_sequence",
            sequenceName = "quiz_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "quiz_sequence")
    private int id;

    @NotNull
    @NotBlank(message = "Quiz title can not be empty")
    private String title;

    @ManyToMany
    @NotNull
    @Size(min = 0, message = "number of questions cannot be less than one")
    private List<Question> questions;

    @Min(value = 1, message = "Quiz number of questions can't be less than 1")
    private int numberOfQuestions;

    public Quiz(String title, List<Question> questions, int numberOfQuestions) {
        this.title = title;
        this.questions = questions;
        this.numberOfQuestions = numberOfQuestions;
    }

    public Quiz(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", questions=" + questions +
                ", numberOfQuestions=" + numberOfQuestions +
                '}';
    }
}
