package br.com.alura.testes_de_sistema.util.PageObjects.leiloes;

import static br.com.alura.testes_de_sistema.util.UrlsDaAplicacao.LeiloesCreate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import br.com.alura.testes_de_sistema.model.Leilao;

public class LeiloesCreatePage {

	private final WebDriver driver;

	public LeiloesCreatePage(WebDriver driver) {
		this.driver = driver;
	}

	public void cadastrar(Leilao leilao) {

		if (null != leilao.getProduto()) {
			elemento("leilao.nome").sendKeys(leilao.getProduto());
		}

		if (0 < leilao.getValorInicial()) {
			elemento("leilao.valorInicial").sendKeys(String.valueOf(leilao.getValorInicial()));
		}

		new Select(elemento("leilao.usuario.id")).selectByVisibleText(leilao.getUsuario());

		if (leilao.isUsado()) {
			driver.findElement(By.name("leilao.usado")).click();
		}

		driver.findElement(By.tagName("button")).click();
	}

	private WebElement elemento(String nome) {
		return driver.findElement(By.name(nome));
	}

	public LeiloesCreatePage acessar() {
		driver.get(LeiloesCreate.getUrl());
		return this;
	}
}
