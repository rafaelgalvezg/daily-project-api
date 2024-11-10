package dev.rafaelgalvezg.dailyprojectapi.mapper;

import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectDto;
import dev.rafaelgalvezg.dailyprojectapi.model.Project;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class ProjectDtoMapper {

    private final ModelMapper modelMapper;
    private final ProjectTeamDtoMapper projectTeamDtoMapper;

    public ProjectDto toDto(Project project) {
        ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
        projectDto.setMembers(projectTeamDtoMapper.toDtoList(project.getTeamMembers()));
        return projectDto;
    }

    public Project toEntity(ProjectDto projectDto) {
        Project project = modelMapper.map(projectDto, Project.class);
        project.setTeamMembers(projectTeamDtoMapper.toEntityList(projectDto.getMembers(), project));
        return project;
    }
}



