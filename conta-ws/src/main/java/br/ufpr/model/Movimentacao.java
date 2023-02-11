package br.ufpr.model;

import java.io.Serializable;
import java.util.Date;

public class Movimentacao implements Serializable{

	private Date data;
	private String tipo;
	private int clienteOrigem;
	private int clienteDestino;
	private double valor;
	
}
