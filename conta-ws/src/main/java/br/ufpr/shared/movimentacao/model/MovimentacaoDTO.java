package br.ufpr.shared.movimentacao.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MovimentacaoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date data;
	private String tipo;
	private Long clienteOrigem;
	private Long clienteDestino;
	private double valor;

	public MovimentacaoDTO() {
		super();
	}

	public MovimentacaoDTO(Long id, Date data, String tipo, Long clienteOrigem, Long clienteDestino, double valor) {
		super();
		this.id = id;
		this.data = data;
		this.tipo = tipo;
		this.clienteOrigem = clienteOrigem;
		this.clienteDestino = clienteDestino;
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getClienteOrigem() {
		return clienteOrigem;
	}

	public void setClienteOrigem(Long clienteOrigem) {
		this.clienteOrigem = clienteOrigem;
	}

	public Long getClienteDestino() {
		return clienteDestino;
	}

	public void setClienteDestino(Long clienteDestino) {
		this.clienteDestino = clienteDestino;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Movimentacao [" + "id=" + id + "data=" + data + ", tipo='" + tipo + '\'' + ", clienteOrigem=" + clienteOrigem
				+ ", clienteDestino=" + clienteDestino + ", valor=" + valor + ']';

	}
}
