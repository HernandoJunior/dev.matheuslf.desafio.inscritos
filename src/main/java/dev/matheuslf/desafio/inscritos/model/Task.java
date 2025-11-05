package dev.matheuslf.desafio.inscritos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "TBL_TASK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    private StatusTask status;

    @Enumerated(EnumType.STRING)
    private PriorityTask priority;

    @Column
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "projectId", nullable = false)
    private Project project;
}
