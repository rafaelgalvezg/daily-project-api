package dev.rafaelgalvezg.dailyprojectapi;

import dev.rafaelgalvezg.dailyprojectapi.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;

public class PermissionTestFactory {
    public static Permission createPermission() {
        Permission permission = new Permission();
        permission.setIdPermission(1L);
        permission.setName("Test Permission");
        permission.setAction("READ");
        permission.setUrl("/test");
        permission.setIcon("test-icon");
        return permission;
    }

    public static Page<Permission> createPermissionsPage() {
        List<Permission> permissions = Collections.singletonList(createPermission());
        return new PageImpl<>(permissions);
    }
}
