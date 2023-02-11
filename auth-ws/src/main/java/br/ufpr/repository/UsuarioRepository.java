package br.ufpr.repository;

import org.springframework.data.mongodb.repository.*;

import br.ufpr.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, Long> {
	
	public Usuario findByLogin(String login);
	
}
