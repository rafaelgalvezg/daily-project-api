package dev.rafaelgalvezg.dailyprojectapi.dto;

import dev.rafaelgalvezg.dailyprojectapi.model.Role;
import dev.rafaelgalvezg.dailyprojectapi.model.StatusUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto implements Serializable {
        @EqualsAndHashCode.Include
        private Long idUser;
        @NotNull(message = "{name.notNull}")
        @Size(min = 2, max = 255, message = "{name.size255}")
        private String name;
        @NotNull(message = "{email.notNull}")
        @Email(message = "{email.format}")
        private String email;
        @NotNull(message = "{password.notNull}")
        private String password;
        @NotNull(message = "{status.notNull}")
        private StatusUser status;
        private List<Role> roles;
        private Integer version;
}
