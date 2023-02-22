package br.ufpr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_cliente")

public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_cliente")
	private Long id;

	@Column(name = "nome_cliente")
	private String nome;

	@Column(name = "email_cliente")
	private String email;

	@Column(name = "cpf_cliente")
	private String cpf;

	@Column(name = "telefone_cliente")
	private String telefone;

	@Column(name = "salario_cliente")
	private double salario;

	@Column(name = "endereco_cliente")
	private Endereco endereco;

	public Cliente() {
		super();
	}

	public Cliente(Long id, String nome, String email, String cpf, String telefone, double salario, Endereco endereco) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.telefone = telefone;
		this.salario = salario;
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long i) {
		this.id = i;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public static Long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
	    return "Cliente[" +
	            "id=" + id +
	            ", nome='" + nome + '\'' +
	            ", email='" + email + '\'' +
	            ", cpf='" + cpf + '\'' +
	            ", telefone='" + telefone + '\'' +
	            ", salario=" + salario +
	            ", endereco=" + endereco +
	            ']';
	}

}
