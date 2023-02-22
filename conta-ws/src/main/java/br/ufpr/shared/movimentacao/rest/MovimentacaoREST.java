package br.ufpr.shared.movimentacao.rest;

import java.util.ArrayList;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.comando.movimentacao.repository.MovimentacaoComandoRepository;
import br.ufpr.consulta.movimentacao.repository.MovimentacaoConsultaRepository;
import br.ufpr.shared.Constants;
import br.ufpr.shared.movimentacao.model.Movimentacao;
import br.ufpr.shared.movimentacao.model.MovimentacaoD;
import br.ufpr.shared.movimentacao.model.MovimentacaoDTO;

@CrossOrigin
@RestController
@RequestMapping(value = "movimentacoes")
public class MovimentacaoREST {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	MovimentacaoComandoRepository repoComando;

	@Autowired
	MovimentacaoConsultaRepository repoConsulta;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping
	public ResponseEntity<List<MovimentacaoDTO>> obterTodasMovimentacoes() {
		List<MovimentacaoD> lista = repoConsulta.findAll();

		if (lista.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(lista.stream().map(e -> mapper.map(e, MovimentacaoDTO.class)).collect(Collectors.toList()));
	}

	@GetMapping("/cliente/{idCliente}")
	public ResponseEntity<List<MovimentacaoDTO>> obterTodasMovimentacoesDoCliente(@PathVariable("idCliente") Long idCliente) {
		
		List<MovimentacaoD> lista = repoConsulta.findAll();

		if (lista.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		
		List<MovimentacaoD> movCliente = new ArrayList<>();
		for(MovimentacaoD m: lista) {
			if(m.getClienteOrigem().equals(idCliente) || m.getClienteDestino().equals(idCliente)) {
				movCliente.add(m);
			}
		}
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(movCliente.stream().map(e -> mapper.map(e, MovimentacaoDTO.class)).collect(Collectors.toList()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MovimentacaoDTO> obterMovimentacaoPeloId(@PathVariable("id") Long id) {
		Optional<MovimentacaoD> movimentacao = repoConsulta.findById(id);
		if (movimentacao.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(movimentacao, MovimentacaoDTO.class));
		}
	}

	@PostMapping
	public ResponseEntity<String> inserirMovimentacao(@RequestBody Movimentacao movimentacao) {
		
		try {
				repoComando.save(mapper.map(movimentacao, Movimentacao.class));
				var json = objectMapper.writeValueAsString(mapper.map(movimentacao, MovimentacaoDTO.class));
				rabbitTemplate.convertAndSend(Constants.FILA_INSERIR_MOVIMENTACAO_C, json);
				return ResponseEntity.status(HttpStatus.OK).body("Movimentação salva com sucesso!");
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		
	}

	@PutMapping("/{id}")
	public ResponseEntity<MovimentacaoDTO> alterarMovimentacao(@PathVariable("id") Long id, @RequestBody Movimentacao movimentacao) throws JsonProcessingException {
		Optional<Movimentacao> mov = repoComando.findById(id);
		
		if (mov.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			movimentacao.setId(id);
			repoComando.save(mapper.map(movimentacao, Movimentacao.class));
			mov = repoComando.findById(id);
			var json = objectMapper.writeValueAsString(mapper.map(mov.get(), MovimentacaoDTO.class));
			rabbitTemplate.convertAndSend(Constants.FILA_ALTERAR_MOVIMENTACAO_C, json);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(mov, MovimentacaoDTO.class));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity removerMovimentacao(@PathVariable("id") Long id) throws JsonProcessingException {
		Optional<Movimentacao> movimentacao = repoComando.findById(id);
		if (movimentacao.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			repoComando.delete(mapper.map(movimentacao, Movimentacao.class));
			var json = objectMapper.writeValueAsString(mapper.map(movimentacao, MovimentacaoDTO.class));
			rabbitTemplate.convertAndSend(Constants.FILA_DELETAR_MOVIMENTACAO_C, json);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}

	}

}