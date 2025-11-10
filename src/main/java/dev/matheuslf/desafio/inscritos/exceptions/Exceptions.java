package dev.matheuslf.desafio.inscritos.exceptions;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class Exceptions {
    private String message;


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runTimeError(
            RuntimeException runtimeException,
            HttpServletResponse httpServletResponse
    ){
        message = "Ocorreu o seguinte erro: " + runtimeException.getMessage();

        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodException(
            MethodArgumentNotValidException methodArgumentNotValidException,
            HttpServletResponse httpServletResponse
    ){
        final var fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();
        message = fieldError != null ? fieldError.getDefaultMessage() : "Erro de validação";

        return ResponseEntity.badRequest().body("Ocorreu o seguinte erro: " + message);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> noResourceException (
         NoResourceFoundException noResourceFoundException,
         HttpServletResponse httpServletResponse
    ) {
        final var response = noResourceFoundException.getMessage();

        return ResponseEntity.badRequest().body(response);
    }
}
