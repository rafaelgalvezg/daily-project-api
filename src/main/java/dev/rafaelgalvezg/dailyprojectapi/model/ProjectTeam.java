package dev.rafaelgalvezg.dailyprojectapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project_team")
@Entity
public class ProjectTeam {

    @EmbeddedId
    private ProjectTeamId id;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "id_project")
    private Project project;

    @ManyToOne
    @MapsId("collaboratorId")
    @JoinColumn(name = "id_collaborator")
    private Collaborator collaborator;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(columnDefinition = "role")
    private ProjectRole role;

}
