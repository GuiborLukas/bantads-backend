package br.ufpr.consumer;

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
	private ModelMapper mapper;

	@RabbitHandler
	@RabbitListener(queues=Constants.FILA_INSERIR_CLIENTE)
	public void inserirMessage(String jsonClienteDTO) throws JsonMappingException, JsonProcessingException {
	    var clienteDTO = objectMapper.readValue(jsonClienteDTO, ClienteDTO.class);
	    System.out.println("RECEBIDA (" + clienteDTO.getNome() + ") " + jsonClienteDTO);
	    try {
	        Optional<Cliente> cliente = repo.findByCpf(clienteDTO.getCpf());

	        if (cliente.isPresent()) {
	            return;
	        } else {
	            repo.save(mapper.map(clienteDTO, Cliente.class));
	            Optional<Cliente> cli = repo.findByCpf(clienteDTO.getCpf());
	            if (cli.isPresent()) {
	                clienteDTO = mapper.map(cli.get(), ClienteDTO.class);
	            }
	        }
	    } catch (Exception e) {

	        return;
	    }
	return;
	}

	@RabbitHandler
	@RabbitListener(queues = Constants.FILA_ALTERAR_CLIENTE)
	public void alterarMessage(String jsonClienteDTO) throws JsonMappingException, JsonProcessingException {
	    var clienteDTO = objectMapper.readValue(jsonClienteDTO, ClienteDTO.class);
	    try {
	        Optional<Cliente> cliente = repo.findByCpf(clienteDTO.getCpf());

	        if (cliente.isEmpty()) {
	            return;
	        } else {
	            Cliente clienteToUpdate = mapper.map(clienteDTO, Cliente.class);
	            clienteToUpdate.setId(cliente.get().getId());
	            repo.save(clienteToUpdate);
	            var json = objectMapper.writeValueAsString(clienteDTO);
	        }
	    } catch (Exception e) {
	        return;
	    }
	}

	@RabbitHandler
	@RabbitListener(queues = Constants.FILA_DELETAR_CLIENTE)
	public void deletarMessage(String jsonClienteDTO) throws JsonMappingException, JsonProcessingException {
	    var clienteDTO = objectMapper.readValue(jsonClienteDTO, ClienteDTO.class);
	    try {
	        Optional<Cliente> cliente = repo.findByCpf(clienteDTO.getCpf());

	        if (cliente.isEmpty()) {
	            return;
	        } else {
	            repo.delete(cliente.get());
	            var json = objectMapper.writeValueAsString(clienteDTO);
	        }
	    } catch (Exception e) {
	        return;
	    }
	}

}
