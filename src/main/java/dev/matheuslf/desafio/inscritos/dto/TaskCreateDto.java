package dev.matheuslf.desafio.inscritos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskCreateDto(
        @NotBlank
        @Size(min = 5, max = 150, message = "Minimo de 5 caracteres, maximo de 150.")
        String title,

        @NotBlank
        String description,

        @NotBlank
        String status,

        @NotBlank
        String priority,

        @NotNull
        LocalDate dueDate,

        @NotBlank
        String projectId
) {
}
