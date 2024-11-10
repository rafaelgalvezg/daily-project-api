package dev.rafaelgalvezg.dailyprojectapi.dto;

import dev.rafaelgalvezg.dailyprojectapi.model.Priority;
import dev.rafaelgalvezg.dailyprojectapi.model.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskDto implements Serializable {
        @EqualsAndHashCode.Include
        private Long idTask;
        @NotNull(message = "{title.notNull}")
        @Size(min = 2, max = 255, message = "{title.size255}")
        private String title;
        @Size(min = 2, max = 1000, message = "{description.size1000}")
        private String description;
        @NotNull(message = "{startDate.notNull}")
        private LocalDate startDate;
        @NotNull(message = "{endDate.notNull}")
        private LocalDate endDate;
        @NotNull(message = "{status.notNull}")
        private Status status;
        @NotNull(message = "{priority.notNull}")
        private Priority priority;
        @NotNull(message = "{order.notNull}")
        private Integer order;
        @NotNull(message = "{assignedCollaborator.notNull}")
        private CollaboratorDto assignedCollaborator;
        @NotNull(message = "{project.notNull}")
        private ProjectDto project;
        private List<TagDto> tags;
        private Integer version;
}
