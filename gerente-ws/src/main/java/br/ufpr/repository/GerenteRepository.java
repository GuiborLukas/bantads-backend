package br.ufpr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.model.Gerente;

public interface GerenteRepository extends JpaRepository<Gerente, Long> {

	Optional<Gerente> findById(Long id);

	Optional<Gerente> findByCpf(String cpf);

}
