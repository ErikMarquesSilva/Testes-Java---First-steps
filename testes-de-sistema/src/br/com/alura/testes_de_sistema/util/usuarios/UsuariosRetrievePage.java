package br.com.alura.testes_de_sistema.util.usuarios;

import static br.com.alura.testes_de_sistema.util.usuarios.UsuariosPagesUrls.UsuariosRetrieve;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class UsuariosRetrievePage {

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
}
