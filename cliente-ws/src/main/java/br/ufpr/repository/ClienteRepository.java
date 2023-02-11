package br.ufpr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	public Cliente findByCpf(String cpf);
	
}
