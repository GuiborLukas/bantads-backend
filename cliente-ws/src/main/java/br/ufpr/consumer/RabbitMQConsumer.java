package br.ufpr.consumer;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.model.Cliente;
import br.ufpr.model.ClienteDTO;
import br.ufpr.repository.ClienteRepository;

@Component
public class RabbitMQConsumer {
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ClienteRepository repo;

	@Autowired
	private MessageConverter messageConverter;
	
	@Autowired
	private ModelMapper mapper;
	
	@RabbitHandler
	@RabbitListener(queues="inserir-cliente")
	public void inserirMessage(String jsonClienteDTO) throws JsonMappingException, JsonProcessingException {
		var clienteDTO = objectMapper.readValue(jsonClienteDTO, ClienteDTO.class);
		System.out.println("RECEBIDA (" + clienteDTO.getNome() + ") " + jsonClienteDTO);
		try {
			Optional<Cliente> cliente = repo.findByCpf(clienteDTO.getCpf());

			if (cliente.isPresent()) {
				return;
			} else {
				repo.save(mapper.map(clienteDTO, Cliente.class));
				Optional<Cliente> ger = repo.findByCpf(clienteDTO.getCpf());
				if (ger.isPresent()) {
					clienteDTO = mapper.map(ger.get(), ClienteDTO.class);
				}
			}
		} catch (Exception e) {
			return;
		}
	return;
	}
}
