package br.ufpr.model;

import java.io.Serializable;

public class LoginDTO implements Serializable {

	private String email;
	private String senha;

	public LoginDTO(String email, String senha) {
		super();
		this.email = email;
		this.senha = senha;
	}

	public LoginDTO() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "LoginDTO [email=" + email + ", senha=" + senha + "]";
	}

}
