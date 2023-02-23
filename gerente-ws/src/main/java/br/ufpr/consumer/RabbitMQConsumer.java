package br.ufpr.consumer;


import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
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
import br.ufpr.model.Gerente;
import br.ufpr.model.GerenteDTO;
import br.ufpr.model.ResponseSaga;
import br.ufpr.repository.GerenteRepository;

@Component
public class RabbitMQConsumer {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private GerenteRepository repo;

	@Autowired
	private ModelMapper mapper;
	
	@RabbitHandler
	@RabbitListener(queues=Constants.FILA_INSERIR_GERENTE)
	public void inserirMessage(String jsonGerenteDTO) throws JsonMappingException, JsonProcessingException {
		var gerenteDTO = objectMapper.readValue(jsonGerenteDTO, GerenteDTO.class);
		
		try {
			Optional<Gerente> gerente = repo.findByCpf(gerenteDTO.getCpf());
			
			if (gerente.isPresent()) {
				return;
			} else {
				repo.save(mapper.map(gerenteDTO, Gerente.class));
				Optional<Gerente> ger = repo.findByCpf(gerenteDTO.getCpf());
				if (ger.isPresent()) {
					if(StringUtils.isNotEmpty(gerenteDTO.getMessage())) {
						gerenteDTO.setId(ger.get().getId());
						gerenteDTO.setSuccess(true);
						gerenteDTO.setMessage("S4P2 - Gerente inserido");
						GerenteDTO gdto = mapper.map(gerenteDTO, GerenteDTO.class);  
						var json = objectMapper.writeValueAsString(gdto);
						rabbitTemplate.convertAndSend(Constants.FILA_SAGA, json);
					}
				}
			}
		} catch (Exception e) {
			if(StringUtils.isNotEmpty(gerenteDTO.getMessage())) {
				ResponseSaga responseSaga = new ResponseSaga(true, "S4P2 - Gerente inserido");
				var json = objectMapper.writeValueAsString(responseSaga);
				rabbitTemplate.convertAndSend(Constants.FILA_SAGA, json);
			}
			return;
		}
	return;
	}
	
	@RabbitHandler
	@RabbitListener(queues = Constants.FILA_ALTERAR_GERENTE)
	public void alterarMessage(String jsonGerenteDTO) throws JsonMappingException, JsonProcessingException {
	    var gerenteDTO = objectMapper.readValue(jsonGerenteDTO, GerenteDTO.class);
	    try {
	        Optional<Gerente> gerente = repo.findByCpf(gerenteDTO.getCpf());

	        if (gerente.isEmpty()) {
	            return;
	        } else {
	            Gerente gerenteToUpdate = mapper.map(gerenteDTO, Gerente.class);
	            gerenteToUpdate.setId(gerente.get().getId());
	            repo.save(gerenteToUpdate);
	            var json = objectMapper.writeValueAsString(gerenteDTO);
	        }
	    } catch (Exception e) {
	        return;
	    }
	}

	@RabbitHandler
	@RabbitListener(queues = Constants.FILA_DELETAR_GERENTE)
	public void deletarMessage(String jsonGerenteDTO) throws JsonMappingException, JsonProcessingException {
	    var gerenteDTO = objectMapper.readValue(jsonGerenteDTO, GerenteDTO.class);
	    try {
	        Optional<Gerente> gerente = repo.findByCpf(gerenteDTO.getCpf());

	        if (gerente.isEmpty()) {
	            return;
	        } else {
	            repo.delete(gerente.get());
				if(StringUtils.isNotEmpty(gerenteDTO.getMessage())) {
					ResponseSaga responseSaga = new ResponseSaga(true, "S3P3 - Gerente deletado");
					var json = objectMapper.writeValueAsString(responseSaga);
					rabbitTemplate.convertAndSend(Constants.FILA_SAGA, json);
				}
	        }
	    } catch (Exception e) {
			if(StringUtils.isNotEmpty(gerenteDTO.getMessage())) {
				ResponseSaga responseSaga = new ResponseSaga(false, "S3P3 - Gerente deletado");
				var json = objectMapper.writeValueAsString(responseSaga);
				rabbitTemplate.convertAndSend(Constants.FILA_SAGA, json);
			}
	        return;
	    }
	}
}