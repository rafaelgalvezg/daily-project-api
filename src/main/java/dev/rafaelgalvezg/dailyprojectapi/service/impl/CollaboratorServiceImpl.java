package dev.rafaelgalvezg.dailyprojectapi.service.impl;

import dev.rafaelgalvezg.dailyprojectapi.exception.CustomOptimisticLockException;
import dev.rafaelgalvezg.dailyprojectapi.exception.ModelNotFoundException;
import dev.rafaelgalvezg.dailyprojectapi.model.Collaborator;
import dev.rafaelgalvezg.dailyprojectapi.repository.CollaboratorRepository;
import dev.rafaelgalvezg.dailyprojectapi.service.CollaboratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollaboratorServiceImpl implements CollaboratorService {

    private final CollaboratorRepository collaboratorRepository;

    @Override
    public Collaborator save(Collaborator collaborator){
        return collaboratorRepository.save(collaborator);
    }

    @Override
    public Page<Collaborator> findAll(Pageable pageable) {
        return collaboratorRepository.findAll(pageable);
    }

    @Override
    public Collaborator findById(Long id) {
        return collaboratorRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
    }

    @Override
    public void delete(Long id) {
        Collaborator collaborator = collaboratorRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        collaboratorRepository.delete(collaborator);
    }

    @Override
    public Collaborator update(Long id, Collaborator collaborator) {
        if (!collaboratorRepository.existsById(id)) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        }
        try {
            return collaboratorRepository.save(collaborator);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new CustomOptimisticLockException("The record has been modified by another user. Please reload and try again.");
        }
    }

}
