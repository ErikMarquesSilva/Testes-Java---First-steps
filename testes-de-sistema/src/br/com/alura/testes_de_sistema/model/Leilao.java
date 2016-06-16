package br.com.alura.testes_de_sistema.model;

public class Leilao {

	private String produto;
	private double valorInicial;
	private String usuario;
	private boolean usado;

	public Leilao(String produto, double valorInicial, String usuario, boolean usado) {
		this.produto = produto;
		this.valorInicial = valorInicial;
		this.usuario = usuario;
		this.usado = usado;
	}

	public String getProduto() {
		return produto;
	}

	public double getValorInicial() {
		return valorInicial;
	}

	public String getUsuario() {
		return usuario;
	}

	public boolean isUsado() {
		return usado;
	}

}
