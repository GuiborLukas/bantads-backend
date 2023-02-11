package br.ufpr.model;

import java.io.Serializable;

public class Usuario implements Serializable {

	private String nome;
	private String login;
	private String senha;
	private String perfil;

	public Usuario() {
		super();
	}
	
	public Usuario(String nome, String login, String senha, String perfil) {
		super();
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.perfil = perfil;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

}
