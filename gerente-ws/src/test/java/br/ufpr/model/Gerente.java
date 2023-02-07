package br.ufpr.model;

public class Gerente {

	public Gerente(int id, String nome, String email, String CPF, String telefone) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.CPF = CPF;
		this.telefone = telefone;
	}

	public Gerente() {
		super();
	}

	private int id;
	private String nome;
	private String email;
	private String CPF;
	private String telefone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
