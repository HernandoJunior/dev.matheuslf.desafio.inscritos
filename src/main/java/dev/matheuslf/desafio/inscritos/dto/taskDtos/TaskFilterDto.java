package dev.matheuslf.desafio.inscritos.dto.taskDtos;


import dev.matheuslf.desafio.inscritos.model.PriorityTask;
import dev.matheuslf.desafio.inscritos.model.StatusTask;

public record TaskFilterDto(
        StatusTask status,
        PriorityTask priority,
        Long projectId
) {}
