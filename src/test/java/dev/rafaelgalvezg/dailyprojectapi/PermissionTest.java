package dev.rafaelgalvezg.dailyprojectapi;

import dev.rafaelgalvezg.dailyprojectapi.exception.CustomOptimisticLockException;
import dev.rafaelgalvezg.dailyprojectapi.exception.ModelNotFoundException;
import dev.rafaelgalvezg.dailyprojectapi.model.Permission;
import dev.rafaelgalvezg.dailyprojectapi.repository.PermissionRepository;
import dev.rafaelgalvezg.dailyprojectapi.service.impl.PermissionServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionTest {

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @Mock
    private PermissionRepository permissionRepository;

    @Test
    @DisplayName("Test save permission successfully")
    void testSave() {
        Permission permission = PermissionTestFactory.createPermission();
        when(permissionRepository.save(permission)).thenReturn(permission);

        Permission savedPermission = permissionService.save(permission);
        assertThat(savedPermission).isNotNull();
        assertThat(savedPermission.getName()).isEqualTo(permission.getName());
        assertSame(savedPermission, permission, "The saved permission is the same as the permission");
        assertNotNull(savedPermission, "The saved permission is not null");

        verify(permissionRepository, times(1)).save(permission);
    }

    @Test
    @DisplayName("Test findAll successfully")
    void testFindAll() {
        Page<Permission> permissionsPage = PermissionTestFactory.createPermissionsPage();
        Pageable pageable = PageRequest.of(0, 10);
        when(permissionRepository.findAll(pageable)).thenReturn(permissionsPage);

        Page<Permission> resultPage = permissionService.findAll(pageable);
        assertThat(resultPage).isNotEmpty();
        assertThat(resultPage.getContent()).hasSize(1);
        assertThat(resultPage.getContent().getFirst().getName()).isEqualTo("Test Permission");

        verify(permissionRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test findById successfully")
    void testFindById() {
        Permission permission = PermissionTestFactory.createPermission();
        when(permissionRepository.findById(permission.getIdPermission())).thenReturn(Optional.of(permission));

        Permission result = permissionService.findById(permission.getIdPermission());
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test Permission");

        verify(permissionRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test findById throws exception")
    void testFindByIdThrowsException() {
        Long id = 1L;
        when(permissionRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> permissionService.findById(id))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessage("ID NOT FOUND: " + id);

        verify(permissionRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test delete successfully")
    void testDelete() {
        Permission permission = PermissionTestFactory.createPermission();
        when(permissionRepository.findById(permission.getIdPermission())).thenReturn(Optional.of(permission));

        permissionService.delete(permission.getIdPermission());

        verify(permissionRepository, times(1)).delete(permission);
    }

    @Test
    @DisplayName("Test delete throws exception")
    void testDeleteThrowsException() {
        Long id = 1L;
        when(permissionRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> permissionService.delete(id))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessage("ID NOT FOUND: " + id);

        verify(permissionRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test update successfully")
    void testUpdate() {
        Permission permission = PermissionTestFactory.createPermission();
        when(permissionRepository.existsById(permission.getIdPermission())).thenReturn(true);
        when(permissionRepository.save(permission)).thenReturn(permission);

        Permission updatedPermission = permissionService.update(permission.getIdPermission(), permission);
        assertThat(updatedPermission).isNotNull();
        assertThat(updatedPermission.getName()).isEqualTo(permission.getName());

        verify(permissionRepository, times(1)).existsById(permission.getIdPermission());
        verify(permissionRepository, times(1)).save(permission);
    }

    @Test
    @DisplayName("Test update throws exception")
    void testUpdateThrowsException() {
        Permission permission = PermissionTestFactory.createPermission();
        Long id = permission.getIdPermission();
        when(permissionRepository.existsById(permission.getIdPermission())).thenReturn(false);

        assertThatThrownBy(() -> permissionService.update(id, permission))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessage("ID NOT FOUND: " + id);

        verify(permissionRepository, times(1)).existsById(id);
    }

    @Test
    @DisplayName("Should throw CustomOptimisticLockException on optimistic lock failure")
    void testUpdateThrowsCustomOptimisticLockException() {
        Permission permission = PermissionTestFactory.createPermission();
        Long id = permission.getIdPermission();
        when(permissionRepository.existsById(id)).thenReturn(true);
        when(permissionRepository.save(any())).thenThrow(ObjectOptimisticLockingFailureException.class);

        assertThrows(CustomOptimisticLockException.class, () -> permissionService.update(id, permission));
    }

    @Test
    @DisplayName("Test findPermissionsByUsername successfully")
    void testFindPermissionsByUsername() {
        String username = "test";
        Permission permission = PermissionTestFactory.createPermission();
        when(permissionRepository.findPermissionsByUsername(username)).thenReturn(List.of(permission));

        permissionService.findPermissionsByUsername(username);

        verify(permissionRepository, times(1)).findPermissionsByUsername(username);
    }



}
