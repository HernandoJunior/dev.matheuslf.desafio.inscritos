package dev.matheuslf.desafio.inscritos.dto.taskDtos;

import dev.matheuslf.desafio.inscritos.model.StatusTask;

public record TaskUpdateDto(
        Long id,
        StatusTask statusTask
) {
}
