package dev.rafaelgalvezg.dailyprojectapi.service;

import dev.rafaelgalvezg.dailyprojectapi.dto.MemberRoleDto;
import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectDto;
import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectTeamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
        ProjectTeamDto save(ProjectTeamDto projectTeamDto);
        ProjectTeamDto update(Long projectId, List<MemberRoleDto> members);
        ProjectTeamDto findById(Long projectId);
        Page<ProjectTeamDto> findAll(Pageable pageable);
        void delete(Long projectId);
        ProjectTeamDto updateProjectDetails(Long projectId, ProjectDto projectDto);
}
