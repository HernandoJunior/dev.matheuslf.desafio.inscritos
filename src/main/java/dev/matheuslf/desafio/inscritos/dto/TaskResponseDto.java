package dev.matheuslf.desafio.inscritos.dto;

import dev.matheuslf.desafio.inscritos.model.PriorityTask;
import dev.matheuslf.desafio.inscritos.model.Project;
import dev.matheuslf.desafio.inscritos.model.StatusTask;
import dev.matheuslf.desafio.inscritos.model.Task;
import jakarta.persistence.*;

import java.time.LocalDate;

public record TaskResponseDto (
     Long id,
     String title,
     String description,
     StatusTask status,
     PriorityTask priority,
     LocalDate dueDate,
     Project project
){
    public TaskResponseDto(Task task) {
        this(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getProject()
        );
    }
}
