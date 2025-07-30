package com.forumhub.apitopicos.dto;

import com.forumhub.apitopicos.domain.topico.StatusTopico;
import com.forumhub.apitopicos.domain.topico.Topico; // Importe a entidade Topico

import java.time.LocalDateTime;

// DTO para retornar os detalhes de um tópico (geralmente após criação ou para consulta)
public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        String autor,
        String curso,
        StatusTopico status,
        LocalDateTime dataCriacao,
        boolean ativo
) {
    // Construtor que recebe a entidade Topico para criar o DTO
    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getAutor(),
                topico.getCurso(), topico.getStatus(), topico.getDataCriacao(), topico.isAtivo());
    }
}