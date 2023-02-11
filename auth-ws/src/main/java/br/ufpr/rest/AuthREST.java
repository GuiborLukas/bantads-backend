package br.ufpr.rest;

	import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RestController;

import br.ufpr.model.Usuario;
import br.ufpr.repository.UsuarioRepository;

	@RestController
	public class AuthREST {

	  @Autowired
	  private UsuarioRepository repository;

	  @PostMapping("/usuarios")
	  public Usuario create(@RequestBody Usuario usuario) {
	    return repository.save(usuario);
	  }
	  
	  @GetMapping("/usuarios")
	  public List<Usuario> listarTodos(){
	  List<Usuario> lista = repository.findAll();
	  	return lista.stream().collect(Collectors.toList());
	  }
}
