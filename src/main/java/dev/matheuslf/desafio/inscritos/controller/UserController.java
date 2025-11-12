package dev.matheuslf.desafio.inscritos.controller;

import dev.matheuslf.desafio.inscritos.dto.userDto.UserCreateDto;
import dev.matheuslf.desafio.inscritos.dto.userDto.UserLoginDto;
import dev.matheuslf.desafio.inscritos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "Usuários", description = "Gerenciamento de usuários")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastro de usuário", description = "Cadastra um novo usuário para ter acesso aos projetos e tasks")
    public ResponseEntity<String> userRegister(@RequestBody @Valid UserCreateDto userCreateDto)
    {
        return userService.createUser(userCreateDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Login do usuário", description = "Login realizado pelo usuário para acesso aos projetos e tasks")
    public ResponseEntity<String> loginUser(
            @RequestBody
            @Valid UserLoginDto userLoginDto
    ){
        return userService.login(userLoginDto);
    }
}
