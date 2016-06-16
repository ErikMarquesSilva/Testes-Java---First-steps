package br.com.alura.testes_de_sistema.util.PageObjects.usuarios;

import static br.com.alura.testes_de_sistema.util.UrlsDaAplicacao.UsuariosRetrieve;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UsuariosRetrievePage {

	private final WebDriver driver;

	public UsuariosRetrievePage(WebDriver driver) {
		this.driver = driver;
	}

	public UsuariosRetrievePage acessar() {
		driver.get(UsuariosRetrieve.getUrl());
		return this;
	}

	public UsuariosCreatePage clicarLinkNovoUsuario() {
		WebElement link = driver.findElement(By.linkText("Novo Usuário"));
		link.click();
		return new UsuariosCreatePage(driver);
	}

	public void apagarUltimoInserido() {
		int posicao = 1;
		driver.findElements(By.tagName("button")).get(posicao - 1).click();

		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public UsuariosCreatePage clicarEmEditar() {
		int posicao = 1;
		driver.findElements(By.linkText("editar")).get(posicao - 1).click();
		return new UsuariosCreatePage(driver);
	}
}
