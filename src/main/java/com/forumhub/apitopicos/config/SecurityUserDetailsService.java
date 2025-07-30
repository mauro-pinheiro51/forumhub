package com.forumhub.apitopicos.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)) {
            return User.withUsername("admin")
                    .password("$2a$10$EsX3VlIPZGCjxWcnkaIpe.bW9sftM71JGFxkE.J5jKkB9AD0ARXSS") // Hash de Admin123!@#
                    .roles("USER")
                    .build();
        }
        throw new UsernameNotFoundException("Usuário não encontrado: " + username);
    }
}