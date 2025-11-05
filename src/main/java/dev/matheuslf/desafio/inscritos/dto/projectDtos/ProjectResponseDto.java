package dev.matheuslf.desafio.inscritos.dto.projectDtos;

import dev.matheuslf.desafio.inscritos.model.Project;

import java.time.LocalDate;

public record ProjectResponseDto (
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {
    public ProjectResponseDto(Project project){
        this(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate()
        );
    }
}
