package br.ufpr.shared.conta.rest;

import java.util.ArrayList;
import java.util.Collections;
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

import br.ufpr.comando.conta.repository.ContaComandoRepository;
import br.ufpr.consulta.conta.repository.ContaConsultaRepository;
import br.ufpr.shared.Constants;
import br.ufpr.shared.conta.model.Conta;
import br.ufpr.shared.conta.model.ContaD;
import br.ufpr.shared.conta.model.ContaDTO;
import br.ufpr.shared.conta.utils.CounterGerente;

@CrossOrigin
@RestController
@RequestMapping(value = "contas")
public class ContaREST {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ContaComandoRepository repoComando;
	
	@Autowired
	private ContaConsultaRepository repoConsulta;

	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<List<ContaDTO>> obterTodasContas() {
		List<ContaD> lista = repoConsulta.findAll();

		if (lista.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(lista.stream().map(e -> mapper.map(e, ContaDTO.class)).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContaDTO> obterContaPeloId(@PathVariable("id") Long id) {
		Optional<ContaD> conta = repoConsulta.findById(id);
		if (conta.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(conta, ContaDTO.class));
		}
	}

	@GetMapping("/cliente/{idCliente}")
	public ResponseEntity<ContaDTO> obterContaPeloIdCliente(@PathVariable("idCliente") Long idCliente) {
		Optional<ContaD> conta = repoConsulta.findByCliente(idCliente);
		if (conta.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(conta, ContaDTO.class));
		}
	}
	
	@GetMapping("/melhores/contas")
	public ResponseEntity<List<Long>> obterMelhoresContas() {
		List<ContaD> contas = repoConsulta.findAll();
		if (contas.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			Collections.sort(contas);
			Collections.reverse(contas);
			List<Long> idsMelhores = new ArrayList<>();
			int count = 0;
			for(ContaD c: contas) {
				idsMelhores.add(c.getCliente());
				count++;
				if(count == 5) 
					break;
			}
			return ResponseEntity.status(HttpStatus.OK).body(idsMelhores.stream().map(e -> mapper.map(e, Long.class)).collect(Collectors.toList()));
		}
	}
	
	@GetMapping("/melhores/cntente")
	public ResponseEntity<Long> obterMelhorGerente() {
		List<ContaD> contas = repoConsulta.findAll();
		List<CounterGerente> lista = new ArrayList<>();
		for (ContaD p : contas){
				boolean found = false;
				if(lista.isEmpty()) {
					lista.add(new CounterGerente(p.getGerente(), 1));
				}else{
					for(CounterGerente counter: lista) {
						if(counter.getGerente() == p.getGerente()) {
							int qtd = counter.getCount();
							counter.setCount(qtd + 1);
							found = true;
							break;
						}
					}
					if(!found) {
						lista.add(new CounterGerente(p.getGerente(), 1));
					}
				
			}
		}
		Collections.sort(lista);
		Collections.reverse(lista);
		Long idMelhorGerente = lista.get(0).getGerente();
		return ResponseEntity.status(HttpStatus.OK)
				.body(mapper.map(idMelhorGerente, Long.class));
	}
	
	@GetMapping("/piores/cntente")
	public ResponseEntity<Long> obterPiorGerente() {
		List<ContaD> contas = repoConsulta.findAll();
		List<CounterGerente> lista = new ArrayList<>();
		for (ContaD p : contas){
				boolean found = false;
				if(lista.isEmpty()) {
					lista.add(new CounterGerente(p.getGerente(), 1));
				}else{
					for(CounterGerente counter: lista) {
						if(counter.getGerente() == p.getGerente()) {
							int qtd = counter.getCount();
							counter.setCount(qtd + 1);
							found = true;
							break;
						}
					}
					if(!found) {
						lista.add(new CounterGerente(p.getGerente(), 1));
					}
				
			}
		}
		Collections.sort(lista);
		Long idMelhorGerente = lista.get(0).getGerente();
		return ResponseEntity.status(HttpStatus.OK)
				.body(mapper.map(idMelhorGerente, Long.class));
	}
	

	@PostMapping
	public ResponseEntity<ContaDTO> inserirConta(@RequestBody Conta conta) {
		Optional<Conta> contaBD = repoComando.findByCliente(conta.getCliente());
		if (contaBD.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		} else {
			try {
				repoComando.save(mapper.map(conta, Conta.class));
				Optional<Conta> cnt = repoComando.findByCliente(conta.getCliente());
				var json = objectMapper.writeValueAsString(mapper.map(cnt.get(), ContaDTO.class));
				rabbitTemplate.convertAndSend(Constants.FILA_INSERIR_CONTA_C, json);
				return ResponseEntity.status(HttpStatus.OK).body(mapper.map(cnt.get(), ContaDTO.class));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ContaDTO> alterarConta(@PathVariable("id") Long id, @RequestBody Conta conta) throws JsonProcessingException {
		Optional<Conta> cnt = repoComando.findById(id);
		
		if (cnt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			conta.setId(id);
			repoComando.save(mapper.map(conta, Conta.class));
			cnt = repoComando.findById(id);
			var json = objectMapper.writeValueAsString(mapper.map(cnt, ContaDTO.class));
			rabbitTemplate.convertAndSend(Constants.FILA_ALTERAR_CONTA_C, json);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(cnt, ContaDTO.class));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity removerConta(@PathVariable("id") Long id) throws JsonProcessingException {
		Optional<Conta> conta = repoComando.findById(id);
		if (conta.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			repoComando.delete(mapper.map(conta, Conta.class));
			var json = objectMapper.writeValueAsString(mapper.map(conta, ContaDTO.class));
			rabbitTemplate.convertAndSend(Constants.FILA_DELETAR_CONTA_C, json);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}

	}
}
