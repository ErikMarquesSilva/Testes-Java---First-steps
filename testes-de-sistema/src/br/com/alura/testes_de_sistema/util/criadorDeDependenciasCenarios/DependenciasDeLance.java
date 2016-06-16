package br.com.alura.testes_de_sistema.util.criadorDeDependenciasCenarios;

import org.openqa.selenium.WebDriver;

import br.com.alura.testes_de_sistema.model.Leilao;
import br.com.alura.testes_de_sistema.model.Usuario;
import br.com.alura.testes_de_sistema.util.PageObjects.leiloes.LeiloesCreatePage;
import br.com.alura.testes_de_sistema.util.PageObjects.usuarios.UsuariosCreatePage;

public class DependenciasDeLance {
	private WebDriver driver;

	public DependenciasDeLance(WebDriver driver) {
		this.driver = driver;
	}

	public DependenciasDeLance com(Usuario usuario) {
		UsuariosCreatePage paginaDeCriacaoDeUsuarios = new UsuariosCreatePage(driver);
		paginaDeCriacaoDeUsuarios.acessar().cadastrar(usuario);
		return this;
	}

	public DependenciasDeLance com(Leilao leilao) {
		LeiloesCreatePage paginaDeCriacaoDeLeiloes = new LeiloesCreatePage(driver);
		paginaDeCriacaoDeLeiloes.acessar().cadastrar(leilao);
		return this;
	}

}
