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

		@GetMapping
		public ResponseEntity<List<ClienteDTO>> obterTodosClientes() {

			List<Cliente> lista = repo.findAll();

			if(lista.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			return ResponseEntity.status(HttpStatus.OK)
					.body(lista.stream().map(e -> mapper.map(e, ClienteDTO.class)).collect(Collectors.toList()));

			
		}
		
		@GetMapping("/{id}")
		public ResponseEntity<ClienteDTO> buscaClientePorId(@PathVariable("id") Long id) {
			try {
				// Busca o usuario pelo id
				Optional<Cliente> usuOpt = repo.findById(id);
				if (usuOpt.isPresent()) {
					// Se ele existe, retorna HTTP 200 - OK e o usuario
					return ResponseEntity.status(HttpStatus.OK).body(mapper.map(usuOpt.get(), ClienteDTO.class));
				} else {
					// Caso contrario, retorna HTTP 404 = Not Found
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				}

			} catch (Exception e) {
				System.err.println("Erro buscar usuario por ID:" + e.toString());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		}
		
		@PostMapping
		public ResponseEntity<ClienteDTO> insereCliente(@RequestBody ClienteDTO cliente) {
			
			try {
			Optional<Cliente> clienteBD = repo.findByCpf(cliente.getCpf());
			if (clienteBD.isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
			}else {
				repo.save(mapper.map(cliente, Cliente.class));
				Optional<Cliente> cli = repo.findByCpf(cliente.getCpf());
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(cli, ClienteDTO.class));
			}
			}catch(Exception e) {
				System.err.println("Erro inserir cliente:" + e.toString());
				// Se deu erro ao salvar, retorna HTTP 500 - Internal server error
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		}
		
		@PutMapping("/{id}")
		public ResponseEntity<ClienteDTO> alterarCliente(@PathVariable("id") long id, @RequestBody ClienteDTO cliente) {
			
			try{
				Optional<Cliente> cli = repo.findById(id);
	
				if (cli.isEmpty()) {
					return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
				} else {
					cliente.setId(id);
					repo.save(mapper.map(cliente, Cliente.class));
					cli = repo.findById(id);
					return ResponseEntity.status(HttpStatus.OK).body(mapper.map(cli, ClienteDTO.class));
				}
			}catch(Exception e) {
				System.err.println("Erro alterar cliente:" + e.toString());
				// Se deu erro ao salvar, retorna HTTP 500 - Internal server error
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		}
		
		@DeleteMapping("/{id}")
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
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		}
}
