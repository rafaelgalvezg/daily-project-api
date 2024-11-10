package dev.rafaelgalvezg.dailyprojectapi.repository;

import dev.rafaelgalvezg.dailyprojectapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}