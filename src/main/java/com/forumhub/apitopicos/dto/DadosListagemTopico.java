package com.forumhub.apitopicos.dto;

import com.forumhub.apitopicos.domain.topico.StatusTopico;
import com.forumhub.apitopicos.domain.topico.Topico;

import java.time.LocalDateTime;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String mensagem,
        String autor,
        String curso,
        StatusTopico status,
        LocalDateTime dataCriacao
) {
    public DadosListagemTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getAutor(),
                topico.getCurso(),
                topico.getStatus(),
                topico.getDataCriacao()
        );
    }
}
