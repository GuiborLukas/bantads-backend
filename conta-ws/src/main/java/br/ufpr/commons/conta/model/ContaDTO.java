package br.ufpr.commons.conta.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ContaDTO implements Serializable, Comparable<ContaDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long cliente;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dataCriacao;
	private Double saldo;
	private Double limite;
	private Long gerente;
	
	public ContaDTO() {
		super();
	}

	public ContaDTO(Long id, Long cliente, Date dataCriacao, Double saldo, Double limite, Long gerente) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.dataCriacao = dataCriacao;
		this.saldo = saldo;
		this.limite = limite;
		this.gerente = gerente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	public Double getLimite() {
		return limite;
	}

	public void setLimite(Double limite) {
		this.limite = limite;
	}

	public Long getGerente() {
		return gerente;
	}

	public void setGerente(Long gerente) {
		this.gerente = gerente;
	}

    @Override
    public String toString() {
        return "Conta[" +
                "id=" + id +
                ", cliente='" + cliente + '\'' +
                ", dataCriacao='" + dataCriacao + '\'' +
                ", limite='" + limite + '\'' +
                ", gerente='" + gerente + '\'' +
                ']';
    }
	
    @Override
    public int compareTo(ContaDTO outraContaDTO) {
        if (this.saldo < outraContaDTO.saldo) {
            return -1;
        }
        if (this.saldo > outraContaDTO.saldo) {
            return 1;
        }
        return 0;
    }
    
}
