package dev.rafaelgalvezg.dailyprojectapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ChangeTrackingDto implements Serializable {

        @EqualsAndHashCode.Include
        private Long idTracking;

        @NotNull(message = "{fieldChanged.notNull}")
        @Size(min = 3, max = 255, message = "{fieldChanged.size255}")
        private String fieldChanged;

        @NotNull(message = "{description.notNull}")
        @Size(min = 3, max = 1000, message = "{description.size1000}")
        private String description;

        @NotNull(message = "{task.notNull}")
        private TaskDto task;

        @NotNull(message = "{collaboratorResponsible.notNull}")
        private CollaboratorDto collaboratorResponsible;

        @NotNull(message = "{changeDate.notNull}")
        private LocalDateTime changeDate;

        private Integer version;
}
