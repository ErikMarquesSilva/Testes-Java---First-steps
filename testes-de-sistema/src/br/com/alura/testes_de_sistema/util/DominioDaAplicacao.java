package br.com.alura.testes_de_sistema.util;

public enum DominioDaAplicacao {

	Dominio("http://localhost:8080");

	private final String url;

	private DominioDaAplicacao(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
