package dev.matheuslf.desafio.inscritos.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${minha.chave.secreta}")
    private String secretWord;

    public String generateToken(UserDetails userDetails) {
        System.out.println("Gerando token para: " + userDetails.getUsername());
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secretWord);
            String token = JWT.create()
                    .withIssuer("virtus-api")
                    .withSubject(userDetails.getUsername())
                    .withExpiresAt(generateDateExpiration())
                    .sign(algoritmo);

            return token;
        } catch (JWTCreationException erro) {
            return erro.getMessage();
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secretWord);
            return JWT.require(algoritmo)
                    .withIssuer("virtus-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException erro) {
            return erro.getMessage();
        }
    }

    public Instant generateDateExpiration() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}