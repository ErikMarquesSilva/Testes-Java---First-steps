package br.com.alura.testes_de_sistema.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import br.com.alura.testes_de_sistema.model.Usuario;
import br.com.alura.testes_de_sistema.util.LimpadorDeCenarios;
import br.com.alura.testes_de_sistema.util.ValidadorDeConteudoDasPaginas;
import br.com.alura.testes_de_sistema.util.PageObjects.usuarios.UsuariosCreatePage;
import br.com.alura.testes_de_sistema.util.PageObjects.usuarios.UsuariosRetrievePage;

public class UsuarioTest {

	private WebDriver driver;
	private UsuariosCreatePage paginaDeCadastroDeUsuario;
	private ValidadorDeConteudoDasPaginas validador;

	@Before
	public void inicializaTeste() {
		driver = new FirefoxDriver();
		new LimpadorDeCenarios(driver).limpar();
		paginaDeCadastroDeUsuario = new UsuariosRetrievePage(driver).acessar().clicarLinkNovoUsuario();
		validador = new ValidadorDeConteudoDasPaginas(driver);
	}

	@After
	public void finalizaTeste() {
		driver.close();
	}

	@Test
	public void cadastraUsuarioValido() {
		String nome = "Adriano Xavier";
		String email = "axavier@empresa.com.br";

		paginaDeCadastroDeUsuario.cadastrar(new Usuario(nome, email));

		assertTrue(validador.achou(nome));
		assertTrue(validador.achou(email));
	}

	@Test
	public void alterarUsuarioValido() {
		String nomeAntigo = "Adriano Xavier";
		String emailAntigo = "axavier@empresa.com.br";
		String nomeNovo = "Alterado";
		String emailNovo = "email@Alterado";

		paginaDeCadastroDeUsuario.cadastrar(new Usuario(nomeAntigo, emailAntigo)).clicarEmEditar()
			.alterar(new Usuario(nomeNovo, emailNovo));

		assertFalse(validador.achou(nomeAntigo));
		assertFalse(validador.achou(emailAntigo));

		assertTrue(validador.achou(nomeNovo));
		assertTrue(validador.achou(emailNovo));

	}

	@Test
	public void naoCadastraUsuarioSemNomeEEmail() {

		paginaDeCadastroDeUsuario.cadastrar(new Usuario("", ""));

		assertTrue(validador.achou("Nome obrigatorio!"));
		assertTrue(validador.achou("E-mail obrigatorio!"));
	}

	@Test
	public void naoCadastraUsuarioSemNome() {
		String email = "axavier@empresa.com.br";

		paginaDeCadastroDeUsuario.cadastrar(new Usuario("", email));

		String mensagemDeErro = "Nome obrigatorio!";
		assertTrue(validador.achou(mensagemDeErro));
	}

	@Test
	public void deletarUsuario() {
		String nome = "jonas";
		String email = "jonas@jonas";

		paginaDeCadastroDeUsuario.cadastrar(new Usuario(nome, email)).apagarUltimoInserido();

		assertFalse(validador.achou(nome));
		assertFalse(validador.achou(email));
	}

}
