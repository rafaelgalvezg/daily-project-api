package dev.rafaelgalvezg.dailyprojectapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoleDto implements Serializable {
        @EqualsAndHashCode.Include
        private Long idRole;
        @NotNull(message = "{name.notNull}")
        @Size(message = "{name.size100}", min = 2, max = 100)
        private String name;
        private Integer version;
}
