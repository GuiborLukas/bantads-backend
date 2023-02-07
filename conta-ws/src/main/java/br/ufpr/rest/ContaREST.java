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

import br.ufpr.model.Conta;

@CrossOrigin
@RestController

public class ContaREST {

	public static List<Conta> lista = new ArrayList<>();
	
	@GetMapping("/contas")
	public List<Conta> obterTodasContas(){
		return lista;
	}
	
	@GetMapping("/contas/{id}")
	public Conta obterTodasContas(@PathVariable("id") int id) {
		
		Conta c = lista.stream().filter(con -> con.getId() == id).findAny().orElse(null);
		return c;
	}
	
	@PostMapping("/contas")
	public Conta inseriConta(@RequestBody Conta conta) {
		Conta c = lista.stream().max(Comparator.comparing(Conta::getId)).orElse(null);
		
		if (c == null)
			conta.setId(1);
		else
			conta.setId(c.getId()+1);
		lista.add(conta);
		return conta;
	}
	
	@PutMapping("/contas/{id}")
	public Conta alterarConta(@PathVariable("id") int id, @RequestBody Conta conta) {
		Conta c = lista.stream().filter(con -> con.getId() == id).findAny().orElse(null);
		
		if(c != null) {
			c.setCliente(conta.getCliente());
			c.setDataCriacao(conta.getDataCriacao());
			c.setGerente(conta.getGerente());
			c.setLimite(conta.getLimite());
		}
		
		return c;
	}
	
	@DeleteMapping("/contas/{id}")
	public Conta removerConta(@PathVariable("id") int id) {
		
		Conta conta = lista.stream().filter(con -> con.getId() == id).findAny().orElse(null);
		
		if(conta != null)
			lista.removeIf(c -> c.getId()==id);
		return conta;
	}
}
