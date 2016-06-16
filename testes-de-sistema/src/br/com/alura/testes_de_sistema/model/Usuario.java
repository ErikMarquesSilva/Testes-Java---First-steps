package br.com.alura.testes_de_sistema.model;

public class Usuario {
	private String nome;
	private String email;

	public Usuario(String nome, String email) {
		this.nome = nome;
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

}
