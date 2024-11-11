package dev.rafaelgalvezg.dailyprojectapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.rafaelgalvezg.dailyprojectapi.dto.MemberRoleDto;
import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectDto;
import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectTeamDto;
import dev.rafaelgalvezg.dailyprojectapi.exception.CustomOptimisticLockException;
import dev.rafaelgalvezg.dailyprojectapi.exception.ModelNotFoundException;
import dev.rafaelgalvezg.dailyprojectapi.mapper.ProjectDtoMapper;
import dev.rafaelgalvezg.dailyprojectapi.mapper.ProjectTeamDtoMapper;
import dev.rafaelgalvezg.dailyprojectapi.model.Collaborator;
import dev.rafaelgalvezg.dailyprojectapi.model.Project;
import dev.rafaelgalvezg.dailyprojectapi.model.ProjectRole;
import dev.rafaelgalvezg.dailyprojectapi.model.ProjectTeam;
import dev.rafaelgalvezg.dailyprojectapi.repository.CollaboratorRepository;
import dev.rafaelgalvezg.dailyprojectapi.repository.ProjectRepository;
import dev.rafaelgalvezg.dailyprojectapi.repository.ProjectTeamRepository;
import dev.rafaelgalvezg.dailyprojectapi.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class ProjectTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private CollaboratorRepository collaboratorRepository;

    @Mock
    private ProjectTeamRepository projectTeamRepository;

    @Mock
    private ProjectDtoMapper projectMapper;

    @Mock
    private ProjectTeamDtoMapper projectTeamMapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    @DisplayName("Should save a ProjectTeamDto and return saved ProjectTeamDto")
    void testSaveProjectTeamDto() {
        ProjectTeamDto projectTeamDto = ProjectTestFactory.createProjectTeamDto();
        Project project = ProjectTestFactory.createProject();
        when(projectMapper.toEntity(any())).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Collaborator collaborator = ProjectTestFactory.createCollaborator();
        when(collaboratorRepository.findById(anyLong())).thenReturn(Optional.of(collaborator));

        ProjectTeam projectTeam = ProjectTestFactory.createProjectTeam();
        when(projectTeamMapper.toEntity(any(), any(), any())).thenReturn(projectTeam);
        when(projectTeamRepository.save(any(ProjectTeam.class))).thenReturn(projectTeam);

        when(projectTeamRepository.findByProject_IdProject(anyLong())).thenReturn(List.of(projectTeam));
        when(projectTeamMapper.toDto(any(Project.class), anyList())).thenReturn(projectTeamDto);

        ProjectTeamDto result = projectService.save(projectTeamDto);

        assertNotNull(result);
        assertEquals(projectTeamDto, result);
        verify(projectRepository).save(any(Project.class));
        verify(projectTeamRepository).save(any(ProjectTeam.class));
    }

    @Test
    @DisplayName("Should throw ModelNotFoundException when Collaborator not found in save")
    void testSaveProjectTeamDto_CollaboratorNotFound() {
        ProjectTeamDto projectTeamDto = ProjectTestFactory.createProjectTeamDto();
        when(projectMapper.toEntity(any())).thenReturn(ProjectTestFactory.createProject());
        when(projectRepository.save(any(Project.class))).thenReturn(ProjectTestFactory.createProject());

        when(collaboratorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class, () -> projectService.save(projectTeamDto));
    }

    @Test
    @DisplayName("Should update project team roles correctly")
    void testUpdateProjectTeamRoles() {
        Long projectId = 1L;
        List<MemberRoleDto> memberRoles = ProjectTestFactory.createMemberRoleDtoList();
        Project project = ProjectTestFactory.createProject();
        ProjectTeam projectTeam = ProjectTestFactory.createProjectTeam();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectTeamRepository.findByProject_IdProject(projectId)).thenReturn(List.of(projectTeam));

        ProjectTeamDto projectTeamDto = ProjectTestFactory.createProjectTeamDto();
        when(projectTeamMapper.toDto(any(Project.class), anyList())).thenReturn(projectTeamDto);

        ProjectTeamDto result = projectService.update(projectId, memberRoles);

        assertNotNull(result);
        assertEquals(projectTeamDto, result);
    }

   @Test
    @DisplayName("Should update member roles correctly")
    void testUpdateMemberRoles() {
        Long projectId = 1L;
        List<MemberRoleDto> memberRoles = ProjectTestFactory.createMemberRoleDtoList();
        Project project = ProjectTestFactory.createProject();
        ProjectTeam projectTeam = ProjectTestFactory.createProjectTeam();
        projectTeam.setRole(ProjectRole.PROJECT_LEADER);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectTeamRepository.findByProject_IdProject(projectId)).thenReturn(List.of(projectTeam));

        ProjectTeamDto projectTeamDto = ProjectTestFactory.createProjectTeamDto();
        when(projectTeamMapper.toDto(any(Project.class), anyList())).thenReturn(projectTeamDto);

        ProjectTeamDto result = projectService.update(projectId, memberRoles);

        assertNotNull(result);
        assertEquals(projectTeamDto, result);
        verify(projectTeamRepository).save(any(ProjectTeam.class));
    }

    @Test
    @DisplayName("Should add new member correctly")
    void testAddNewMember() {
        Long projectId = 1L;
        List<MemberRoleDto> memberRoles = ProjectTestFactory.createMemberRoleDtoList();
        Project project = ProjectTestFactory.createProject();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectTeamRepository.findByProject_IdProject(projectId)).thenReturn(List.of());

        Collaborator collaborator = ProjectTestFactory.createCollaborator();
        when(collaboratorRepository.findById(anyLong())).thenReturn(Optional.of(collaborator));

        ProjectTeamDto projectTeamDto = ProjectTestFactory.createProjectTeamDto();
        when(projectTeamMapper.toDto(any(Project.class), anyList())).thenReturn(projectTeamDto);

        ProjectTeamDto result = projectService.update(projectId, memberRoles);

        assertNotNull(result);
        assertEquals(projectTeamDto, result);
        verify(projectTeamRepository).save(any(ProjectTeam.class));
    }

    @Test
    @DisplayName("Should throw ModelNotFoundException when Project not found in update")
    void testUpdateProjectTeam_ProjectNotFound() {
        Long projectId = 1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class, () -> projectService.update(projectId, ProjectTestFactory.createMemberRoleDtoList()));
    }

    @Test
    @DisplayName("Should return ProjectTeamDto when project found by id")
    void testFindById() {
        Long projectId = 1L;
        Project project = ProjectTestFactory.createProject();
        ProjectTeam projectTeam = ProjectTestFactory.createProjectTeam();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectTeamRepository.findByProject_IdProject(projectId)).thenReturn(List.of(projectTeam));

        ProjectTeamDto projectTeamDto = ProjectTestFactory.createProjectTeamDto();
        when(projectTeamMapper.toDto(project, List.of(projectTeam))).thenReturn(projectTeamDto);

        ProjectTeamDto result = projectService.findById(projectId);

        assertNotNull(result);
        assertEquals(projectTeamDto, result);
    }

    @Test
    @DisplayName("Should delete project by id")
    void testDeleteProjectById() {
        Long projectId = 1L;
        when(projectRepository.existsById(projectId)).thenReturn(true);

        projectService.delete(projectId);

        verify(projectRepository).deleteById(projectId);
        verify(projectTeamRepository).deleteByProject_IdProject(projectId);
    }

    @Test
    @DisplayName("Should throw ModelNotFoundException when deleting non-existing project")
    void testDeleteProject_ProjectNotFound() {
        Long projectId = 1L;
        when(projectRepository.existsById(projectId)).thenReturn(false);

        assertThrows(ModelNotFoundException.class, () -> projectService.delete(projectId));
    }


    @Test
    @DisplayName("Should update project details correctly")
    void updateProjectDetails_Success() {
        Long projectId = 1L;
        ProjectDto projectDto = ProjectTestFactory.createProjectDto();
        Project project = ProjectTestFactory.createProject();
        List<ProjectTeam> projectTeams = List.of(ProjectTestFactory.createProjectTeam());
        ProjectTeamDto projectTeamDto = ProjectTestFactory.createProjectTeamDto();

        when(projectRepository.existsById(projectId)).thenReturn(true);
        when(projectMapper.toEntity(projectDto)).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectTeamRepository.findByProject_IdProject(projectId)).thenReturn(projectTeams);
        when(projectTeamMapper.toDto(project, projectTeams)).thenReturn(projectTeamDto);
        ProjectTeamDto result = projectService.updateProjectDetails(projectId, projectDto);

        assertNotNull(result, "The result should not be null");
        assertEquals(projectTeamDto, result);
        verify(projectRepository).save(any(Project.class));
        verify(projectTeamRepository).findByProject_IdProject(projectId);
        verify(projectTeamMapper).toDto(project, projectTeams);
    }


    @Test
    @DisplayName("Should throw CustomOptimisticLockException on optimistic lock failure")
    void testUpdateProjectDetails_OptimisticLockingFailure() {
        Long projectId = 1L;
        ProjectDto projectDto = ProjectTestFactory.createProjectDto();

        when(projectRepository.existsById(projectId)).thenReturn(true);
        when(projectRepository.save(any())).thenThrow(ObjectOptimisticLockingFailureException.class);

        assertThrows(CustomOptimisticLockException.class, () -> projectService.updateProjectDetails(projectId, projectDto));
    }

    @Test
    @DisplayName("Should throw ModelNotFoundException when project not found")
    void testUpdateProjectDetails_ProjectNotFound() {
        Long projectId = 1L;
        ProjectDto projectDto = ProjectTestFactory.createProjectDto();

        when(projectRepository.existsById(projectId)).thenReturn(false);

        assertThrows(ModelNotFoundException.class, () -> projectService.updateProjectDetails(projectId, projectDto));
        verify(projectRepository, never()).save(any(Project.class));
        verify(projectTeamRepository, never()).findByProject_IdProject(anyLong());
        verify(projectTeamMapper, never()).toDto(any(Project.class), anyList());
    }

    @Test
    @DisplayName("Should return paginated list of ProjectTeamDto")
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Project project = ProjectTestFactory.createProject();
        ProjectTeam projectTeam = ProjectTestFactory.createProjectTeam();
        ProjectTeamDto projectTeamDto = ProjectTestFactory.createProjectTeamDto();

        Page<Project> projectPage = new PageImpl<>(List.of(project), pageable, 1);
        when(projectRepository.findAll(pageable)).thenReturn(projectPage);
        when(projectTeamRepository.findByProject_IdProject(project.getIdProject())).thenReturn(List.of(projectTeam));
        when(projectTeamMapper.toDto(project, List.of(projectTeam))).thenReturn(projectTeamDto);

        Page<ProjectTeamDto> result = projectService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(projectTeamDto, result.getContent().getFirst());
    }

    @Test
    @DisplayName("Should throw ModelNotFoundException when project not found")
    void testProjectNotFoundException() {
        List<MemberRoleDto> members = new ArrayList<>();
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class, () -> projectService.update(1L, members));
    }

    @Test
    @DisplayName("Should throw ModelNotFoundException when collaborator not found")
    void testCollaboratorNotFoundException() {
        Project project = ProjectTestFactory.createProject();
        Collaborator collaborator = ProjectTestFactory.createCollaborator();

        List<MemberRoleDto> members = new ArrayList<>();
        MemberRoleDto memberRoleDto = ProjectTestFactory.createMemberRoleDto();
        members.add(memberRoleDto);

        when(projectRepository.findById(project.getIdProject())).thenReturn(Optional.of(project));
        when(collaboratorRepository.findById(collaborator.getIdCollaborator())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class, () -> projectService.update(project.getIdProject(), members));
    }
}
