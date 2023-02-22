package br.ufpr.consulta.movimentacao.consumer;


import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.commons.Constants;
import br.ufpr.commons.movimentacao.model.Movimentacao;
import br.ufpr.commons.movimentacao.model.MovimentacaoD;
import br.ufpr.commons.movimentacao.model.MovimentacaoDTO;
import br.ufpr.consulta.movimentacao.repository.MovimentacaoConsultaRepository;

@Component
public class MovimentacaoConsultaConsumer {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MovimentacaoConsultaRepository repo;

	@Autowired
	private ModelMapper mapper;
	
	@RabbitListener(queues=Constants.FILA_INSERIR_MOVIMENTACAO_C)
	public void inserirMessage(String jsonMovimentacaoDTO) throws JsonMappingException, JsonProcessingException {
		var movimentacaoDTO = objectMapper.readValue(jsonMovimentacaoDTO, MovimentacaoDTO.class);
		try {
			Optional<MovimentacaoD> movimentacao = repo.findById(movimentacaoDTO.getId());

			if (movimentacao.isPresent()) {
				return;
			} else {
				repo.save(mapper.map(movimentacaoDTO, MovimentacaoD.class));
				}
		} catch (Exception e) {
			return;
		}
	return;
	}
	
	@RabbitListener(queues=Constants.FILA_ALTERAR_MOVIMENTACAO_C)
	public void alterarMessage(String jsonMovimentacaoDTO) throws JsonMappingException, JsonProcessingException {
		var movimentacaoDTO = objectMapper.readValue(jsonMovimentacaoDTO, MovimentacaoDTO.class);
		try {
			Optional<MovimentacaoD> mov = repo.findById(movimentacaoDTO.getId());
			
			if (mov.isEmpty()) {
				return;
			} else {
				repo.save(mapper.map(movimentacaoDTO, MovimentacaoD.class));
				}
		} catch (Exception e) {
			return;
		}
	return;
	}
	
	@RabbitListener(queues=Constants.FILA_DELETAR_MOVIMENTACAO_C)
	public void deletarMessage(String jsonMovimentacaoDTO) throws JsonMappingException, JsonProcessingException {
		var movimentacaoDTO = objectMapper.readValue(jsonMovimentacaoDTO, MovimentacaoDTO.class);
		try {
			Optional<MovimentacaoD> mov = repo.findById(movimentacaoDTO.getId());
			
			if (mov.isEmpty()) {
				return;
			} else {
				repo.delete(mapper.map(movimentacaoDTO, MovimentacaoD.class));
				}
		} catch (Exception e) {
			return;
		}
	return;
	}
}