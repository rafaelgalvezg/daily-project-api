package dev.rafaelgalvezg.dailyprojectapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "change_tracking")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ChangeTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_tracking", nullable = false)
    private Long idTracking;
    @Column(name = "field_changed", nullable = false)
    private String fieldChanged;
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_task", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "collaborator_responsible", nullable = false)
    private Collaborator collaboratorResponsible;

    @Version
    private Integer version;

    @CreatedDate
    @Column(name = "change_date")
    private LocalDateTime changeDate;

}
