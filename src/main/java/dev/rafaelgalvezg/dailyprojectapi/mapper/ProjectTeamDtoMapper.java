package dev.rafaelgalvezg.dailyprojectapi.mapper;

import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectTeamDto;
import dev.rafaelgalvezg.dailyprojectapi.model.Project;
import dev.rafaelgalvezg.dailyprojectapi.model.ProjectTeam;
import dev.rafaelgalvezg.dailyprojectapi.model.ProjectTeamId;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ProjectTeamDtoMapper {

    private final ModelMapper modelMapper;
    private final CollaboratorDtoMapper collaboratorDtoMapper;

    public ProjectTeamDto toDto(ProjectTeam projectTeam) {
        ProjectTeamDto projectTeamDto = modelMapper.map(projectTeam, ProjectTeamDto.class);
        projectTeamDto.setMember(collaboratorDtoMapper.toDto(projectTeam.getCollaborator()));
        return projectTeamDto;
    }

    public ProjectTeam toEntity(ProjectTeamDto projectTeamDto, Project project) {
        ProjectTeam projectTeam = modelMapper.map(projectTeamDto, ProjectTeam.class);
        projectTeam.setId(new ProjectTeamId(project.getIdProject(), projectTeamDto.getMember().getIdCollaborator()));
        projectTeam.setProject(project);
        projectTeam.setCollaborator(collaboratorDtoMapper.toEntity(projectTeamDto.getMember()));
        return projectTeam;
    }

    public List<ProjectTeamDto> toDtoList(List<ProjectTeam> projectTeams) {
        return projectTeams.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<ProjectTeam> toEntityList(List<ProjectTeamDto> projectTeamDtos, Project project) {
        return projectTeamDtos.stream()
                .map(dto -> this.toEntity(dto, project))
                .collect(Collectors.toList());
    }
}
