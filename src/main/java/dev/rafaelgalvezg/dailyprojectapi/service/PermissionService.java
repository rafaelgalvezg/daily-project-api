package dev.rafaelgalvezg.dailyprojectapi.service;

import dev.rafaelgalvezg.dailyprojectapi.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PermissionService {
    Permission save(Permission permission);
    Permission update(Long id, Permission permission);
    Permission findById(Long id);
    void delete(Long id);
    Page<Permission> findAll(Pageable pageable);
    List<Permission> findPermissionsByUsername(String username);
}
