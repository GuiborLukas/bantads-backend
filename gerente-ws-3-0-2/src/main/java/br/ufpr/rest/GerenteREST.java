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
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.model.Gerente;
import br.ufpr.model.GerenteDTO;
import br.ufpr.repository.GerenteRepository;

@CrossOrigin
@RestController

public class GerenteREST {

	@Autowired
	private GerenteRepository repo;

	@Autowired
	private ModelMapper mapper;

	@GetMapping("/gerentes")
	public ResponseEntity<List<GerenteDTO>> obterTodosGerentes() {

		List<Gerente> lista = repo.findAll();

		return ResponseEntity.status(HttpStatus.OK)
				.body(lista.stream().map(e -> mapper.map(e, GerenteDTO.class)).collect(Collectors.toList()));

	}

	@GetMapping("/gerentes/{id}")
	public ResponseEntity<GerenteDTO> buscaPorId(@PathVariable Long id) {

		Optional<Gerente> gerente = repo.findById(id);
		if (gerente.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(gerente, GerenteDTO.class));
		}
	}

	@PostMapping("/gerentes")
	public ResponseEntity<GerenteDTO> inseriGerente(@RequestBody GerenteDTO gerente) {
		repo.save(mapper.map(gerente, Gerente.class));
		Optional<Gerente> ger = repo.findByCpf(gerente.getCpf());
		return ResponseEntity.status(HttpStatus.OK).body(mapper.map(ger, GerenteDTO.class));
	}

	@PutMapping("/gerentes/{id}")
	public ResponseEntity<GerenteDTO> alterarGerente(@PathVariable("id") long id, @RequestBody Gerente gerente) {
		Optional<Gerente> ger = repo.findById(id);

		if (ger.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			gerente.setId(id);
			repo.save(mapper.map(gerente, Gerente.class));
			ger = repo.findById(id);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(ger, GerenteDTO.class));
		}

	}

	@DeleteMapping("/gerentes/{id}")
	public ResponseEntity removerGerente(@PathVariable("id") long id) {

		Optional<Gerente> gerente = repo.findById(id);
		if (gerente.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			repo.delete(mapper.map(gerente, Gerente.class));
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}
}
