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

	public static List<Gerente> lista = new ArrayList<>();

		@GetMapping("/gerentes")
		public List<Gerente> obterTodosGerentes() {
			return lista;
		}

		@GetMapping("/gerentes/{id}")
		public Gerente obterTodosGerentes(@PathVariable("id") int id) {
			Gerente c = lista.stream().filter(usu -> usu.getId() == id).findAny().orElse(null);
			return c;
		}

		@PostMapping("/gerentes")
		public Gerente inserirGerente(@RequestBody Gerente gerente) {
			Gerente c = lista.stream().max(Comparator.comparing(Gerente::getId)).orElse(null);
			if (c == null)
				gerente.setId(1);
			else
				gerente.setId(c.getId() + 1);
			lista.add(gerente);
			return gerente;
		}

		@PutMapping("/gerentes/{id}")
		public Gerente alterarGerente(@PathVariable("id") int id, @RequestBody Gerente gerente) {
			Gerente c = lista.stream().filter(ger -> ger.getId() == id).findAny().orElse(null);
			if (c != null) {
				c.setNome(gerente.getNome());
				c.setEmail(gerente.getEmail());
				c.setCpf(gerente.getCpf());
				c.setTelefone(gerente.getTelefone());
				}
			return c;
		}

		@DeleteMapping("/gerentes/{id}")
		public Gerente removerGerente(@PathVariable("id") int id) {
			Gerente gerente = lista.stream().filter(usu -> usu.getId() == id).findAny().orElse(null);
			if (gerente != null)
				lista.removeIf(c -> c.getId() == id);
			return gerente;
		}

		static {
		    lista.add(new Gerente(1, "gerente teste", "gerente@gerente.com.br", "11111111111", "11999999999"));
		    lista.add(new Gerente(2, "Fran", "fran@fran.", "00000000000", "41090909888"));
		    lista.add(new Gerente(3, "Luis", "luisgerente@email.com", "11111111111", "41999999999"));
		}
}
