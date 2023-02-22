package br.ufpr.shared.conta.model;

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
@Table(name = "tb_conta")
public class Conta implements Serializable, Comparable<Conta> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conta")
	private Long id;
	
	@Column(name = "id_cliente_conta")
	private Long cliente;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "data_criacao_conta")
	private Date dataCriacao;
	
	@Column(name = "saldo_conta")
	private Double saldo;
	
	@Column(name = "limite_conta")
	private Double limite;
	
	@Column(name = "gerente_conta")
	private Long gerente;
	
	public Conta() {
		super();
	}

	public Conta(Long id, Long cliente, Date dataCriacao, Double saldo, Double limite, Long gerente) {
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
    public int compareTo(Conta outraConta) {
        if (this.saldo < outraConta.saldo) {
            return -1;
        }
        if (this.saldo > outraConta.saldo) {
            return 1;
        }
        return 0;
    }
	
}
