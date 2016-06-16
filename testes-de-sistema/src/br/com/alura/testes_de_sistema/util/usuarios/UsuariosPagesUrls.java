package br.com.alura.testes_de_sistema.util.usuarios;

import static br.com.alura.testes_de_sistema.util.DominioDaAplicacao.Dominio;

public enum UsuariosPagesUrls {

	UsuariosRetrieve(Dominio.getUrl() + "/usuarios");

	private final String url;

	private UsuariosPagesUrls(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
