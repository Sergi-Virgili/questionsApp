package com.quizcloud.questions.controllers;

import com.quizcloud.questions.models.Certificate;
import com.quizcloud.questions.models.Question;
import com.quizcloud.questions.repositories.CertificateRepository;
import com.quizcloud.questions.repositories.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private CertificateRepository certificateRepository;

    @Test
    void should_returns_not_Fount_if_id_doesnt_exist() throws Exception {
        mockMvc.perform(get("/quiz/123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void should_returns_only_the_quiz_with_certificate_id() throws Exception {
        Question question = saveValidQuestion();
        Question question2 = saveValidQuestion();

//        mockMvc.perform(get("/quiz/" + question.getCertificate().getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().json("[{\"id\":\"" + question.getId() + "\",\"certificate\":{\"id\":\"" + question.getCertificate().getId() + "\"}}]"));
    }

    private Question saveValidQuestion() {
        Certificate certificate = new Certificate();
        certificate = certificateRepository.save(certificate);

        Question question = new Question();
        question.setCertificate(certificate);

        questionRepository.save(question);
        return question;
    }
}