package br.com.alura.testes_de_sistema.testes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TesteAutomatizado {

	public static void main(String[] args) {

		WebDriver webDriver = new FirefoxDriver();
		webDriver.get("http://www.google.com.br/");

		WebElement fieldSearch = webDriver.findElement(By.name("q"));
		fieldSearch.sendKeys("Caelum");

		fieldSearch.submit();

	}
}
