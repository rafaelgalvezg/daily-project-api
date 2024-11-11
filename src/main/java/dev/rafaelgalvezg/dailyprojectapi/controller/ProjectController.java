package dev.rafaelgalvezg.dailyprojectapi.controller;

import dev.rafaelgalvezg.dailyprojectapi.dto.MemberRoleDto;
import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectDto;
import dev.rafaelgalvezg.dailyprojectapi.dto.ProjectTeamDto;
import dev.rafaelgalvezg.dailyprojectapi.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Tag(name = "Projects Team", description = "Projects Team API")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectTeamDto> createProject(@Valid @RequestBody ProjectTeamDto projectTeamDto) {
        ProjectTeamDto createdProject = projectService.save(projectTeamDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdProject.getProject().getIdProject()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{projectId}/members")
    public ResponseEntity<ProjectTeamDto> updateProjectMembers(@PathVariable Long projectId,@Valid @RequestBody List<MemberRoleDto> members) {
        ProjectTeamDto updatedProject = projectService.update(projectId, members);
        return ResponseEntity.ok(updatedProject);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectTeamDto> getProjectById(@PathVariable Long projectId){
        ProjectTeamDto projectTeamDto = projectService.findById(projectId);
        return ResponseEntity.ok(projectTeamDto);
    }

    @GetMapping
    public ResponseEntity<Page<ProjectTeamDto>> getAllProjects(@ParameterObject Pageable pageable) {
        Page<ProjectTeamDto> projects = projectService.findAll(pageable);
        return ResponseEntity.ok(projects);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.delete(projectId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectTeamDto> updateProjectDetails(@PathVariable Long projectId, @RequestBody ProjectDto projectDto) {
        ProjectTeamDto updatedProject = projectService.updateProjectDetails(projectId, projectDto);
        return ResponseEntity.ok(updatedProject);
    }
}