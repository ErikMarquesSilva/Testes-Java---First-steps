package br.com.alura.testes_de_sistema.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import br.com.alura.testes_de_sistema.model.Lance;
import br.com.alura.testes_de_sistema.model.Leilao;
import br.com.alura.testes_de_sistema.model.Usuario;
import br.com.alura.testes_de_sistema.util.LimpadorDeCenarios;
import br.com.alura.testes_de_sistema.util.ValidadorDeConteudoDasPaginas;
import br.com.alura.testes_de_sistema.util.PageObjects.leiloes.LeilaoDetailPage;
import br.com.alura.testes_de_sistema.util.PageObjects.leiloes.LeiloesRetrievePage;
import br.com.alura.testes_de_sistema.util.builder.LeilaoBuilder;
import br.com.alura.testes_de_sistema.util.criadorDeDependenciasCenarios.DependenciasDeLance;

public class LanceTest {

	private WebDriver driver;
	private LeilaoDetailPage paginaDeDetalhesDoLeilao;
	private Usuario dono;
	private Usuario participante;
	private ValidadorDeConteudoDasPaginas validador;

	@Before
	public void inicializaTeste() {
		driver = new FirefoxDriver();
		new LimpadorDeCenarios(driver).limpar();
		validador = new ValidadorDeConteudoDasPaginas(driver);
		prepararDependenciasDoCenario();
		paginaDeDetalhesDoLeilao = new LeiloesRetrievePage(driver).clicarNoLinkExibir();
	}

	@After
	public void finalizaTeste() {
		driver.close();
	}

	@Test
	public void cadastraLanceValido() {
		Lance lance = new Lance(participante, 190d);
		paginaDeDetalhesDoLeilao.cadastrar(lance);

		Assert.assertTrue(validador.achou(participante.getNome()));
		Assert.assertTrue(validador.achou(String.valueOf(lance.getValor())));
	}

	private void prepararDependenciasDoCenario() {
		dono = new Usuario("Dono", "dona@dono");
		participante = new Usuario("Participante", "participante@participante");

		Leilao leilao = new LeilaoBuilder().para("Geladeira").com(200.0).pertencenteA(dono.getNome()).criar();

		new DependenciasDeLance(driver).com(dono).com(participante).com(leilao);

	}
}
