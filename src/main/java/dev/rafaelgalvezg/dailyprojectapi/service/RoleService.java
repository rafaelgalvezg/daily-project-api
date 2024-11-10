package dev.rafaelgalvezg.dailyprojectapi.service;

import dev.rafaelgalvezg.dailyprojectapi.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Role save(Role role);
    Role update(Long id, Role role);
    Role findById(Long id);
    void delete(Long id);
    Page<Role> findAll(Pageable pageable);
}
