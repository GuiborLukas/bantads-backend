package br.ufpr.comando.movimentacao.consumer;


import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.comando.movimentacao.repository.MovimentacaoComandoRepository;
import br.ufpr.commons.Constants;
import br.ufpr.commons.movimentacao.model.Movimentacao;
import br.ufpr.commons.movimentacao.model.MovimentacaoDTO;
import br.ufpr.commons.movimentacao.rest.MovimentacaoREST;

@Component
public class MovimentacaoComandoConsumer {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MovimentacaoComandoRepository repo;

	@Autowired
	private ModelMapper mapper;
	
	@RabbitListener(queues=Constants.FILA_INSERIR_MOVIMENTACAO)
	public void inserirMessage(String jsonMovimentacaoDTO) throws JsonMappingException, JsonProcessingException {
		var movimentacaoDTO = objectMapper.readValue(jsonMovimentacaoDTO, MovimentacaoDTO.class);
		try {
			Optional<Movimentacao> movimentacao = repo.findById(movimentacaoDTO.getId());

			if (movimentacao.isPresent()) {
				return;
			} else {
				repo.save(mapper.map(movimentacaoDTO, Movimentacao.class));
				var json = objectMapper.writeValueAsString(movimentacaoDTO);
				rabbitTemplate.convertAndSend(Constants.FILA_INSERIR_MOVIMENTACAO_C, json);
				}
		} catch (Exception e) {
			return;
		}
	return;
	}
	
	@RabbitListener(queues=Constants.FILA_ALTERAR_MOVIMENTACAO)
	public void alterarMessage(String jsonMovimentacaoDTO) throws JsonMappingException, JsonProcessingException {
		var movimentacaoDTO = objectMapper.readValue(jsonMovimentacaoDTO, MovimentacaoDTO.class);
		try {
			Optional<Movimentacao> mov = repo.findById(movimentacaoDTO.getId());
			
			if (mov.isEmpty()) {
				return;
			} else {
				repo.save(mapper.map(movimentacaoDTO, Movimentacao.class));
				var json = objectMapper.writeValueAsString(movimentacaoDTO);
				rabbitTemplate.convertAndSend(Constants.FILA_ALTERAR_MOVIMENTACAO_C, json);
				}
		} catch (Exception e) {
			return;
		}
	return;
	}
	
	@RabbitListener(queues=Constants.FILA_DELETAR_MOVIMENTACAO)
	public void deletarMessage(String jsonMovimentacaoDTO) throws JsonMappingException, JsonProcessingException {
		var movimentacaoDTO = objectMapper.readValue(jsonMovimentacaoDTO, MovimentacaoDTO.class);
		try {
			Optional<Movimentacao> mov = repo.findById(movimentacaoDTO.getId());
			
			if (mov.isEmpty()) {
				return;
			} else {
				repo.delete(mapper.map(movimentacaoDTO, Movimentacao.class));
				var json = objectMapper.writeValueAsString(movimentacaoDTO);
				rabbitTemplate.convertAndSend(Constants.FILA_DELETAR_MOVIMENTACAO_C, json);
				}
		} catch (Exception e) {
			return;
		}
	return;
	}
	
}