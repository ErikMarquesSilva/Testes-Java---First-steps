package br.com.alura.testes_de_sistema.util;

import org.openqa.selenium.WebDriver;

public class ValidadorDeConteudoDasPaginas {

	private WebDriver driver;

	public ValidadorDeConteudoDasPaginas(WebDriver driver) {
		this.driver = driver;
	}

	public boolean achou(String valor) {
		return driver.getPageSource().contains(valor);
	}

}
