package dev.rafaelgalvezg.dailyprojectapi.dto;

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
public class ProjectDto implements Serializable {
    @EqualsAndHashCode.Include
    private Long idProject;
    @NotNull(message = "{name.notNull}")
    @Size(min = 2, max = 50, message = "{name.size55}")
    private String name;
    @NotNull(message = "{description.notNull}")
    @Size(min = 2, max = 500, message = "{description.size500}")
    private String description;
    @NotNull(message = "{startDate.notNull}")
    private LocalDate startDate;
    @NotNull(message = "{endDate.notNull}")
    private LocalDate endDate;
    @NotNull(message = "{status.notNull}")
    private Status status;
    private Integer version;
    private List<ProjectTeamDto> members;
}