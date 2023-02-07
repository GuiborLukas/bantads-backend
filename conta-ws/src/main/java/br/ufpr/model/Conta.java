package br.ufpr.model;

import java.io.Serializable;

public class Conta implements Serializable {

	public Conta() {
		super();
	}
	
	public Conta(int id, String cliente, String dataCriacao, String limite, String gerente) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.dataCriacao = dataCriacao;
		this.limite = limite;
		this.gerente = gerente;
	}

	private int id;
	private String cliente;
	private String dataCriacao; // dataCriacao String ?
	private String limite;
	private String gerente;

	public int getId() {
		return id;
	}
 
	public void setId(int id) {
		this.id = id;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getLimite() {
		return limite;
	}

	public void setLimite(String limite) {
		this.limite = limite;
	}

	public String getGerente() {
		return gerente;
	}

	public void setGerente(String gerente) {
		this.gerente = gerente;
	}

}
