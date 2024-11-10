package dev.rafaelgalvezg.dailyprojectapi.repository;

import dev.rafaelgalvezg.dailyprojectapi.model.Project;
import dev.rafaelgalvezg.dailyprojectapi.model.ProjectTeam;
import dev.rafaelgalvezg.dailyprojectapi.model.ProjectTeamId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, ProjectTeamId> {
    void deleteAllByProject(Project project);
    List<ProjectTeam> findAllByProject(Project project);
}