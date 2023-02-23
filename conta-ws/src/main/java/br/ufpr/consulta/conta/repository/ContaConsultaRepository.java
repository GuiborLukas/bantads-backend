package br.ufpr.consulta.conta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.commons.conta.model.ContaD;

public interface ContaConsultaRepository extends JpaRepository<ContaD, Long>{
	
	Optional<ContaD> findById(Long id);

	Optional<ContaD> findByCliente(Long cliente);

	List<ContaD> findByGerente(Long gerente);
	
//	List<ContaD> findByGerente(Long gerente);
	
}

