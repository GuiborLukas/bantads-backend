package br.ufpr.consulta.conta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.shared.conta.model.ContaD;

public interface ContaConsultaRepository extends JpaRepository<ContaD, Long>{
	
	Optional<ContaD> findById(Long id);

	Optional<ContaD> findByCliente(Long cliente);
	
}

