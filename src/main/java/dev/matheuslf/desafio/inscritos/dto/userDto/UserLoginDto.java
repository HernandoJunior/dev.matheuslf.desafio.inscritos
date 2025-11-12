package dev.matheuslf.desafio.inscritos.dto.userDto;

import jakarta.validation.constraints.NotNull;

public record UserLoginDto(
        @NotNull
        String login,

        @NotNull
        String password
) {

}
