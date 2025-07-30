package com.forumhub.apitopicos.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value; // Importe esta anotação
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component; // Importe esta anotação
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component // <--- ADICIONADO: Torna o filtro um componente gerenciado pelo Spring
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}") // <--- ADICIONADO: Injeta o valor da propriedade 'jwt.secret'
    private String secret; // <--- ADICIONADO: Variável para armazenar a chave secreta

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            try {
                String jwt = token.substring(7);
                // Usando a chave secreta injetada, que deve ser a mesma usada para assinar o token
                String login = JWT.require(Algorithm.HMAC256(secret))
                        .build()
                        .verify(jwt)
                        .getSubject();
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(login, null, null));
            } catch (Exception e) {
                // É altamente recomendável logar a exceção completa para depuração em ambientes de desenvolvimento
                System.err.println("Erro na verificação do JWT: " + e.getMessage());
                // Você pode até logar a stack trace: e.printStackTrace();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}