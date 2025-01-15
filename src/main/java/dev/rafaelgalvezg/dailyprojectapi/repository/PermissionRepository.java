package dev.rafaelgalvezg.dailyprojectapi.repository;

import dev.rafaelgalvezg.dailyprojectapi.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query(value = """
            select p.* from role_permission rp
            inner join user_role ur on ur.id_role = rp.id_role
            inner join permissions p on p.id_permission = rp.id_permission
            inner join users u on u.id_user = ur.id_user
            where u.username = :username
            """, nativeQuery = true)
    List<Permission> findPermissionsByUsername(@Param("username") String username);

}