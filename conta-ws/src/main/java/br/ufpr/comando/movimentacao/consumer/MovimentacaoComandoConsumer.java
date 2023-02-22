package br.ufpr.comando.movimentacao.consumer;


import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.comando.movimentacao.repository.MovimentacaoComandoRepository;
import br.ufpr.shared.Constants;
import br.ufpr.shared.movimentacao.model.Movimentacao;
import br.ufpr.shared.movimentacao.model.MovimentacaoDTO;
import br.ufpr.shared.movimentacao.rest.MovimentacaoREST;

@Component
public class MovimentacaoComandoConsumer {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MovimentacaoComandoRepository repo;

	@Autowired
	private ModelMapper mapper;
	
	@RabbitHandler
	@RabbitListener(queues=Constants.FILA_INSERIR_MOVIMENTACAO)
	public void inserirMessage(String jsonMovimentacaoDTO) throws JsonMappingException, JsonProcessingException {
		var movimentacaoDTO = objectMapper.readValue(jsonMovimentacaoDTO, MovimentacaoDTO.class);
		try {
			Optional<Movimentacao> movimentacao = repo.findById(movimentacaoDTO.getId());

			if (movimentacao.isPresent()) {
				return;
			} else {
				repo.save(mapper.map(movimentacaoDTO, Movimentacao.class));
				}
		} catch (Exception e) {
			return;
		}
	return;
	}
}