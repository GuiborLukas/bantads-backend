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

import br.ufpr.model.Gerente;


@CrossOrigin
@RestController

public class GerenteREST {

	public static List<Gerente> lista = new ArrayList(); 
		@GetMapping("/gerentes")
		public List<Gerente> obterTodosGerentes(){
			return lista;
		}
		
		@GetMapping("/gerentes/{id}")
		public Gerente obterTodosGerentes(@PathVariable("id") int id) {
			
			Gerente g = lista.stream().filter(ger -> ger.getId() == id).findAny().orElse(null);
			return g;
		}
		
		@PostMapping("/gerentes")
		public Gerente inseriGerente(@RequestBody Gerente gerente) {
			Gerente g = lista.stream().max(Comparator.comparing(Gerente::getId)).orElse(null);
			
			if (g == null)
				gerente.setId(1);
			else
				gerente.setId(g.getId()+1);
			lista.add(gerente);
			return gerente;
		}
		
		@PutMapping("/gerentes/{id}")
		public Gerente alterarGerente(@PathVariable("id") int id, @RequestBody Gerente gerente) {
			Gerente g = lista.stream().filter(con -> con.getId() == id).findAny().orElse(null);
			
			if(g != null) {
				g.setCPF(gerente.getCPF());
				g.setEmail(gerente.getEmail());
				g.setNome(gerente.getNome());
				g.setTelefone(gerente.getTelefone());
			}
			
			return g;
		}
		
		@DeleteMapping("/gerentes/{id}")
		public Gerente removerGerente(@PathVariable("id") int id) {
			
			Gerente gerente = lista.stream().filter(gen -> gen.getId() == id).findAny().orElse(null);
			
			if(gerente != null)
				lista.removeIf(c -> c.getId()==id);
			return gerente;
		}
}
