package dev.rafaelgalvezg.dailyprojectapi.service;

import dev.rafaelgalvezg.dailyprojectapi.model.ChangeTracking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChangeTrackingService {
    ChangeTracking save(ChangeTracking changeTracking);
    ChangeTracking update(Long id, ChangeTracking changeTracking);
    ChangeTracking findById(Long id);
    void delete(Long id);
    Page<ChangeTracking> findAll(Pageable pageable);
}