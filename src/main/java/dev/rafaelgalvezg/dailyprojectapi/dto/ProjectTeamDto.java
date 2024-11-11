package dev.rafaelgalvezg.dailyprojectapi.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectTeamDto {
    private ProjectDto project;
    private List<MemberRoleDto> members;
}
