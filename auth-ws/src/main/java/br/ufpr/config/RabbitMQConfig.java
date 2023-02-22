package br.ufpr.config;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

import br.ufpr.commons.Constants;

@Configuration
public class RabbitMQConfig {

	private AmqpAdmin amqpAdmin;

	public RabbitMQConfig(AmqpAdmin amqpAdmin) {
		this.amqpAdmin = amqpAdmin;
	}

	private Queue fila(String nomeFila) {
		return new Queue(nomeFila, true, false, false);
	}

	private DirectExchange directExchange() {
		return new DirectExchange(Constants.EXCHANGE);
	}

	@PostConstruct
	private void adiciona() {
		DirectExchange exchange = this.directExchange();

		this.amqpAdmin.declareExchange(exchange);

		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_SAGA));
		
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_CONSULTAR_USUARIO));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_INSERIR_USUARIO));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_ALTERAR_USUARIO));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_DELETAR_USUARIO));
		
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_CONSULTAR_AUTH));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_INSERIR_AUTH));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_ALTERAR_AUTH));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_DELETAR_AUTH));

	}
}

