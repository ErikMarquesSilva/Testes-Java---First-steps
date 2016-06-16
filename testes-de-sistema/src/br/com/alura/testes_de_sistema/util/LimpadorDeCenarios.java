package br.com.alura.testes_de_sistema.util;

import static br.com.alura.testes_de_sistema.util.UrlsDaAplicacao.LimpaCenario;

import org.openqa.selenium.WebDriver;

public class LimpadorDeCenarios {

	private WebDriver driver;

	public LimpadorDeCenarios(WebDriver driver) {
		this.driver = driver;
	}

	public void limpar() {
		driver.get(LimpaCenario.getUrl());
	}
}
