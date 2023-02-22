package br.ufpr.model;

import java.io.Serializable;

public class GerenteDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String email;
	private String cpf;
	private String telefone;
	
	private boolean success;
	private String message;
	
	public GerenteDTO() {
		super();
	}

	public GerenteDTO(Long id, String nome, String email, String Cpf, String telefone) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpf = Cpf;
		this.telefone = telefone;
	}
		
    public GerenteDTO(Long id, String nome, String email, String cpf, String telefone, boolean success,
			String message) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.telefone = telefone;
		this.success = success;
		this.message = message;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    @Override
    public String toString() {
        return "GerenteDTO [" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", success=" + success + ", message='"
				+ message + '\'' + ']';
    }
    
    
}
