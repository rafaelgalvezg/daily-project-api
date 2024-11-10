package dev.rafaelgalvezg.dailyprojectapi.controller;

import dev.rafaelgalvezg.dailyprojectapi.dto.CollaboratorDto;
import dev.rafaelgalvezg.dailyprojectapi.mapper.CollaboratorDtoMapper;
import dev.rafaelgalvezg.dailyprojectapi.model.Collaborator;
import dev.rafaelgalvezg.dailyprojectapi.service.CollaboratorService;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/collaborators")
@RequiredArgsConstructor
@Tag(name = "Collaborators", description = "Collaborators API")
public class CollaboratorController {

    private final CollaboratorService collaboratorService;
    private final CollaboratorDtoMapper collaboratorMapper;

    @GetMapping
    public ResponseEntity<Page<CollaboratorDto>> findAll(@ParameterObject Pageable pageable){
        Page<Collaborator> collaboratorPage = collaboratorService.findAll(pageable);
        Page<CollaboratorDto> collaborators = collaboratorPage.map(collaboratorMapper::toDto);
        return ResponseEntity.ok(collaborators);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollaboratorDto> findById(@PathVariable Long id) {
        CollaboratorDto collaborator = collaboratorMapper.toDto(collaboratorService.findById(id));
        return ResponseEntity.ok(collaborator);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody CollaboratorDto collaboratorDto)  {
        Collaborator savedCollaborator = collaboratorService.save(collaboratorMapper.toEntity(collaboratorDto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCollaborator.getIdCollaborator()).toUri();
        // URI location = URI.create("/api/v1/collaborators/" + savedCollaborator.getIdCollaborator());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollaboratorDto> update(@PathVariable Long id, @Valid @RequestBody CollaboratorDto collaborator) {
        Collaborator updatedCollaborator = collaboratorService.update(id, collaboratorMapper.toEntity(collaborator));
        return ResponseEntity.ok(collaboratorMapper.toDto(updatedCollaborator));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        collaboratorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
