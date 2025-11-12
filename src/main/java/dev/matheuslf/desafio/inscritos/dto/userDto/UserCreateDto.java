package dev.matheuslf.desafio.inscritos.dto.userDto;

import jakarta.validation.constraints.NotNull;

public record UserCreateDto(
        @NotNull
        String login,

        @NotNull
        String email,

        @NotNull
        String password
) {
}
