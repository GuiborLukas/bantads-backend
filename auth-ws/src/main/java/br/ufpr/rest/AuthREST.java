package br.ufpr.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.model.LoginDTO;
import br.ufpr.model.Usuario;
import br.ufpr.model.UsuarioDTO;
import br.ufpr.repository.UsuarioRepository;

@RestController
public class AuthREST {

	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/login")
	public ResponseEntity<UsuarioDTO> login(@RequestBody LoginDTO login) {

		Usuario usu = repository.findByEmail(login.getEmail());

		// Se nao encontrou o usuario, retorna HTTP 401 - Unauthorized
		if (usu == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		// Se encontrou, valida o md5 da senha
		// Exceto pra admin pq admin ja foi inserido manualmente no banco, ai pode
		// passar so a senha direto

		Boolean senhaValida;
		if (usu.getPerfil().equals("ADMIN")) {
			senhaValida = login.getSenha().equals(usu.getSenha());
		} else {
			senhaValida = bCryptPasswordEncoder.matches(login.getSenha(), usu.getSenha());
		}

		// Se a validacao da senha deu erro, retorna HTTP 401 - Unauthorized
		if (!senhaValida) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		return ResponseEntity.ok(mapper.map(usu, UsuarioDTO.class));
	}

	@PostMapping("/usuario")
	public ResponseEntity<UsuarioDTO> insereUsuario(@RequestBody Usuario usuario) {
		// Se usuario ja existir, retorna HTTP 409 - Conflict
		if (repository.findByEmail(usuario.getEmail()) != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}

		try {
			// Encripta a senha do usuario antes de salvar
			String senhaEncriptada = bCryptPasswordEncoder.encode(usuario.getSenha());
			usuario.setSenha(senhaEncriptada);
			// Salva o usuario
			repository.save(usuario);

			Usuario usu = repository.findByEmail(usuario.getEmail());

			// Se deu tudo certo, retorna HTTP 201 - Created e o usuário criado
			return ResponseEntity.created(null).body(mapper.map(usu, UsuarioDTO.class));

		} catch (Exception e) {
			System.err.println("Erro inserir usuario:" + e.toString());
			// Se deu erro ao salvar, retorna HTTP 500 - Internal server error
			return ResponseEntity.internalServerError().body(null);
		}

	}

	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<UsuarioDTO> removeUsuario(@PathVariable String id) {
		try {
			// Busca o usuario pelo id
			Optional<Usuario> usuOpt = repository.findById(id);
			if (usuOpt.isPresent()) {
				// Se ele existe, remove e retorna HTTP 204 - No Content
				repository.delete(usuOpt.get());
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			} else {
				// Caso contrario, retorna HTTP 404 - Not Found
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			System.err.println("Erro remover usuario:" + e.toString());
			return ResponseEntity.internalServerError().body(null);
		}
	}

	@GetMapping("/usuario/{id}")
	public ResponseEntity<UsuarioDTO> buscaUsuarioPorId(@PathVariable String id) {
		try {
			// Busca o usuario pelo id
			Optional<Usuario> usuOpt = repository.findById(id);
			if (usuOpt.isPresent()) {
				// Se ele existe, retorna HTTP 200 - OK e o usuario
				return ResponseEntity.ok(mapper.map(usuOpt.get(), UsuarioDTO.class));
			} else {
				// Caso contrario, retorna HTTP 404 = Not Found
				return ResponseEntity.notFound().build();
			}

		} catch (Exception e) {
			System.err.println("Erro buscar usuario por ID:" + e.toString());
			return ResponseEntity.internalServerError().body(null);
		}
	}

	@GetMapping("/usuarios")
	public ResponseEntity<List<UsuarioDTO>> buscaTodosUsuarios() {
		try {
			// Busca todos os usuarios salvos
			List<Usuario> list = repository.findAll();
			if (list.isEmpty()) {
				// Se nao existir nenhum, retorna HTTP 404 Not Found
				return ResponseEntity.notFound().build();
			} else {
				// Caso contrario, retorna a HTTP 200 - OK e a lista de usuários
				List<UsuarioDTO> DTOlist = list.stream().map(usu -> mapper.map(usu, UsuarioDTO.class))
						.collect(Collectors.toList());

				return ResponseEntity.ok(DTOlist);
			}
		} catch (Exception e) {
			System.err.println("Erro buscar todos usuarios: " + e.toString());
			return ResponseEntity.internalServerError().body(null);
		}
	}

	@PutMapping("/usuario/{id}")
	public ResponseEntity<UsuarioDTO> atualizaUsuario(@PathVariable String id,
			@RequestBody Usuario usuarioAtualizado) {
		try {
			// Busca o usuario pelo ID
			Optional<Usuario> usuOpt = repository.findById(id);
			if (!usuOpt.isPresent()) {
				// Se nao encontrou, retorna HTTP 404 - Not Found
				return ResponseEntity.notFound().build();
			} else {
				// Se encontrou, atualiza para os dados informados
				Usuario usuario = usuOpt.get();
				usuario.setNome(usuarioAtualizado.getNome());
				usuario.setEmail(usuarioAtualizado.getEmail());

				String senhaEncriptada = bCryptPasswordEncoder.encode(usuarioAtualizado.getSenha());
				usuario.setSenha(senhaEncriptada);

				usuario.setPerfil(usuarioAtualizado.getPerfil());
				usuario = repository.save(usuario);
				// Retorna HTTP 200 - OK e o usuario atualizado
				return ResponseEntity.ok(mapper.map(usuario, UsuarioDTO.class));
			}

		} catch (Exception e) {
			System.err.println("Erro atualizar usuario: " + e.toString());
			return ResponseEntity.internalServerError().body(null);
		}

	}
}
