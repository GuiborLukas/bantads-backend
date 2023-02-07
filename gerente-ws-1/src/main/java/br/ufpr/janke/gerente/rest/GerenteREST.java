package br.ufpr.janke.gerente.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.janke.gerente.model.Gerente;


@CrossOrigin
@RestController

public class GerenteREST {

	public static List<Gerente> lista = new ArrayList(); 
		@GetMapping("/gerentes")
		public List<Gerente> obterTodosGerentes(){
			return lista;
		}
		
}
