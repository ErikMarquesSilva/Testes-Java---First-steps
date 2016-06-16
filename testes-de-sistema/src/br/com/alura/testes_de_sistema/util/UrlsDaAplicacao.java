package br.com.alura.testes_de_sistema.util;

public enum UrlsDaAplicacao {

	Dominio("http://localhost:8080"), Cadastro("/new"), LimpaCenario(Dominio.getUrl() + "/apenas-teste/limpa"),

	LeiloesRetrieve(Dominio.getUrl() + "/leiloes"), LeiloesCreate(
		LeiloesRetrieve.getUrl() + Cadastro.getUrl()), UsuariosRetrieve(Dominio.getUrl() + "/usuarios"), UsuariosCreate(
			UsuariosRetrieve.getUrl() + Cadastro.getUrl());

	private final String url;

	private UrlsDaAplicacao(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
