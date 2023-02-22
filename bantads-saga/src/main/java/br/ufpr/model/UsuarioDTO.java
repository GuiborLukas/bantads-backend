package br.ufpr.model;

import java.io.Serializable;

public class UsuarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String nome;
	private String email;
	private String perfil;
	
	private boolean success;
	private String message;

	public UsuarioDTO(String id, String nome, String email, String perfil) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.perfil = perfil;
	}
	
	public UsuarioDTO(String id, String nome, String email, String perfil, boolean success, String message) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.perfil = perfil;
		this.success = success;
		this.message = message;
	}

	public UsuarioDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", nome=" + nome + ", email=" + email + ", perfil=" + perfil + ", success="
				+ success + ", message='" + message + '\'' + ']';
	}

}
