package br.com.alura.testes_de_sistema.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import br.com.alura.testes_de_sistema.util.usuarios.UsuariosCreatePage;
import br.com.alura.testes_de_sistema.util.usuarios.UsuariosRetrievePage;

public class CadastroUsuarioTest {

	private WebDriver driver;
	private UsuariosCreatePage paginasDoUsuario;

	@Before
	public void inicializaTeste() {
		driver = new FirefoxDriver();
		paginasDoUsuario = new UsuariosRetrievePage(driver).acessar().clicarLinkNovoUsuario();
	}

	@After
	public void finalizaTeste() {
		driver.close();
	}

	@Test
	public void cadastraUsuarioValido() {
		String nome = "Adriano Xavier";
		String email = "axavier@empresa.com.br";

		paginasDoUsuario.digitarNome(nome).digitarEmail(email).salvar();

		assertTrue(achou(nome));
		assertTrue(achou(email));
	}

	@Test
	public void naoCadastraUsuarioSemNomeEEmail() {

		paginasDoUsuario.salvar();

		assertTrue(achou("Nome obrigatorio!"));
		assertTrue(achou("E-mail obrigatorio!"));
	}

	@Test
	public void naoCadastraUsuarioSemNome() {
		String email = "axavier@empresa.com.br";

		paginasDoUsuario.digitarEmail(email).salvar();

		String mensagemDeErro = "Nome obrigatorio!";
		assertTrue(achou(mensagemDeErro));
	}

	@Test
	public void deletarUsuario() {
		String nome = "jonas";
		String email = "jonas@jonas";

		paginasDoUsuario.digitarNome(nome).digitarEmail(email).salvar().apagarUltimoInserido();

		assertFalse(achou(nome));
		assertFalse(achou(email));
	}

	private boolean achou(String valor) {
		return driver.getPageSource().contains(valor);
	}
}
