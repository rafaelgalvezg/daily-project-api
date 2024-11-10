package dev.rafaelgalvezg.dailyprojectapi.dto;

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
public class PermissionDto implements Serializable {

        @EqualsAndHashCode.Include
        private Long idPermission;
        @NotNull(message = "Name is required")
        @Size(min = 3, max = 100, message = "{name.size100}")
        private String name;
        @NotNull(message = "{url.notNull}")
        @Size(min = 3, max = 100, message = "{url.size255}")
        private String url;
        private String action;
        private String icon;
        private List<RoleDto> roles;
        private Integer version;
}
