package com.forumhub.apitopicos.dto;

import com.forumhub.apitopicos.domain.topico.StatusTopico;
import jakarta.validation.constraints.NotNull; // Importe NotNull
import jakarta.validation.constraints.Size; // Importe Size

public record DadosAtualizacaoTopico(
        // Para atualização, geralmente os campos não são @NotBlank
        // pois são opcionais. Você só envia o que quer alterar.
        // No entanto, se precisar validar tamanho ou outros critérios, pode usar @Size, etc.
        @Size(max = 255) // Exemplo: Título não pode ter mais de 255 caracteres
        String titulo,
        @Size(max = 1000) // Exemplo: Mensagem não pode ter mais de 1000 caracteres
        String mensagem,
        @Size(max = 100)
        String autor, // Adicione autor se for atualizável
        @Size(max = 100)
        String curso, // Adicione curso se for atualizável
        StatusTopico status // Adicione status se for atualizável
) {
}