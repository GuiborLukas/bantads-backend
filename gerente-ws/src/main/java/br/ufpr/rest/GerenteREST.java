package br.ufpr.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.model.Gerente;
import br.ufpr.model.GerenteDTO;
import br.ufpr.repository.GerenteRepository;

@CrossOrigin
@RestController
@RequestMapping(value = "gerentes")
public class GerenteREST {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private GerenteRepository repo;

	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<List<GerenteDTO>> obterTodosGerentes() {

		List<Gerente> lista = repo.findAll();

		if (lista.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(lista.stream().map(e -> mapper.map(e, GerenteDTO.class)).collect(Collectors.toList()));

	}

	@GetMapping("/{id}")
	public ResponseEntity<GerenteDTO> buscaPorId(@PathVariable Long id) {

		Optional<Gerente> gerente = repo.findById(id);
		if (gerente.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(gerente, GerenteDTO.class));
		}
	}

	@PostMapping
	public ResponseEntity<GerenteDTO> inseriGerente(@RequestBody GerenteDTO gerente) {

		try {
			System.err.println(1);
			Gerente ger = repo.save(mapper.map(gerente, Gerente.class));
			System.err.println(2);
			Optional<Gerente> gerOpt = repo.findById(ger.getId());
			System.err.println(3);
			if (!gerOpt.isPresent()) {
				throw new Exception("Criação do gerente não foi realizada com sucesso");
			}
			System.err.println(4);
			return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(gerOpt.get(), GerenteDTO.class));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<GerenteDTO> alterarGerente(@PathVariable("id") long id, @RequestBody Gerente gerente) {
		Optional<Gerente> ger = repo.findById(id);

		if (ger.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			gerente.setId(id);
			repo.save(mapper.map(gerente, Gerente.class));
			ger = repo.findById(id);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(ger, GerenteDTO.class));
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity removerGerente(@PathVariable("id") long id) {

		Optional<Gerente> gerente = repo.findById(id);
		if (gerente.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			repo.delete(mapper.map(gerente, Gerente.class));
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}
}
