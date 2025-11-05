package dev.matheuslf.desafio.inscritos.dto.projectDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProjectCreateDto (
        @NotBlank
        String name,

        @Size(
                min = 3,
                max = 100,
                message = "Minimo de 3 caracteres, maximo de 100."
        )
        String description,

        @NotBlank
        String startDate,

        LocalDate endDate
) {
}
