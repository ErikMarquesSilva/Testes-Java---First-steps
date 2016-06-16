package br.com.alura.testes_de_sistema.util.PageObjects.leiloes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.alura.testes_de_sistema.model.Lance;

public class LeilaoDetailPage {

	private static final String CAMPO_VALOR = "lance.valor";
	private static final String COMBO_USUARIO = "lance.usuario.id";
	private WebDriver driver;

	public LeilaoDetailPage(WebDriver driver) {
		this.driver = driver;
	}

	public LeilaoDetailPage cadastrar(Lance lance) {
		new Select(driver.findElement(By.name(COMBO_USUARIO))).selectByVisibleText(lance.getUsuario().getNome());
		driver.findElement(By.name(CAMPO_VALOR)).sendKeys(String.valueOf(lance.getValor()));

		driver.findElement(By.id("btnDarLance")).click();

		limparCamposEguardarValidacaoDo(lance);

		return this;
	}

	@SuppressWarnings("deprecation")
	private void limparCamposEguardarValidacaoDo(Lance lance) {

		driver.findElement(By.name(CAMPO_VALOR)).clear();

		new WebDriverWait(driver, 10)
			.until(ExpectedConditions.textToBePresentInElement(By.id("lancesDados"), lance.getUsuario().getNome()));
	}

}
