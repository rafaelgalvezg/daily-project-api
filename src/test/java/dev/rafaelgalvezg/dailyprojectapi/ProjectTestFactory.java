package dev.rafaelgalvezg.dailyprojectapi;
import java.time.LocalDate;
import java.util.List;

import dev.rafaelgalvezg.dailyprojectapi.dto.CollaboratorDto;
import dev.rafaelgalvezg.dailyprojectapi.dto.MemberRoleDto;
import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectDto;
import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectTeamDto;
import dev.rafaelgalvezg.dailyprojectapi.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class ProjectTestFactory {

    public static Project createProject() {
        Project project = new Project();
        project.setIdProject(1L);
        project.setName("Sample Project");
        project.setDescription("Sample project description");
        project.setStartDate(LocalDate.now());
        project.setEndDate(LocalDate.now().plusDays(30));
        project.setStatus(Status.IN_PROGRESS);
        project.setVersion(1);
        return project;
    }

    public static Collaborator createCollaborator() {
        Collaborator collaborator = new Collaborator();
        collaborator.setIdCollaborator(1L);
        collaborator.setName("John Doe");
        collaborator.setEmail("john.doe@example.com");
        collaborator.setVersion(1);
        return collaborator;
    }

    public static ProjectTeam createProjectTeam() {
        ProjectTeam projectTeam = new ProjectTeam();
        projectTeam.setCollaborator(createCollaborator());
        projectTeam.setRole(ProjectRole.DEVELOPER);
        return projectTeam;
    }

    public static ProjectDto createProjectDto() {
        return new ProjectDto(
                1L,
                "Sample Project",
                "Description of the project",
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                Status.IN_PROGRESS,
                1
        );
    }

    public static ProjectTeamDto createProjectTeamDto() {
        ProjectTeamDto projectTeamDto = new ProjectTeamDto();
        projectTeamDto.setProject(createProjectDto());
        projectTeamDto.setMembers(createMemberRoleDtoList());
        return projectTeamDto;
    }

    public static MemberRoleDto createMemberRoleDto() {
        return new MemberRoleDto(
                new CollaboratorDto(
                        1L,
                        "John Doe",
                        "john.doe@example.com",
                        1
                ),
                ProjectRole.DEVELOPER
        );
    }

    public static List<MemberRoleDto> createMemberRoleDtoList() {
        return List.of(createMemberRoleDto());
    }

    public static Page<Project> createProjectPage() {
        return new PageImpl<>(List.of(createProject()));
    }
}
