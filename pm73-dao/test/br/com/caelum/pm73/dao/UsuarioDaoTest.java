package br.com.caelum.pm73.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.pm73.dominio.Usuario;

public class UsuarioDaoTest {
	private Session session;
	private UsuarioDao usuarioDao;
	private String nome;
	private String email;
	private CriadorDeSessao criadorDeSessao;

	@Before
	public void inicializaTeste() {
		criadorDeSessao = new CriadorDeSessao();
		session = criadorDeSessao.getSession();
		session.beginTransaction();
		usuarioDao = new UsuarioDao(session);
	}

	@After
	public void finalizaTeste() {
		session.getTransaction().rollback();
		session.close();
	}

	@Test
	public void devolveUmUsuarioPesquisandoPorNomeEEmail() {
		nome = "jonas";
		email = "jonas@jonas";

		Usuario usuarioInserido = new Usuario(nome, email);

		usuarioDao.salvar(usuarioInserido);

		Usuario usuarioPesquisado = usuarioDao.porNomeEEmail(nome, email);

		assertEquals(usuarioInserido.getNome(), usuarioPesquisado.getNome());
		assertEquals(usuarioInserido.getEmail(), usuarioPesquisado.getEmail());

	}

	@Test
	public void pesquisaPorNomeEEmailPorUsuarioQueNaoExiste() {
		nome = "não existe";
		email = "não existe";

		Usuario usuarioInexistente = usuarioDao.porNomeEEmail(nome, email);

		assertNull(usuarioInexistente);
	}

	@Test
	public void validarDeletar() {

		Usuario usuario = new Usuario("user", "user@user");

		usuarioDao.salvar(usuario);
		usuarioDao.deletar(usuario);

		sincronizarBD();

		Usuario usuarioNulo = usuarioDao.porNomeEEmail(usuario.getNome(), usuario.getEmail());

		assertNull(usuarioNulo);
	}

	@Test
	public void validaAlterar() {

		String nomeAntigo = "user";
		String emailAntigo = "user@user";
		String nomeNovo = "Editado";
		String emailNovo = "user@edited";

		Usuario usuario = new Usuario(nomeAntigo, emailAntigo);

		usuarioDao.salvar(usuario);

		sincronizarBD();
		assertNotNull(usuarioDao.porNomeEEmail(nomeAntigo, emailAntigo));

		usuario.setEmail(emailNovo);
		usuario.setNome(nomeNovo);
		usuarioDao.atualizar(usuario);

		sincronizarBD();

		assertNull(usuarioDao.porNomeEEmail(nomeAntigo, emailAntigo));
		assertNotNull(usuarioDao.porNomeEEmail(nomeNovo, emailNovo));
	}

	private void sincronizarBD() {
		session.flush();
		session.clear();
	}
}
