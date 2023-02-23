package br.ufpr.consulta.conta.consumer;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import br.ufpr.commons.conta.model.ContaD;
import br.ufpr.commons.conta.model.ContaDTO;
import br.ufpr.commons.conta.utils.CounterGerente;
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
		try {
			Optional<ContaD> conta = repo.findByCliente(contaDTO.getCliente());
			System.out.println("Inserindo na conta consulta");
			if (conta.isPresent()) {
				return;
			} else {
				repo.save(mapper.map(contaDTO, ContaD.class));
				Optional<ContaD> cnt = repo.findByCliente(contaDTO.getCliente());
				if (cnt.isPresent()) {
					contaDTO = mapper.map(cnt.get(), ContaDTO.class);
					System.out.println("Inserido na conta consulta");
				}
			}
		} catch (Exception e) {

			return;
		}
	return;
	}
	
	@RabbitHandler
	@RabbitListener(queues=Constants.FILA_MELHOR_GERENTE)
	public Long obterMelhorGerente() throws JsonMappingException, JsonProcessingException {
		Long idMelhorGerente = null;
		try {
			List<ContaD> contas = repo.findAll();
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
			idMelhorGerente = lista.get(0).getGerente();
			
		} catch (Exception e) {
			return null;
		}
		return idMelhorGerente;
	}
	
	
	@RabbitHandler
	@RabbitListener(queues=Constants.FILA_ALTERAR_CONTA_C)
	public void alterarMessage(String jsonContaDTO) throws JsonMappingException, JsonProcessingException {
		var contaDTO = objectMapper.readValue(jsonContaDTO, ContaDTO.class);
		try {
			Optional<ContaD> conta = repo.findById(contaDTO.getId());
			System.out.println("Alterando na conta consulta");
			if (conta.isEmpty()) {
				return;
			} else {
				repo.save(mapper.map(contaDTO, ContaD.class));
				System.out.println("Alterado na conta consulta");
				}
		} catch (Exception e) {
			return;
		}
	return;
	}
	
	@RabbitHandler
	@RabbitListener(queues=Constants.FILA_DELETAR_CONTA_C)
	public void deletarMessage(String jsonContaDTO) throws JsonMappingException, JsonProcessingException {
		var contaDTO = objectMapper.readValue(jsonContaDTO, ContaDTO.class);
		try {
			Optional<ContaD> cnt = repo.findById(contaDTO.getId());
			System.out.println("Deletando na conta consulta");
			if (cnt.isEmpty()) {
				return;
			} else {
				repo.delete(mapper.map(contaDTO, ContaD.class));
				System.out.println("Deletado na conta consulta");
				}
		} catch (Exception e) {
			return;
		}
	return;
	}
	
}