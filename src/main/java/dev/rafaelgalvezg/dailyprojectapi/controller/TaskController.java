package dev.rafaelgalvezg.dailyprojectapi.controller;

import dev.rafaelgalvezg.dailyprojectapi.dto.TaskDto;
import dev.rafaelgalvezg.dailyprojectapi.mapper.TaskDtoMapper;
import dev.rafaelgalvezg.dailyprojectapi.model.Task;
import dev.rafaelgalvezg.dailyprojectapi.service.TaskService;
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
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Tasks API")
public class TaskController {

    private final TaskService taskService;
    private final TaskDtoMapper taskMapper;

    @GetMapping
    public ResponseEntity<Page<TaskDto>> findAll(@ParameterObject Pageable pageable){;
        Page<Task> tasks = taskService.findAll(pageable);
        Page<TaskDto> tasksDto =tasks.map(taskMapper::toDto);
        return ResponseEntity.ok(tasksDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable Long id) {
        TaskDto task = taskMapper.toDto(taskService.findById(id));
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody TaskDto taskDto)  {
        Task savedTask = taskService.save(taskMapper.toEntity(taskDto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedTask.getIdTask()).toUri();
        // URI location = URI.create("/api/v1/tasks/" + savedTask.getIdTask());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> update(@PathVariable Long id, @Valid @RequestBody TaskDto task) {
        Task updatedTask = taskService.update(id, taskMapper.toEntity(task));
        return ResponseEntity.ok(taskMapper.toDto(updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
