package dev.rafaelgalvezg.dailyprojectapi.service.impl;

import dev.rafaelgalvezg.dailyprojectapi.dto.MemberRoleDto;
import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectDto;
import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectTeamDto;
import dev.rafaelgalvezg.dailyprojectapi.exception.CustomOptimisticLockException;
import dev.rafaelgalvezg.dailyprojectapi.exception.ModelNotFoundException;
import dev.rafaelgalvezg.dailyprojectapi.mapper.ProjectDtoMapper;
import dev.rafaelgalvezg.dailyprojectapi.mapper.ProjectTeamDtoMapper;
import dev.rafaelgalvezg.dailyprojectapi.model.Collaborator;
import dev.rafaelgalvezg.dailyprojectapi.model.Project;
import dev.rafaelgalvezg.dailyprojectapi.model.ProjectTeam;
import dev.rafaelgalvezg.dailyprojectapi.model.ProjectTeamId;
import dev.rafaelgalvezg.dailyprojectapi.repository.CollaboratorRepository;
import dev.rafaelgalvezg.dailyprojectapi.repository.ProjectRepository;
import dev.rafaelgalvezg.dailyprojectapi.repository.ProjectTeamRepository;
import dev.rafaelgalvezg.dailyprojectapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final ProjectTeamRepository projectTeamRepository;
    private final ProjectDtoMapper projectMapper;
    private final ProjectTeamDtoMapper projectTeamMapper;

    @Override
    @Transactional
    public ProjectTeamDto save(ProjectTeamDto projectTeamDto) {
        Project project = projectRepository.save(projectMapper.toEntity(projectTeamDto.getProject()));

        projectTeamDto.getMembers().forEach(memberRoleDto -> {
            Collaborator collaborator = collaboratorRepository.findById(memberRoleDto.getMember().getIdCollaborator())
                    .orElseThrow(() -> new ModelNotFoundException("COLLABORATOR NOT FOUND ID: " + memberRoleDto.getMember().getIdCollaborator()));
            ProjectTeam projectTeam = projectTeamMapper.toEntity(memberRoleDto, project, collaborator);
            projectTeamRepository.save(projectTeam);
        });

        List<ProjectTeam> members = projectTeamRepository.findByProject_IdProject(project.getIdProject());
        return projectTeamMapper.toDto(project, members);
    }

    @Override
    @Transactional
    public ProjectTeamDto update(Long projectId, List<MemberRoleDto> members) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ModelNotFoundException("PROJECT NOT FOUND ID: " + projectId));

        Map<Long, ProjectTeam> currentProjectTeams = projectTeamRepository.findByProject_IdProject(projectId)
                .stream().collect(Collectors.toMap(pt -> pt.getCollaborator().getIdCollaborator(), pt -> pt));

        members.forEach(memberRoleDto -> {
            Long collaboratorId = memberRoleDto.getMember().getIdCollaborator();
            if (currentProjectTeams.containsKey(collaboratorId)) {
                ProjectTeam projectTeam = currentProjectTeams.get(collaboratorId);
                if (!projectTeam.getRole().equals(memberRoleDto.getRole())) {
                    projectTeam.setRole(memberRoleDto.getRole());
                    projectTeamRepository.save(projectTeam);
                }
                currentProjectTeams.remove(collaboratorId);
            } else {
                Collaborator collaborator = collaboratorRepository.findById(collaboratorId)
                        .orElseThrow(() -> new ModelNotFoundException("COLLABORATOR NOT FOUND ID: " + collaboratorId));
                ProjectTeam projectTeam = new ProjectTeam(new ProjectTeamId(projectId, collaboratorId), project, collaborator, memberRoleDto.getRole());
                projectTeamRepository.save(projectTeam);
            }
        });
        projectTeamRepository.deleteAll(currentProjectTeams.values());
        List<ProjectTeam> updatedMembers = projectTeamRepository.findByProject_IdProject(projectId);
        return projectTeamMapper.toDto(project, updatedMembers);
    }

    @Override
    public ProjectTeamDto findById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ModelNotFoundException("PROJECT NOT FOUND ID: " + projectId));
        List<ProjectTeam> members = projectTeamRepository.findByProject_IdProject(projectId);
        return projectTeamMapper.toDto(project, members);
    }

    @Override
    public Page<ProjectTeamDto> findAll(Pageable pageable) {
        Page<Project> projectPage = projectRepository.findAll(pageable);
        List<ProjectTeamDto> projectMembersDto = projectPage.stream()
                .map(project -> {
                    List<ProjectTeam> members = projectTeamRepository.findByProject_IdProject(project.getIdProject());
                    return projectTeamMapper.toDto(project, members);
                }).toList();
        return new PageImpl<>(projectMembersDto, pageable, projectPage.getTotalElements());
    }

    @Override
    @Transactional
    public void delete(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ModelNotFoundException("PROJECT NOT FOUND ID: " + projectId);
        }
        projectTeamRepository.deleteByProject_IdProject(projectId);
        projectRepository.deleteById(projectId);
    }

    @Override
    @Transactional
    public ProjectTeamDto updateProjectDetails(Long projectId, ProjectDto projectDto) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ModelNotFoundException("PROJECT NOT FOUND ID: " + projectId));
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setStartDate(projectDto.getStartDate());
        project.setEndDate(projectDto.getEndDate());
        project.setStatus(projectDto.getStatus());
        project.setVersion(projectDto.getVersion());
        try {
            Project projectSave = projectRepository.save(project);
            List<ProjectTeam> members = projectTeamRepository.findByProject_IdProject(projectId);
            return projectTeamMapper.toDto(projectSave, members);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new CustomOptimisticLockException("The record has been modified by another user. Please reload and try again.");
        }
    }
}
