package br.com.alura.testes_de_sistema.util.PageObjects.leiloes;

import static br.com.alura.testes_de_sistema.util.UrlsDaAplicacao.LeiloesRetrieve;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LeiloesRetrievePage {

	private final WebDriver driver;

	public LeiloesRetrievePage(WebDriver driver) {
		this.driver = driver;
	}

	public LeiloesRetrievePage acessar() {
		driver.get(LeiloesRetrieve.getUrl());
		return this;
	}

	public LeiloesCreatePage clicarLinkNovoLeilao() {
		driver.findElement(By.linkText("Novo Leilão")).click();
		return new LeiloesCreatePage(driver);
	}

	public LeilaoDetailPage clicarNoLinkExibir() {
		driver.findElement(By.linkText("exibir")).click();
		return new LeilaoDetailPage(driver);
	}

}
