package com.quizcloud.questions.controllers;

import com.quizcloud.questions.models.Question;
import com.quizcloud.questions.repositories.CertificateRepository;
import com.quizcloud.questions.repositories.DomainRepository;
import com.quizcloud.questions.repositories.QuestionRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Transactional
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final CertificateRepository certificateRepository;
    private final DomainRepository domainRepository;

    public QuestionController(QuestionRepository questionRepository, CertificateRepository certificateRepository, DomainRepository domainRepository) {
        this.questionRepository = questionRepository;
        this.certificateRepository = certificateRepository;
        this.domainRepository = domainRepository;
    }

    @PostMapping("/question")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question) {
        var purgetQuestion = certificationPurge(question);

        var createdQuestion = questionRepository.save(purgetQuestion);

        return ResponseEntity
                .created(URI.create("/question/" + createdQuestion.getId()))
                .body(createdQuestion);
    }

    private Question certificationPurge(Question question) {
        var existingCertificate = certificateRepository.findByName(question.getCertificate().getName());
        existingCertificate.ifPresent(question::setCertificate);

        if (question.getDomain() == null) {
            return question;
        }

        var existingDomain = domainRepository.findByName(question.getDomain().getName());
        existingDomain.ifPresent(question::setDomain);
        return question;
    }

    @PostMapping("/questions")
    public Iterable<Question> createQuestions(@RequestBody Iterable<Question> questions) {
        questions.forEach(this::certificationPurge);

        questionRepository.saveAll(questions);

        return questions;
    }

    @CrossOrigin("*")
    @GetMapping("/questions")
    public Iterable<Question> getQuestions() {
        return questionRepository.findAll();
    }
}
