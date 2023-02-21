package br.ufpr.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
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

		try {

			Usuario usu = repository.findByEmail(login.getEmail());

			// Se nao encontrou o usuario, retorna HTTP 401 - Unauthorized
			if (usu == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}
			// Se encontrou, valida o md5 da senha
			// Exceto pra admin pq admin ja foi inserido manualmente no banco, ai pode
			// passar so a senha direto

			Boolean senhaValida;
			System.out.println(usu.toString());
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
		} catch (Exception e) {
			System.err.println("Erro inserir usuario:" + e.toString());
			e.printStackTrace();
			// Se deu erro ao salvar, retorna HTTP 500 - Internal server error
			return ResponseEntity.internalServerError().body(null);
		}
	}
}
