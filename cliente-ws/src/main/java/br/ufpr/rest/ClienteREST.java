package br.ufpr.rest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.model.Cliente;
import br.ufpr.model.Usuario;

@CrossOrigin
@RestController
public class ClienteREST {

	public static List<Cliente> lista = new ArrayList<>();

		@GetMapping("/clientes")
		public List<Cliente> obterTodosClientes() {
			return lista;
		}

		@GetMapping("/clientes/{id}")
		public Cliente obterTodosClientes(@PathVariable("id") int id) {
			Cliente c = lista.stream().filter(usu -> usu.getId() == id).findAny().orElse(null);
			return c;
		}

		@PostMapping("/clientes")
		public Cliente inserirCliente(@RequestBody Cliente cliente) {
			Cliente c = lista.stream().max(Comparator.comparing(Cliente::getId)).orElse(null);
			if (c == null)
				cliente.setId(1);
			else
				cliente.setId(c.getId() + 1);
			lista.add(cliente);
			return cliente;
		}

		@PutMapping("/clientes/{id}")
		public Cliente alterarCliente(@PathVariable("id") int id, @RequestBody Cliente cliente) {
			Cliente c = lista.stream().filter(cli -> cli.getId() == id).findAny().orElse(null);
			if (c != null) {
				c.setNome(cliente.getNome());
				c.setEmail(cliente.getEmail());
				c.setCpf(cliente.getCpf());
				c.setTelefone(cliente.getTelefone());
				c.setSalario(cliente.getSalario());
				c.setUsuario(cliente.getUsuario());
				c.setEndereco(cliente.getEndereco());
			}
			return c;
		}

		@DeleteMapping("/clientes/{id}")
		public Cliente removerCliente(@PathVariable("id") int id) {
			Cliente cliente = lista.stream().filter(usu -> usu.getId() == id).findAny().orElse(null);
			if (cliente != null)
				lista.removeIf(c -> c.getId() == id);
			return cliente;
		}

}
