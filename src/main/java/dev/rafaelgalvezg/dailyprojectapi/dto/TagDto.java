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
public class TagDto implements Serializable {
        @EqualsAndHashCode.Include
        private Long idTag;
        @NotNull(message = "{name.notNull}")
        @Size(message = "{name.size50}", min = 2, max = 50)
        private String name;
        @NotNull(message = "{color.notNull}")
        @Size(message = "{color.size50}", min = 2, max = 50)
        private String color;
        private Integer version;
}
