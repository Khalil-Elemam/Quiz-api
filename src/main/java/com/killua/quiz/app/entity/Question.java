package com.killua.quiz.app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {

    @Id
    @SequenceGenerator(
            name = "question_seq",
            sequenceName = "question_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    private Integer id;

    @NotNull
    @NotBlank(message = "title can not be empty")
    private String title;

    @Pattern(regexp = "^(Beginner|Intermediate|Advanced|beginner|intermediate|advanced)$",
            message = "this level isn't valid")
    private String level;

    @NotBlank(message = "category cannot be empty")
    @NotNull
    private String category;

    @NotNull
    @Size(min = 2, max = 10, message = "question cannot have less than 2 options")
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Answer> answers;

    public Question() {
    }

    public Question(Integer id, String title, String level, String category, List<Answer> answers) {
        this.id = id;
        this.title = title;
        this.level = level;
        this.category = category;
        this.answers = answers;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", level='" + level + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
