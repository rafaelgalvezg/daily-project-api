package dev.rafaelgalvezg.dailyprojectapi.repository;

import dev.rafaelgalvezg.dailyprojectapi.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}