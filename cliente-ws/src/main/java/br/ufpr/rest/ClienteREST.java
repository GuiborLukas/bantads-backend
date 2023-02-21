package br.ufpr.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.model.Cliente;
import br.ufpr.model.ClienteDTO;
import br.ufpr.repository.ClienteRepository;


@CrossOrigin
@RestController
@RequestMapping(value = "clientes")
public class ClienteREST {
	@Autowired
	private ClienteRepository repo;
	@Autowired
	private ModelMapper mapper;

		@GetMapping("/clientes")
		public ResponseEntity<List<ClienteDTO>> obterTodosClientes() {

			try {
				List<Cliente> lista = repo.findAll();
				if (lista.isEmpty()) {
					// Se nao existir nenhum, retorna HTTP 404 Not Found
					return ResponseEntity.notFound().build();
				}
				// Converte lista de Entity para lista DTO

				List<ClienteDTO> DTOlist = lista.stream().map(usu -> mapper.map(usu, ClienteDTO.class))
						.collect(Collectors.toList());

				return ResponseEntity.ok(DTOlist);

			}catch(Exception e) {
				System.err.println("Erro buscar todos clientes: " + e.toString());
				return ResponseEntity.internalServerError().body(null);
			}
		}
		
		@GetMapping("/clientes/{id}")
		public ResponseEntity<ClienteDTO> buscaClientePorId(@PathVariable("id") Long id) {
			try {
				// Busca o usuario pelo id
				Optional<Cliente> usuOpt = repo.findById(id);
				if (usuOpt.isPresent()) {
					// Se ele existe, retorna HTTP 200 - OK e o usuario
					return ResponseEntity.ok(mapper.map(usuOpt.get(), ClienteDTO.class));
				} else {
					// Caso contrario, retorna HTTP 404 = Not Found
					return ResponseEntity.notFound().build();
				}

			} catch (Exception e) {
				System.err.println("Erro buscar usuario por ID:" + e.toString());
				return ResponseEntity.internalServerError().body(null);
			}
		}
		
		@PostMapping("/clientes")
		public ResponseEntity<ClienteDTO> insereCliente(@RequestBody Cliente cliente) {
			
			try {
			// Se cliente ja existir, retorna HTTP 409 - Conflict
			if (repo.findByCpf(cliente.getCpf()) != null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
			}else {
				repo.save(mapper.map(cliente, Cliente.class));
				Optional<Cliente> cli = repo.findByCpf(cliente.getCpf());
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(cli, ClienteDTO.class));
			}
			}catch(Exception e) {
				System.err.println("Erro inserir cliente:" + e.toString());
				// Se deu erro ao salvar, retorna HTTP 500 - Internal server error
				return ResponseEntity.internalServerError().body(null);
			}
		}
		
		@PutMapping("/clientes/{id}")
		public ResponseEntity<ClienteDTO> alterarCliente(@PathVariable("id") long id, @RequestBody Cliente cliente) {
			
			try{
				Optional<Cliente> cli = repo.findById(id);
	
				if (cli.isEmpty()) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				} else {
					cliente.setId(id);
					repo.save(mapper.map(cliente, Cliente.class));
					cli = repo.findById(id);
					return ResponseEntity.status(HttpStatus.OK).body(mapper.map(cli, ClienteDTO.class));
				}
			}catch(Exception e) {
				System.err.println("Erro alterar cliente:" + e.toString());
				// Se deu erro ao salvar, retorna HTTP 500 - Internal server error
				return ResponseEntity.internalServerError().body(null);
			}
		}
		
		@DeleteMapping("/clientes/{id}")
		public ResponseEntity removerCliente(@PathVariable("id") long id) {

			try {
				Optional<Cliente> cliente = repo.findById(id);
				if (cliente.isEmpty()) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				} else {
					repo.delete(mapper.map(cliente, Cliente.class));
					return ResponseEntity.status(HttpStatus.OK).body(null);
				}
			}catch(Exception e ) {
				System.err.println("Erro remover cliente:" + e.toString());
				// Se deu erro ao salvar, retorna HTTP 500 - Internal server error
				return ResponseEntity.internalServerError().body(null);
			}
		}
}
