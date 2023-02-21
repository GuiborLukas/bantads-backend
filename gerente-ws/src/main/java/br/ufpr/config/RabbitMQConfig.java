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
		Queue filaConsultar = this.fila(Constants.FILA_CONSULTAR);
		Queue filaInserir = this.fila(Constants.FILA_INSERIR);
		Queue filaAlterar = this.fila(Constants.FILA_ALTERAR);
		Queue filaDeletar = this.fila(Constants.FILA_DELETAR);

		DirectExchange exchange = this.directExchange();

		this.amqpAdmin.declareExchange(exchange);

		this.amqpAdmin.declareQueue(filaConsultar);
		this.amqpAdmin.declareQueue(filaInserir);
		this.amqpAdmin.declareQueue(filaAlterar);
		this.amqpAdmin.declareQueue(filaDeletar);

	}
}
