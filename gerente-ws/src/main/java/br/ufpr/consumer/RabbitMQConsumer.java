package br.ufpr.consumer;


import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
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
	private ObjectMapper objectMapper;
	
	@Autowired
	private GerenteRepository repo;

	@Autowired
	private ModelMapper mapper;
	
	@RabbitHandler
	@RabbitListener(queues=Constants.FILA_INSERIR)
	public void inserirMessage(String jsonGerenteDTO) throws JsonMappingException, JsonProcessingException {
		var gerenteDTO = objectMapper.readValue(jsonGerenteDTO, GerenteDTO.class);
		System.out.println("RECEBIDA (" + gerenteDTO.getNome() + ") " + jsonGerenteDTO);
		try {
			Optional<Gerente> gerente = repo.findByCpf(gerenteDTO.getCpf());

			if (gerente.isPresent()) {
				return;
			} else {
				repo.save(mapper.map(gerenteDTO, Gerente.class));
				Optional<Gerente> ger = repo.findByCpf(gerenteDTO.getCpf());
				if (ger.isPresent()) {
					gerenteDTO = mapper.map(ger.get(), GerenteDTO.class);
				}
			}
		} catch (Exception e) {

			return;
		}
	return;
	}
}