package dev.rafaelgalvezg.dailyprojectapi.mapper;

import dev.rafaelgalvezg.dailyprojectapi.dto.MemberRoleDto;
import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectDto;
import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectTeamDto;
import dev.rafaelgalvezg.dailyprojectapi.model.Collaborator;
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
    private final ProjectDtoMapper projectMapper;
    private final CollaboratorDtoMapper collaboratorMapper;

    public ProjectTeamDto toDto(Project project, List<ProjectTeam> members) {
        ProjectDto projectDto = projectMapper.toDto(project);
        List<MemberRoleDto> memberRoleDtos = members.stream().map(member -> new MemberRoleDto(collaboratorMapper.toDto(member.getCollaborator()), member.getRole())).collect(Collectors.toList());
        return new ProjectTeamDto(projectDto, memberRoleDtos);
    }

    public ProjectTeam toEntity(MemberRoleDto memberRoleDto, Project project, Collaborator collaborator) {
        ProjectTeam projectTeam = new ProjectTeam();
        projectTeam.setId(new ProjectTeamId(project.getIdProject(), collaborator.getIdCollaborator()));
        projectTeam.setProject(project);
        projectTeam.setCollaborator(collaborator);
        projectTeam.setRole(memberRoleDto.getRole());
        return projectTeam;
    }
}
