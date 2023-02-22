package br.ufpr.commons.movimentacao.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tb_movimentacao")
public class Movimentacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao")
	private Long id;
	
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Column(name = "data_movimentacao")
	private Date data;
	
	@Column(name = "tipo_movimentacao")
	private String tipo;
	
	@Column(name = "cliente_origem")
	private Long clienteOrigem;
	
	@Column(name = "cliente_destino")
	private Long clienteDestino;
	
	@Column(name = "valor_movimentacao")
	private double valor;

	public Movimentacao() {
		super();
	}

	public Movimentacao(Date data, String tipo, Long clienteOrigem, Long clienteDestino, double valor) {
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
