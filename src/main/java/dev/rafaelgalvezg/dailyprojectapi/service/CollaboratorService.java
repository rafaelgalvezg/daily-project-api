package dev.rafaelgalvezg.dailyprojectapi.service;

import dev.rafaelgalvezg.dailyprojectapi.model.Collaborator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollaboratorService {
    Collaborator save(Collaborator collaborator);
    Collaborator update(Long id, Collaborator collaborator);
    Collaborator findById(Long id);
    void delete(Long id);
    Page<Collaborator> findAll(Pageable pageable);
}
