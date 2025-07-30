package com.forumhub.apitopicos.domain.topico;

import com.forumhub.apitopicos.dto.DadosAtualizacaoTopico;
import com.forumhub.apitopicos.dto.DadosCadastroTopico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "topicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensagem;
    private String autor;
    private String curso;

    @Enumerated(EnumType.STRING)
    private StatusTopico status;

    private LocalDateTime dataCriacao;

    private boolean ativo;

    public Topico(String titulo, String mensagem, String autor, String curso, StatusTopico status) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.autor = autor;
        this.curso = curso;
        this.dataCriacao = LocalDateTime.now();
        this.status = status;
        this.ativo = true;
    }

    public Topico(DadosCadastroTopico dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.autor = dados.autor();
        this.curso = dados.curso();
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusTopico.NAO_SOLUCIONADO; // <-- ALTERADO DE NAO_RESPONDIDO PARA NAO_SOLUCIONADO
        this.ativo = true;
    }

    public void atualizarDados(DadosAtualizacaoTopico dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
        if (dados.autor() != null) {
            this.autor = dados.autor();
        }
        if (dados.curso() != null) {
            this.curso = dados.curso();
        }
        if (dados.status() != null) {
            this.status = dados.status();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}