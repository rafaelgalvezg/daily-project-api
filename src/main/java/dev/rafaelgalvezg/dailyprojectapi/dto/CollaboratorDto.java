package dev.rafaelgalvezg.dailyprojectapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CollaboratorDto implements Serializable {
        @EqualsAndHashCode.Include
        private Long idCollaborator;
        @NotNull(message = "{name.notNull}")
        @Size(min = 2, max = 50, message = "{name.size55}")
        private String name;
        @NotNull(message = "{email.notNull}")
        @Email
        private String email;
        private Integer version;
}