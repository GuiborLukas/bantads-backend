package br.ufpr.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.model.Cliente;
import br.ufpr.model.ClienteDTO;
import br.ufpr.repository.ClienteRepository;

@CrossOrigin
@RestController
public class ClienteREST {

	@Autowired
	private ClienteRepository repo;
	@Autowired
	private ModelMapper mapper;
	
		@GetMapping("/clientes")
		public List<ClienteDTO> obterTodosClientes() {
			List<Cliente> lista = repo.findAll();
			
			// Converte lista de Entity para lista DTO
			return lista.stream().map(e -> mapper.map(e,ClienteDTO.class)).collect(Collectors.toList());
		}

//		@GetMapping("/clientes/{id}")
//		public Cliente obterTodosClientes(@PathVariable("id") int id) {
//			Cliente c = lista.stream().filter(usu -> usu.getId() == id).findAny().orElse(null);
//			return c;
//		}

//		@PostMapping("/clientes")
//		public Cliente inserirCliente(@RequestBody Cliente cliente) {
//			Cliente c = lista.stream().max(Comparator.comparing(Cliente::getId)).orElse(null);
//			if (c == null)
//				cliente.setId(1);
//			else
//				cliente.setId(c.getId() + 1);
//			lista.add(cliente);
//			return cliente;
//		}
//
//		@PutMapping("/clientes/{id}")
//		public Cliente alterarCliente(@PathVariable("id") int id, @RequestBody Cliente cliente) {
//			Cliente c = lista.stream().filter(cli -> cli.getId() == id).findAny().orElse(null);
//			if (c != null) {
//				c.setNome(cliente.getNome());
//				c.setEmail(cliente.getEmail());
//				c.setCpf(cliente.getCpf());
//				c.setTelefone(cliente.getTelefone());
//				c.setSalario(cliente.getSalario());
//			}
//			return c;
//		}
//
//		@DeleteMapping("/clientes/{id}")
//		public Cliente removerCliente(@PathVariable("id") int id) {
//			Cliente cliente = lista.stream().filter(usu -> usu.getId() == id).findAny().orElse(null);
//			if (cliente != null)
//				lista.removeIf(c -> c.getId() == id);
//			return cliente;
//		}

}
