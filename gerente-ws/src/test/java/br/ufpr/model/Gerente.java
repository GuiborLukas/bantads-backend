package br.ufpr.model;

public class Gerente {

	private int id;
	private String nome;
	private String email;
	private String Cpf;
	private String telefone;
	
	public Gerente() {
		super();
	}

	public Gerente(int id, String nome, String email, String Cpf, String telefone) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.Cpf = Cpf;
		this.telefone = telefone;
	}
	
	
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

	public String getCpf() {
		return Cpf;
	}

	public void setCpf(String cPF) {
		Cpf = cPF;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
