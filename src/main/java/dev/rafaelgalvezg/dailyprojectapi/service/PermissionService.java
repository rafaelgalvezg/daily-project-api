package dev.rafaelgalvezg.dailyprojectapi.service;

import dev.rafaelgalvezg.dailyprojectapi.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissionService {
    Permission save(Permission permission);
    Permission update(Long id, Permission permission);
    Permission findById(Long id);
    void delete(Long id);
    Page<Permission> findAll(Pageable pageable);
}
