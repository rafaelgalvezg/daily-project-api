package dev.rafaelgalvezg.dailyprojectapi.dto;

import dev.rafaelgalvezg.dailyprojectapi.model.ProjectRole;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRoleDto implements Serializable {
        private CollaboratorDto member;
        private ProjectRole role;
}
