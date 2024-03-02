package com.quizcloud.questions.repositories;

import com.quizcloud.questions.models.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DomainRepository extends JpaRepository<Domain, UUID> {
    Optional<Domain> findByName(String name);
}
