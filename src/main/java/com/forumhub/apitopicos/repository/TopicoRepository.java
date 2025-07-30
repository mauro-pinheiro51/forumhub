package com.forumhub.apitopicos.repository;

import com.forumhub.apitopicos.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // NOVO IMPORT: Para usar queries JPQL customizadas
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // Método para listar todos os tópicos ativos com paginação
    Page<Topico> findAllByAtivoTrue(Pageable paginacao);

    // NOVO MÉTODO: Busca por curso E (opcionalmente) por ano da data de criação
    // A query JPQL permite filtrar por curso e pelo ano da data de criação.
    // Os parâmetros :curso e :ano são opcionais (se forem NULL, o filtro é ignorado para aquele parâmetro).
    @Query("SELECT t FROM Topico t WHERE t.ativo = true " +
            "AND (:curso IS NULL OR t.curso = :curso) " +
            "AND (:ano IS NULL OR FUNCTION('YEAR', t.dataCriacao) = :ano)")
    Page<Topico> findByCursoAndDataCriacaoYear(String curso, Integer ano, Pageable paginacao);
}