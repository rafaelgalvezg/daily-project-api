package dev.rafaelgalvezg.dailyprojectapi;

import dev.rafaelgalvezg.dailyprojectapi.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class TaskTestFactory {

    public static Task createTask() {
        Task task = new Task();
        task.setIdTask(1L);
        task.setTitle("Sample Task");
        task.setDescription("This is a sample task description.");
        task.setStartDate(LocalDate.now());
        task.setEndDate(LocalDate.now().plusDays(1));
        task.setStatus(Status.IN_PROGRESS);
        task.setPriority(Priority.HIGH);
        task.setOrder(1);
        Collaborator collaborator = new Collaborator();
        collaborator.setIdCollaborator(1L);
        collaborator.setName("Juan Perez");
        collaborator.setEmail("juan.perez@example.com");
        task.setAssignedCollaborator(collaborator);
        Project project = new Project();
        project.setIdProject(1L);
        project.setName("Sample Project");
        task.setProject(project);
        return task;
    }

    public static Page<Task> createTasksPage() {
        List<Task> tasks = Collections.singletonList(createTask());
        return new PageImpl<>(tasks);
    }
}
