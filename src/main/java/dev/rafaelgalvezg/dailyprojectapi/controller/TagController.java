package dev.rafaelgalvezg.dailyprojectapi.controller;

import dev.rafaelgalvezg.dailyprojectapi.dto.TagDto;
import dev.rafaelgalvezg.dailyprojectapi.mapper.TagDtoMapper;
import dev.rafaelgalvezg.dailyprojectapi.model.Tag;
import dev.rafaelgalvezg.dailyprojectapi.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tags", description = "Tags API")
public class TagController {

    private final TagService tagService;
    private final TagDtoMapper tagMapper;

    @GetMapping
    @Operation(
            summary = "Get all tags",
            description = "Returns a paginated list of tags"
    )
    public ResponseEntity<Page<TagDto>> findAll(@ParameterObject Pageable pageable) {
        Page<Tag> tagsPage = tagService.findAll(pageable);
        Page<TagDto> tagsDto = tagsPage.map(tagMapper::toDto);
        return ResponseEntity.ok(tagsDto);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a tag by ID",
            description = "Returns a specific tag by its ID"
    )
    public ResponseEntity<TagDto> findById(@PathVariable Long id) {
        TagDto tag = tagMapper.toDto(tagService.findById(id));
        return ResponseEntity.ok(tag);
    }

    @PostMapping
    @Operation(
            summary = "Create a new tag",
            description = "Creates a new tag and returns the resource location"
    )
    public ResponseEntity<Void> save(@Valid @RequestBody TagDto tagDto) {
        Tag savedTag = tagService.save(tagMapper.toEntity(tagDto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedTag.getIdTag()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a tag",
            description = "Updates an existing tag by its ID"
    )
    public ResponseEntity<TagDto> update(@PathVariable Long id, @Valid @RequestBody TagDto tag) {
        Tag updatedTag = tagService.update(id, tagMapper.toEntity(tag));
        return ResponseEntity.ok(tagMapper.toDto(updatedTag));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a tag",
            description = "Deletes a tag by its ID"
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}