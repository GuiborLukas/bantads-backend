package br.ufpr.rest;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.commons.Constants;
import br.ufpr.model.ClienteDTO;
import br.ufpr.model.GerenteDTO;

@CrossOrigin
@RestController
@RequestMapping(value = "saga")
public class SagaREST {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/autocadastro")
	public ResponseEntity<ClienteDTO> iniciaSagaAutocadastro(@RequestBody ClienteDTO cliente) throws JsonProcessingException {
		var json = objectMapper.writeValueAsString(cliente);
		try {
			var registro = rabbitTemplate.convertSendAndReceive(Constants.FILA_SAGA, json);
			String jsonReg = objectMapper.writeValueAsString(registro);
			var clienteDTO = objectMapper.readValue(jsonReg, ClienteDTO.class);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(clienteDTO, ClienteDTO.class));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
	}
	
	@PostMapping("/alteracao/cliente")
	public ResponseEntity<ClienteDTO> iniciaSagaAlteraPerfil(@RequestBody ClienteDTO cliente) throws JsonProcessingException {
		var json = objectMapper.writeValueAsString(cliente);
		try {
			var registro = rabbitTemplate.convertSendAndReceive(Constants.FILA_SAGA, json);
			String jsonReg = objectMapper.writeValueAsString(registro);
			var clienteDTO = objectMapper.readValue(jsonReg, ClienteDTO.class);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(clienteDTO, ClienteDTO.class));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
	}
	
	@PostMapping("/gerente/remover")
	public ResponseEntity<ClienteDTO> iniciaSagaGerenteRemover(@RequestBody GerenteDTO cliente) throws JsonProcessingException {
		var json = objectMapper.writeValueAsString(cliente);
		try {
			var registro = rabbitTemplate.convertSendAndReceive(Constants.FILA_SAGA, json);
			String jsonReg = objectMapper.writeValueAsString(registro);
			var clienteDTO = objectMapper.readValue(jsonReg, ClienteDTO.class);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(clienteDTO, ClienteDTO.class));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
	}
	
	@PostMapping("/gerente/remover")
	public ResponseEntity<ClienteDTO> iniciaSagaGerenteInserir(@RequestBody GerenteDTO gerente) throws JsonProcessingException {
		var json = objectMapper.writeValueAsString(gerente);
		try {
			var registro = rabbitTemplate.convertSendAndReceive(Constants.FILA_SAGA, json);
			String jsonReg = objectMapper.writeValueAsString(registro);
			var clienteDTO = objectMapper.readValue(jsonReg, ClienteDTO.class);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.map(clienteDTO, ClienteDTO.class));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
	}

	
}
