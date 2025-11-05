package dev.matheuslf.desafio.inscritos.dto;


public record TaskFilterDto(
        String status,
        String priority,
        Long id,
        String title,
        String description,
        String dueDate,
        String projectName,
        Long projectId
) {}
