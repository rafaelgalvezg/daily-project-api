package dev.rafaelgalvezg.dailyprojectapi.repository;

import dev.rafaelgalvezg.dailyprojectapi.model.ProjectTeam;
import dev.rafaelgalvezg.dailyprojectapi.model.ProjectTeamId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, ProjectTeamId> {
    List<ProjectTeam> findByProject_IdProject(Long projectId);
    void deleteByProject_IdProject(Long projectId);
}