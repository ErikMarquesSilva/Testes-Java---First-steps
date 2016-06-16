package br.com.alura.testes_de_sistema.util.PageObjects.usuarios;

import static br.com.alura.testes_de_sistema.util.UrlsDaAplicacao.UsuariosCreate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.alura.testes_de_sistema.model.Usuario;;

public class UsuariosCreatePage {

	private static final String CAMPO_EMAIL = "usuario.email";
	private static final String CAMPO_NOME = "usuario.nome";
	private final WebDriver driver;

	public UsuariosCreatePage(WebDriver driver) {
		this.driver = driver;
	}

	public UsuariosCreatePage acessar() {
		driver.get(UsuariosCreate.getUrl());
		return this;
	}

	public UsuariosRetrievePage cadastrar(Usuario usuario) {
		elemento(CAMPO_NOME).sendKeys(usuario.getNome());
		elemento(CAMPO_EMAIL).sendKeys(usuario.getEmail());
		driver.findElement(By.id("btnSalvar")).click();
		return new UsuariosRetrievePage(driver);
	}

	private WebElement elemento(String nome) {
		return driver.findElement(By.name(nome));
	}

	public void alterar(Usuario usuario) {
		elemento(CAMPO_NOME).clear();
		elemento(CAMPO_EMAIL).clear();
		cadastrar(usuario);
	}
}
