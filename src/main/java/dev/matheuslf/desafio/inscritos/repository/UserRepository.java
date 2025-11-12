package dev.matheuslf.desafio.inscritos.repository;


import dev.matheuslf.desafio.inscritos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByLogin(String login);
}
