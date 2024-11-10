package dev.rafaelgalvezg.dailyprojectapi.service;

import dev.rafaelgalvezg.dailyprojectapi.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Task save(Task task);
    Task update(Long id, Task task);
    Task findById(Long id);
    void delete(Long id);
    Page<Task> findAll(Pageable pageable);
}
