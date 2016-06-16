package br.com.alura.testes_de_sistema.util.builder;

import br.com.alura.testes_de_sistema.model.Leilao;

public class LeilaoBuilder {

	private String nomeDoProduto;
	private boolean usado;
	private double valorInicial;
	private String nomeDoUsuario;

	public LeilaoBuilder() {
		this.usado = false;
	}

	public LeilaoBuilder para(String nomeDoProduto) {
		this.nomeDoProduto = nomeDoProduto;
		return this;
	}

	public LeilaoBuilder usado() {
		usado = true;
		return this;
	}

	public LeilaoBuilder com(double valorInicial) {
		this.valorInicial = valorInicial;
		return this;
	}

	public LeilaoBuilder pertencenteA(String nomeDoUsuario) {
		this.nomeDoUsuario = nomeDoUsuario;
		return this;
	}

	public Leilao criar() {
		return new Leilao(nomeDoProduto, valorInicial, nomeDoUsuario, usado);
	}

}
