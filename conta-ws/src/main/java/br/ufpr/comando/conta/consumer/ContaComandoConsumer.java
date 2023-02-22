package br.ufpr.comando.conta.consumer;


import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.comando.conta.repository.ContaComandoRepository;
import br.ufpr.shared.Constants;
import br.ufpr.shared.conta.model.Conta;
import br.ufpr.shared.conta.model.ContaDTO;

@Component
public class ContaComandoConsumer {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ContaComandoRepository repo;
	
	@Autowired
	private ModelMapper mapper;
	
	@RabbitHandler
	@RabbitListener(queues=Constants.FILA_INSERIR_CONTA)
	public void inserirMessage(String jsonContaDTO) throws JsonMappingException, JsonProcessingException {
		var contaDTO = objectMapper.readValue(jsonContaDTO, ContaDTO.class);
		try {
			Optional<Conta> conta = repo.findByCliente(contaDTO.getCliente());

			if (conta.isPresent()) {
				return;
			} else {
				repo.save(mapper.map(contaDTO, Conta.class));
				Optional<Conta> cnt = repo.findByCliente(contaDTO.getCliente());
				if (cnt.isPresent()) {
					contaDTO = mapper.map(cnt.get(), ContaDTO.class);
				}
				var json = objectMapper.writeValueAsString(contaDTO);
				rabbitTemplate.convertAndSend(Constants.FILA_INSERIR_CONTA_C, json);
			}
		} catch (Exception e) {

			return;
		}
	return;
	}
	
	@RabbitHandler
	@RabbitListener(queues=Constants.FILA_ALTERAR_CONTA)
	public void alterarMessage(String jsonContaDTO) throws JsonMappingException, JsonProcessingException {
		var contaDTO = objectMapper.readValue(jsonContaDTO, ContaDTO.class);
		try {
			Optional<Conta> conta = repo.findById(contaDTO.getId());

			if (conta.isEmpty()) {
				return;
			} else {
				repo.save(mapper.map(contaDTO, Conta.class));
				var json = objectMapper.writeValueAsString(contaDTO);
				rabbitTemplate.convertAndSend(Constants.FILA_ALTERAR_CONTA_C, json);
				}
		} catch (Exception e) {
			return;
		}
	return;
	}
	
	@RabbitHandler
	@RabbitListener(queues=Constants.FILA_DELETAR_CONTA)
	public void deletarMessage(String jsonContaDTO) throws JsonMappingException, JsonProcessingException {
		var contaDTO = objectMapper.readValue(jsonContaDTO, ContaDTO.class);
		try {
			Optional<Conta> cnt = repo.findById(contaDTO.getId());
			
			if (cnt.isEmpty()) {
				return;
			} else {
				repo.delete(mapper.map(contaDTO, Conta.class));
				var json = objectMapper.writeValueAsString(contaDTO);
				rabbitTemplate.convertAndSend(Constants.FILA_DELETAR_CONTA_C, json);
				}
		} catch (Exception e) {
			return;
		}
	return;
	}
}