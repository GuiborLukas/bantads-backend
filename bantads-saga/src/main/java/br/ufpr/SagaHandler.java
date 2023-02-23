package br.ufpr;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.commons.Constants;
import br.ufpr.model.ContaDTO;
import br.ufpr.model.GerenteDTO;
import br.ufpr.model.ResponseSaga;

@Component
public class SagaHandler {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ModelMapper mapper;

	@RabbitHandler
	@RabbitListener(queues=Constants.FILA_SAGA)
	public ResponseSaga handler(String json) throws JsonMappingException, JsonProcessingException {
	    var responseSaga = objectMapper.readValue(json, ResponseSaga.class);
	    
	    String message = responseSaga.getMessage();	    
	    
	    switch(message) {
	    case "S1P0 - Autocadastro" : { }; break;
	    case "S1P1 - Cliente criado" : { }; break;
	    case "S1P2 - Conta criada" : { }; break;
	    case "S1P3 - Gerente atribuído" : { }; break;
	    
	    case "S2P0 - Alteração de perfil" : { }; break;
	    case "S2P1 - Gerente inserido" : { }; break;
	    case "S2P2 - Gerente inserido" : { }; break;
	    case "S2P3 - Gerente inserido" : { }; break;
	    
	    case "S3P0 - Remover gerente" : { }; break;
	    case "S3P1 - Pior gerente encontrado" : { }; break;
	    case "S3P2 - Gerente atribuído" : { }; break;
	    case "S3P3 - Gerente deletado" : { }; break;
	    
	    case "S4P0 - Inserir gerente" : { 
	    	var gerenteDTO = objectMapper.readValue(json, GerenteDTO.class);
	    	String jsonRed = objectMapper.writeValueAsString(gerenteDTO);
	    	rabbitTemplate.convertAndSend(Constants.FILA_INSERIR_GERENTE, jsonRed);
	    }; break;
	    case "S4P2 - Gerente inserido" : {
	    	var gerenteDTO = objectMapper.readValue(json, GerenteDTO.class);
	    	if(gerenteDTO.isSuccess()) {
	    		gerenteDTO.setSuccess(false);
	    		ContaDTO contaMelhorGerente = (ContaDTO) rabbitTemplate.convertSendAndReceive(Constants.FILA_MELHOR_GERENTE);
	    		contaMelhorGerente.setGerente(gerenteDTO.getId());
	    		String jsonRed = objectMapper.writeValueAsString(contaMelhorGerente);
	    		rabbitTemplate.convertAndSend(Constants.FILA_ALTERAR_CONTA, jsonRed);
	    	}
	    }; break;
	    case "S4P1 - Conta de Melhor gerente" : { }; break;
	    case "S4P3 - Gerente atribuído" : { }; break;
	    	
	    }
	    
	    return null;
	}

}
