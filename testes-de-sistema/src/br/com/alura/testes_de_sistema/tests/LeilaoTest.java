package br.com.alura.testes_de_sistema.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import br.com.alura.testes_de_sistema.model.Leilao;
import br.com.alura.testes_de_sistema.model.Usuario;
import br.com.alura.testes_de_sistema.util.LimpadorDeCenarios;
import br.com.alura.testes_de_sistema.util.ValidadorDeConteudoDasPaginas;
import br.com.alura.testes_de_sistema.util.PageObjects.leiloes.LeiloesCreatePage;
import br.com.alura.testes_de_sistema.util.PageObjects.leiloes.LeiloesRetrievePage;
import br.com.alura.testes_de_sistema.util.PageObjects.usuarios.UsuariosCreatePage;
import br.com.alura.testes_de_sistema.util.builder.LeilaoBuilder;

public class LeilaoTest {

	private WebDriver driver;
	private LeiloesCreatePage paginaDeCadastroDeLeiloes;
	private ValidadorDeConteudoDasPaginas validador;
	private Leilao leilao;
	private Usuario usuario;

	@Before
	public void inicializaTeste() {
		driver = new FirefoxDriver();
		new LimpadorDeCenarios(driver).limpar();
		usuario = new Usuario("Paulo Henrique", "user@user");
		new UsuariosCreatePage(driver).acessar().cadastrar(usuario);
		paginaDeCadastroDeLeiloes = new LeiloesRetrievePage(driver).acessar().clicarLinkNovoLeilao();
		validador = new ValidadorDeConteudoDasPaginas(driver);
	}

	@After
	public void finalizaTeste() {
		driver.close();
	}

	@Test
	public void cadastrarLeilaoValido() {
		String produto = "Geladeira";
		double valorInicial = 123.0;

		leilao = new LeilaoBuilder().para(produto).usado().com(valorInicial).pertencenteA(usuario.getNome()).criar();

		paginaDeCadastroDeLeiloes.cadastrar(leilao);

		Assert.assertTrue(validador.achou(usuario.getNome()));
		Assert.assertTrue(validador.achou("Sim"));

	}

	@Test
	public void naoAceitaLeilaoSemNome() {

		double valorInicial = 180.0;
		leilao = new LeilaoBuilder().com(valorInicial).pertencenteA(usuario.getNome()).criar();

		paginaDeCadastroDeLeiloes.cadastrar(leilao);

		Assert.assertTrue(validador.achou("Nome obrigatorio!"));

	}

	@Test
	public void naoAceitaLeilaoSemValorInicial() {

		leilao = new LeilaoBuilder().para("Geladeira").pertencenteA(usuario.getNome()).criar();

		paginaDeCadastroDeLeiloes.cadastrar(leilao);

		Assert.assertTrue(validador.achou("Valor inicial deve ser maior que zero!"));

	}
}
