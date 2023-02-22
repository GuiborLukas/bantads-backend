package br.ufpr.consulta.movimentacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.shared.movimentacao.model.MovimentacaoD;

public interface MovimentacaoConsultaRepository extends JpaRepository<MovimentacaoD, Long>{
	
	Optional<MovimentacaoD> findById(Long id);

}

