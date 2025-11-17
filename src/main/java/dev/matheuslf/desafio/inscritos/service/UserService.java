package dev.matheuslf.desafio.inscritos.service;

import dev.matheuslf.desafio.inscritos.config.security.TokenService;
import dev.matheuslf.desafio.inscritos.dto.userDto.UserCreateDto;
import dev.matheuslf.desafio.inscritos.dto.userDto.UserLoginDto;
import dev.matheuslf.desafio.inscritos.dto.userDto.UserResponseDto;
import dev.matheuslf.desafio.inscritos.model.User;
import dev.matheuslf.desafio.inscritos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<String> createUser(UserCreateDto userCreateDto){
        if (userRepository.existsByEmail(userCreateDto.email())){
            throw new RuntimeException("Já existe um usuario com o email cadastrado.");
        }

        String passwordEncripted = new BCryptPasswordEncoder().encode(userCreateDto.password());

        User userSave = new User(
                userCreateDto.email(),
                userCreateDto.login(),
                passwordEncripted);

        User userSavedInDataBase = userRepository.save(userSave);

        if (userSavedInDataBase.toString().isEmpty()){
            throw new RuntimeException("Erro ao salvar usuario.");
        }

        return ResponseEntity.ok("Usuario cadastrado com sucesso!");
    }

    public UserResponseDto login(UserLoginDto userLoginDto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(userLoginDto.login(), userLoginDto.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken( (User) auth.getPrincipal());

        return new UserResponseDto(token);
    }
}
