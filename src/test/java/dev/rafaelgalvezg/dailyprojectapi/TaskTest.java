package dev.rafaelgalvezg.dailyprojectapi;

import dev.rafaelgalvezg.dailyprojectapi.exception.CustomOptimisticLockException;
import dev.rafaelgalvezg.dailyprojectapi.exception.ModelNotFoundException;
import dev.rafaelgalvezg.dailyprojectapi.model.Task;
import dev.rafaelgalvezg.dailyprojectapi.repository.TaskRepository;
import dev.rafaelgalvezg.dailyprojectapi.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Test save task successfully")
    void testSaveTask() {
        Task task = TaskTestFactory.createTask();
        when(taskRepository.save(task)).thenReturn(task);

        Task savedTask = taskService.save(task);
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getTitle()).isEqualTo(task.getTitle());

        verify(taskRepository, Mockito.times(1)).save(task);
    }

    @Test
    @DisplayName("Test findAll successfully")
    void testFindAll() {
        Page<Task> tasksPage = TaskTestFactory.createTasksPage();
        Pageable pageable = PageRequest.of(0, 10);
        when(taskRepository.findAll(pageable)).thenReturn(tasksPage);

        Page<Task> resultPage = taskService.findAll(pageable);
        assertThat(resultPage).isNotEmpty();

        verify(taskRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test findById successfully")
    void testFindById() {
        Task task = TaskTestFactory.createTask();
        when(taskRepository.findById(task.getIdTask())).thenReturn(Optional.of(task));

        Task foundTask = taskService.findById(task.getIdTask());
        assertThat(foundTask).isNotNull();
        assertThat(foundTask.getIdTask()).isEqualTo(task.getIdTask());

        verify(taskRepository, Mockito.times(1)).findById(task.getIdTask());
    }

    @Test
    @DisplayName("Test findById throws exception")
    void testFindByIdThrowsException() {
        Long id = 1L;
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findById(id))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("ID NOT FOUND: " + id);

        verify(taskRepository, Mockito.times(1)).findById(id);
    }

    @Test
    @DisplayName("Test delete successfully")
    void testDelete() {
        Task task = TaskTestFactory.createTask();
        when(taskRepository.findById(task.getIdTask())).thenReturn(Optional.of(task));

        taskService.delete(task.getIdTask());
        verify(taskRepository, Mockito.times(1)).delete(task);
    }

    @Test
    @DisplayName("Test delete throws exception")
    void testDeleteThrowsException() {
        Long id = 1L;
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.delete(id))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("ID NOT FOUND: " + id);

        verify(taskRepository, Mockito.times(1)).findById(id);
    }

    @Test
    @DisplayName("Test update successfully")
    void testUpdate() {
        Task task = TaskTestFactory.createTask();
        when(taskRepository.existsById(task.getIdTask())).thenReturn(true);
        when(taskRepository.save(task)).thenReturn(task);

        Task updatedTask = taskService.update(task.getIdTask(), task);
        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.getTitle()).isEqualTo(task.getTitle());

        verify(taskRepository, Mockito.times(1)).existsById(task.getIdTask());
        verify(taskRepository, Mockito.times(1)).save(task);
    }

    @Test
    @DisplayName("Test update throws exception")
    void testUpdateThrowsException() {
        Long id = 1L;
        Task task = TaskTestFactory.createTask();
        when(taskRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> taskService.update(id, task))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("ID NOT FOUND: " + id);

        verify(taskRepository, Mockito.times(1)).existsById(id);
    }

    @Test
    @DisplayName("Test find tasks by project successfully")
    void testFindByProject() {
        Page<Task> tasksPage = TaskTestFactory.createTasksPage();
        Long idProject = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        when(taskRepository.findByProjectIdProject(idProject, pageable)).thenReturn(tasksPage);

        Page<Task> resultPage = taskService.findByProject(idProject, pageable);
        assertThat(resultPage).isNotEmpty();

        verify(taskRepository, Mockito.times(1)).findByProjectIdProject(idProject, pageable);
    }

    @Test
    @DisplayName("Should throw OCustomOptimisticLockException on optimistic lock failure")
    void testUpdateThrowsCustomOptimisticLockException() {
        Task task = TaskTestFactory.createTask();
        Long idTask = task.getIdTask();

        when(taskRepository.existsById(idTask)).thenReturn(true);
        when(taskRepository.save(task)).thenThrow(ObjectOptimisticLockingFailureException.class);

        assertThrows(CustomOptimisticLockException.class, () -> taskService.update(idTask, task));
    }

}
