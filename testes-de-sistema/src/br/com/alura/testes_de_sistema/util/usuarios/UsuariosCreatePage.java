package br.com.alura.testes_de_sistema.util.usuarios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class UsuariosCreatePage {

	private final WebDriver driver;
	private WebElement campoNome;
	private WebElement campoEmail;
	private WebElement botaoSalvar;

	public UsuariosCreatePage(WebDriver driver) {
		this.driver = driver;
		inicializarComponentes();
	}

	public UsuariosCreatePage digitarNome(String nome) {
		campoNome.sendKeys(nome);
		return this;
	}

	public UsuariosCreatePage digitarEmail(String email) {
		campoEmail.sendKeys(email);
		return this;
	}

	public UsuariosRetrievePage salvar() {
		botaoSalvar.click();
		return new UsuariosRetrievePage(driver);
	}

	private void inicializarComponentes() {
		campoNome = driver.findElement(By.name("usuario.nome"));
		campoEmail = driver.findElement(By.name("usuario.email"));
		botaoSalvar = driver.findElement(By.id("btnSalvar"));
	}
}
