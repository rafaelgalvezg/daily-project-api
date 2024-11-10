package dev.rafaelgalvezg.dailyprojectapi.repository;

import dev.rafaelgalvezg.dailyprojectapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}