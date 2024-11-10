package dev.rafaelgalvezg.dailyprojectapi.repository;

import dev.rafaelgalvezg.dailyprojectapi.model.ChangeTracking;
import dev.rafaelgalvezg.dailyprojectapi.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChangeTrackingRepository extends JpaRepository<ChangeTracking, Long> , JpaSpecificationExecutor<Tag> {

}