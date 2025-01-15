package dev.rafaelgalvezg.dailyprojectapi.controller;

import dev.rafaelgalvezg.dailyprojectapi.dto.PermissionDto;
import dev.rafaelgalvezg.dailyprojectapi.mapper.PermissionDtoMapper;
import dev.rafaelgalvezg.dailyprojectapi.service.PermissionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/permissions")
@RequiredArgsConstructor
@RestController
@Tag(name = "Permissions", description = "Permissions API")
public class PermissionController {

   private final PermissionService permissionService;
   private final PermissionDtoMapper permissionMapper;

    @GetMapping
    public ResponseEntity<Page<PermissionDto>> findAll(@ParameterObject Pageable pageable) {
        Page<dev.rafaelgalvezg.dailyprojectapi.model.Permission> permissionsPage = permissionService.findAll(pageable);
        Page<PermissionDto> permissionsDto = permissionsPage.map(permissionMapper::toDto);
        return ResponseEntity.ok(permissionsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionDto> findById(@PathVariable Long id) {
        PermissionDto permission = permissionMapper.toDto(permissionService.findById(id));
        return ResponseEntity.ok(permission);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody PermissionDto permissionDto)  {
        dev.rafaelgalvezg.dailyprojectapi.model.Permission savedPermission = permissionService.save(permissionMapper.toEntity(permissionDto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPermission.getIdPermission()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionDto> update(@PathVariable Long id, @Valid @RequestBody PermissionDto permission) {
        dev.rafaelgalvezg.dailyprojectapi.model.Permission updatedPermission = permissionService.update(id, permissionMapper.toEntity(permission));
        return ResponseEntity.ok(permissionMapper.toDto(updatedPermission));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        permissionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user")
    public ResponseEntity<List<PermissionDto>> findPermissionsByUsername(@RequestBody String username) {
        List<dev.rafaelgalvezg.dailyprojectapi.model.Permission> permissions = permissionService.findPermissionsByUsername(username);
        List<PermissionDto> permissionsDto = permissions.stream().map(permissionMapper::toDto).toList();
        return ResponseEntity.ok(permissionsDto);
    }


}
