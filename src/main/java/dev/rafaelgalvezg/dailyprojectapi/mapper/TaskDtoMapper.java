package dev.rafaelgalvezg.dailyprojectapi.mapper;

import dev.rafaelgalvezg.dailyprojectapi.dto.TaskDto;
import dev.rafaelgalvezg.dailyprojectapi.model.Task;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TaskDtoMapper {

    private final ModelMapper modelMapper;

    public TaskDto toDto(Task task) {
        return modelMapper.map(task, TaskDto.class);
    }

    public Task toEntity(TaskDto taskDto) {
        return modelMapper.map(taskDto, Task.class);
    }
}
