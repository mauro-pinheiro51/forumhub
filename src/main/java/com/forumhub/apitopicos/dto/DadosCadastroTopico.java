package com.forumhub.apitopicos.dto;

// Import de StatusTopico removido se não for mais usado aqui
// import com.forumhub.apitopicos.domain.topico.StatusTopico;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroTopico(
        @NotBlank String titulo,
        @NotBlank String mensagem,
        @NotBlank String autor,
        @NotBlank String curso
        // StatusTopico status // <-- Esta linha foi REMOVIDA
) {
}