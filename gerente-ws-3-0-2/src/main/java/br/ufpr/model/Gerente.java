package br.ufpr.model;

import java.io.Serializable;

import jakarta.persistence.*;
@Entity
@Table(name = "tb_gerente")
public class Gerente implements Serializable{
	
	private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gerente")
    private Long id;

    @Column(name = "nome_gerente")
    private String nome;

    @Column(name = "email_gerente")
    private String email;

    @Column(name = "cpf_gerente")
    private String cpf;

    @Column(name = "telefone_gerente")
    private String telefone;

    public Gerente() {}

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
}