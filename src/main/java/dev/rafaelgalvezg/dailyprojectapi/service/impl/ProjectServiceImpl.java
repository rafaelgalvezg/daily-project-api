package dev.rafaelgalvezg.dailyprojectapi.service.impl;

import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectDto;
import dev.rafaelgalvezg.dailyprojectapi.exception.CustomOptimisticLockException;
import dev.rafaelgalvezg.dailyprojectapi.exception.ModelNotFoundException;
import dev.rafaelgalvezg.dailyprojectapi.mapper.ProjectDtoMapper;
import dev.rafaelgalvezg.dailyprojectapi.mapper.ProjectTeamDtoMapper;
import dev.rafaelgalvezg.dailyprojectapi.model.Collaborator;
import dev.rafaelgalvezg.dailyprojectapi.model.Project;
import dev.rafaelgalvezg.dailyprojectapi.model.ProjectTeam;
import dev.rafaelgalvezg.dailyprojectapi.repository.CollaboratorRepository;
import dev.rafaelgalvezg.dailyprojectapi.repository.ProjectRepository;
import dev.rafaelgalvezg.dailyprojectapi.repository.ProjectTeamRepository;
import dev.rafaelgalvezg.dailyprojectapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final ProjectTeamRepository projectTeamRepository;
    private final ProjectDtoMapper projectDtoMapper;
    private final ProjectTeamDtoMapper projectTeamDtoMapper;

    @Override
    @Transactional
    public ProjectDto save(ProjectDto projectDto) {
        Project project = projectRepository.save(projectDtoMapper.toEntity(projectDto));

        project.getTeamMembers().forEach(projectTeam -> {
            Collaborator collaborator = collaboratorRepository.findById(projectTeam.getCollaborator().getIdCollaborator())
                    .orElseThrow(() -> new ModelNotFoundException("COLLABORATOR NOT FOUND: " + projectTeam.getCollaborator().getIdCollaborator()));
            projectTeam.setProject(project);
            projectTeam.setCollaborator(collaborator);
            projectTeamRepository.save(projectTeam);
        });

        return this.findById(project.getIdProject());
    }

    @Override
    @Transactional
    public ProjectDto update(ProjectDto projectDto) {
        Project project = projectRepository.findById(projectDto.getIdProject())
                .orElseThrow(() -> new ModelNotFoundException("PROJECT NOT FOUND: " + projectDto.getIdProject()));

        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setStartDate(projectDto.getStartDate());
        project.setEndDate(projectDto.getEndDate());
        project.setVersion(projectDto.getVersion());

        try {
            projectRepository.save(project);
        } catch (OptimisticEntityLockException ex) {
            throw new CustomOptimisticLockException("The record has been modified by another user. Please reload and try again.");
        }

        //TODO: Refactor this code to avoid deleting and inserting all the project team members
        projectTeamRepository.deleteAllByProject(project);

        projectTeamDtoMapper.toEntityList(projectDto.getMembers(), project).forEach(projectTeam -> {
            Collaborator collaborator = collaboratorRepository.findById(projectTeam.getCollaborator().getIdCollaborator())
                    .orElseThrow(() -> new ModelNotFoundException("COLLABORATOR NOT FOUND: " + projectTeam.getCollaborator().getIdCollaborator()));
            projectTeam.setProject(project);
            projectTeam.setCollaborator(collaborator);
            projectTeamRepository.save(projectTeam);
        });

        return this.findById(project.getIdProject());
    }

    @Override
    public ProjectDto findById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("PROJECT NOT FOUND: " + id));

        ProjectDto projectDto = projectDtoMapper.toDto(project);
        List<ProjectTeam> projectTeams = projectTeamRepository.findAllByProject(project);
        projectDto.setMembers(projectTeamDtoMapper.toDtoList(projectTeams));

        return projectDto;
    }

    @Override
    public Page<ProjectDto> findAll(Pageable pageable) {
        Page<Project> projectPage = projectRepository.findAll(pageable);
        List<ProjectDto> projects = projectPage.stream()
                .map(project -> {
                    ProjectDto projectDto = projectDtoMapper.toDto(project);
                    List<ProjectTeam> projectTeams = projectTeamRepository.findAllByProject(project);
                    projectDto.setMembers(projectTeamDtoMapper.toDtoList(projectTeams));
                    return projectDto;
                })
                .toList();
        return new PageImpl<>(projects, pageable, projectPage.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("PROJECT NOT FOUND: " + id));

        projectTeamRepository.deleteAllByProject(project);
        projectRepository.delete(project);
    }
}


