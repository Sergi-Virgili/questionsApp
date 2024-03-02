package com.quizcloud.questions.repositories;

import com.quizcloud.questions.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID>{


    List<Question> findByCertificate_Id(UUID id);
}
