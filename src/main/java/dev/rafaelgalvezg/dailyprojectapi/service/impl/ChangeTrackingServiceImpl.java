package dev.rafaelgalvezg.dailyprojectapi.service.impl;

import dev.rafaelgalvezg.dailyprojectapi.exception.CustomOptimisticLockException;
import dev.rafaelgalvezg.dailyprojectapi.exception.ModelNotFoundException;
import dev.rafaelgalvezg.dailyprojectapi.model.ChangeTracking;
import dev.rafaelgalvezg.dailyprojectapi.repository.ChangeTrackingRepository;
import dev.rafaelgalvezg.dailyprojectapi.service.ChangeTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeTrackingServiceImpl implements ChangeTrackingService {

    private static final String MESSAGE_NOT_FOUND = "ID NOT FOUND: ";
    private final ChangeTrackingRepository changeTrackingRepository;

    @Override
    public ChangeTracking save(ChangeTracking changeTracking){
        return changeTrackingRepository.save(changeTracking);
    }

    @Override
    public Page<ChangeTracking> findAll(Pageable pageable) {
        return changeTrackingRepository.findAll(pageable);
    }

    @Override
    public ChangeTracking findById(Long id) {
        return changeTrackingRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(MESSAGE_NOT_FOUND + id));
    }

    @Override
    public void delete(Long id) {
        ChangeTracking changeTracking = changeTrackingRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(MESSAGE_NOT_FOUND + id));
        changeTrackingRepository.delete(changeTracking);
    }

    @Override
    public ChangeTracking update(Long id, ChangeTracking changeTrackingUpdate) {
        if(!changeTrackingRepository.existsById(id)){
            throw new ModelNotFoundException(MESSAGE_NOT_FOUND + id);
        }
        try {
            return changeTrackingRepository.save(changeTrackingUpdate);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new CustomOptimisticLockException("The record has been modified by another user. Please reload and try again.");
        }
    }

}
