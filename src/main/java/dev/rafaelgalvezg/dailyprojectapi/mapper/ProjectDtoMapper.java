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

    public ProjectDto toDto(Project project) {
        return modelMapper.map(project, ProjectDto.class);
    }

    public Project toEntity(ProjectDto projectDto) {
        return modelMapper.map(projectDto, Project.class);
    }
}



