package com.quizcloud.questions.controllers;

import com.quizcloud.questions.models.Question;
import com.quizcloud.questions.repositories.QuestionRepository;
import jakarta.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class QuizController {
    private final QuestionRepository questionRepository;

    public QuizController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    @GetMapping("/quiz/{id}")
    public ResponseEntity<List<Question>> getQuizByCertificationId(@PathVariable String id) {
        var certificateId = UUID.fromString(id);
        var quiz = questionRepository.findByCertificate_Id(certificateId);
        if (quiz.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quiz);

    }
}
