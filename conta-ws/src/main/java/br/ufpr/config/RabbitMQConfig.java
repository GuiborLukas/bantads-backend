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

		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_CONSULTAR_CONTA));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_INSERIR_CONTA));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_ALTERAR_CONTA));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_DELETAR_CONTA));
		
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_CONSULTAR_MOVIMENTACAO));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_INSERIR_MOVIMENTACAO));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_ALTERAR_MOVIMENTACAO));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_DELETAR_MOVIMENTACAO));
		
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_CONSULTAR_CONTA_C));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_INSERIR_CONTA_C));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_ALTERAR_CONTA_C));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_DELETAR_CONTA_C));
		
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_CONSULTAR_MOVIMENTACAO_C));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_INSERIR_MOVIMENTACAO_C));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_ALTERAR_MOVIMENTACAO_C));
		this.amqpAdmin.declareQueue(this.fila(Constants.FILA_DELETAR_MOVIMENTACAO_C));

	}
}
