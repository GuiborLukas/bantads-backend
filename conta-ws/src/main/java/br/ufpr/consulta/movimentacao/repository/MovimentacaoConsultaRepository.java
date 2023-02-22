package br.ufpr.consulta.movimentacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.shared.movimentacao.model.Movimentacao;

public interface MovimentacaoConsultaRepository extends JpaRepository<Movimentacao, Long>{
	
	Optional<Movimentacao> findById(Long id);

}

