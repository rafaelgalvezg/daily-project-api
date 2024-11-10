package dev.rafaelgalvezg.dailyprojectapi.repository;

import dev.rafaelgalvezg.dailyprojectapi.model.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {
}