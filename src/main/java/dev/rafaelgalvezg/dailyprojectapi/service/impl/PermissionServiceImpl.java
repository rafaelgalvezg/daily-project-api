package dev.rafaelgalvezg.dailyprojectapi.service.impl;

import dev.rafaelgalvezg.dailyprojectapi.exception.CustomOptimisticLockException;
import dev.rafaelgalvezg.dailyprojectapi.exception.ModelNotFoundException;
import dev.rafaelgalvezg.dailyprojectapi.model.Permission;
import dev.rafaelgalvezg.dailyprojectapi.repository.PermissionRepository;
import dev.rafaelgalvezg.dailyprojectapi.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private static final String MESSAGE_NOT_FOUND = "ID NOT FOUND: ";
    private final PermissionRepository permissionRepository;

    @Override
    public Permission save(Permission permission){
        return permissionRepository.save(permission);
    }

    @Override
    public Page<Permission> findAll(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }

    @Override
    public Permission findById(Long id) {
        return permissionRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(MESSAGE_NOT_FOUND + id));
    }

    @Override
    public void delete(Long id) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(MESSAGE_NOT_FOUND + id));
        permissionRepository.delete(permission);
    }

    @Override
    @Transactional
    public Permission update(Long id, Permission permission) {
        if (!permissionRepository.existsById(id)) {
            throw new ModelNotFoundException(MESSAGE_NOT_FOUND+ id);
        }
        try {
            return permissionRepository.save(permission);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new CustomOptimisticLockException("The record has been modified by another user. Please reload and try again.");
        }
    }

    @Override
    public List<Permission> findPermissionsByUsername(String username) {
        return permissionRepository.findPermissionsByUsername(username);
    }
}
