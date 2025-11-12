package dev.matheuslf.desafio.inscritos.service;

import dev.matheuslf.desafio.inscritos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);

        UserDetails userDetails = userRepository.findByLogin(username);

        if (userDetails == null){
            throw new RuntimeException("Usuario nao identificado");
        }
        return userDetails;
    }
}
