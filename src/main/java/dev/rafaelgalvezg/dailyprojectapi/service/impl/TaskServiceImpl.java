package dev.rafaelgalvezg.dailyprojectapi.service.impl;

import dev.rafaelgalvezg.dailyprojectapi.exception.CustomOptimisticLockException;
import dev.rafaelgalvezg.dailyprojectapi.exception.ModelNotFoundException;
import dev.rafaelgalvezg.dailyprojectapi.model.Task;
import dev.rafaelgalvezg.dailyprojectapi.repository.TaskRepository;
import dev.rafaelgalvezg.dailyprojectapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private static final String MESSAGE_NOT_FOUND = "ID NOT FOUND: ";
    private final TaskRepository taskRepository;

    @Override
    public Task save(Task task){
        return taskRepository.save(task);
    }

    @Override
    public Page<Task> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(MESSAGE_NOT_FOUND + id));
    }

    @Override
    public Page<Task> findByProject(Long idProject, Pageable pageable) {
        return taskRepository.findByProjectIdProject(idProject, pageable);
    }

    @Override
    public void delete(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(MESSAGE_NOT_FOUND + id));
        taskRepository.delete(task);
    }

    @Override
    public Task update(Long id, Task task) {
        if (!taskRepository.existsById(id)) {
            throw new ModelNotFoundException(MESSAGE_NOT_FOUND + id);
        }
        try {
            return taskRepository.save(task);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new CustomOptimisticLockException("The record has been modified by another user. Please reload and try again." );
        }
    }

}
