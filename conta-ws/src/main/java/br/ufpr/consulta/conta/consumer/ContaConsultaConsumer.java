package br.ufpr.consulta.conta.consumer;


import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.commons.Constants;
import br.ufpr.commons.conta.model.Conta;
import br.ufpr.commons.conta.model.ContaDTO;
import br.ufpr.consulta.conta.repository.ContaConsultaRepository;

@Component
public class ContaConsultaConsumer {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ContaConsultaRepository repo;
	
	@Autowired
	private ModelMapper mapper;
	
	@RabbitHandler
	@RabbitListener(queues=Constants.FILA_INSERIR_CONTA_C)
	public void inserirMessage(String jsonContaDTO) throws JsonMappingException, JsonProcessingException {
		var contaDTO = objectMapper.readValue(jsonContaDTO, ContaDTO.class);
		System.out.println("RECEBIDA (" + contaDTO.getId() + ") " + jsonContaDTO);
		try {
			Optional<Conta> conta = repo.findByCliente(contaDTO.getCliente());

			if (conta.isPresent()) {
				return;
			} else {
				repo.save(mapper.map(contaDTO, Conta.class));
				Optional<Conta> ger = repo.findByCliente(contaDTO.getCliente());
				if (ger.isPresent()) {
					contaDTO = mapper.map(ger.get(), ContaDTO.class);
				}
			}
		} catch (Exception e) {

			return;
		}
	return;
	}
}