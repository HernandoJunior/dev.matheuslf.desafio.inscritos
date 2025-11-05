package dev.matheuslf.desafio.inscritos.dto.projectDtos;

import java.time.LocalDate;

public record ProjectUpdateDto(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {
}
