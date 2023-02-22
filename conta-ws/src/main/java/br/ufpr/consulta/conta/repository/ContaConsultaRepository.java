package br.ufpr.consulta.conta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.shared.conta.model.Conta;

public interface ContaConsultaRepository extends JpaRepository<Conta, Long>{
	
	Optional<Conta> findById(Long id);

	Optional<Conta> findByCliente(Long cliente);
	
}

