package com.killua.quiz.app.repository;

import com.killua.quiz.app.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{
    List<Question> findByCategoryAndLevel(String category, String level);

    List<Question> findByLevel(String level);

    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM question WHERE question.category=:category ORDER BY RANDOM() LIMIT :numberOfQuestions", nativeQuery = true)
    List<Question> findRandomQuestions(String category, int numberOfQuestions);
}
