package com.forumhub.apitopicos.controller;

import com.forumhub.apitopicos.dto.DadosAutenticacao;
import com.forumhub.apitopicos.dto.DadosTokenJWT; // Importação da nova classe DTO
import com.forumhub.apitopicos.service.TokenService; // Importação do TokenService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired // Nova injeção de dependência para o TokenService
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DadosAutenticacao dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var autenticacao = authenticationManager.authenticate(authToken);

        // Gera o token usando o login do usuário autenticado
        var tokenJWT = tokenService.gerarToken(autenticacao.getName());

        // Retorna o token JWT na resposta
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}