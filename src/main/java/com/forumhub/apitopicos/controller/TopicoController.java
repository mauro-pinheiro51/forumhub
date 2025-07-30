package com.forumhub.apitopicos.controller;

import com.forumhub.apitopicos.domain.topico.Topico;
import com.forumhub.apitopicos.dto.DadosCadastroTopico;
import com.forumhub.apitopicos.dto.DadosListagemTopico;
import com.forumhub.apitopicos.dto.DadosAtualizacaoTopico;
import com.forumhub.apitopicos.dto.DadosDetalhamentoTopico;
import com.forumhub.apitopicos.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

// --- NOVOS IMPORTS NECESSÁRIOS PARA SEGURANÇA ---
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus; // Para retornar 403 Forbidden

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoTopico> cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder) {
        // --- ADICIONANDO O AUTOR DO TÓPICO COM BASE NO USUÁRIO LOGADO ---
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginUsuarioLogado = authentication.getName(); // Obtém o login do usuário autenticado

        // Cria uma nova instância de DadosCadastroTopico para garantir que o autor seja o usuário logado
        // Isso impede que o usuário tente se passar por outro autor
        DadosCadastroTopico dadosComAutorLogado = new DadosCadastroTopico(
                dados.titulo(),
                dados.mensagem(),
                loginUsuarioLogado, // Sobrescreve o autor com o login do usuário logado
                dados.curso()
        );

        Topico topico = new Topico(dadosComAutorLogado); // Usa o DTO com o autor corrigido
        repository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    // ... (método listar, detalhar) ...

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTopico dados) {
        Optional<Topico> topicoOptional = repository.findById(id);

        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topico topico = topicoOptional.get();

        // --- LÓGICA DE AUTORIZAÇÃO PARA ATUALIZAÇÃO ---
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginUsuarioLogado = authentication.getName();

        if (!topico.getAutor().equals(loginUsuarioLogado)) {
            // Se o usuário logado NÃO for o autor do tópico, retorna 403 Forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        topico.atualizarDados(dados);
        repository.save(topico);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Optional<Topico> topicoOptional = repository.findById(id);

        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topico topico = topicoOptional.get();

        // --- LÓGICA DE AUTORIZAÇÃO PARA EXCLUSÃO ---
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginUsuarioLogado = authentication.getName();

        if (!topico.getAutor().equals(loginUsuarioLogado)) {
            // Se o usuário logado NÃO for o autor do tópico, retorna 403 Forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        topico.excluir();
        repository.save(topico);
        return ResponseEntity.noContent().build();
    }

    // ... (método listar, detalhar) ...
    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(
            @PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao,
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer ano
    ) {
        Page<Topico> pageTopicos;

        if (curso != null || ano != null) {
            pageTopicos = repository.findByCursoAndDataCriacaoYear(curso, ano, paginacao);
        } else {
            pageTopicos = repository.findAllByAtivoTrue(paginacao);
        }

        Page<DadosListagemTopico> pageDTO = pageTopicos.map(DadosListagemTopico::new);
        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) { // Alterado para DadosDetalhamentoTopico
        Optional<Topico> topicoOptional = repository.findById(id);

        if (topicoOptional.isPresent()) {
            DadosDetalhamentoTopico dados = new DadosDetalhamentoTopico(topicoOptional.get());
            return ResponseEntity.ok(dados);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}