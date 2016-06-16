package br.com.caelum.pm73.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.pm73.dominio.Lance;
import br.com.caelum.pm73.dominio.Leilao;
import br.com.caelum.pm73.dominio.LeilaoBuilder;
import br.com.caelum.pm73.dominio.Usuario;

public class LeilaoDaoTest {
	private Session session;
	private LeilaoDao leilaoDao;
	private CriadorDeSessao criadorDeSessao;
	private Usuario dono;
	private UsuarioDao usuarioDao;
	private Leilao leilao;
	private Leilao leilao2;

	@Before
	public void inicializaTeste() {
		criadorDeSessao = new CriadorDeSessao();
		session = criadorDeSessao.getSession();
		session.beginTransaction();
		leilaoDao = new LeilaoDao(session);
		usuarioDao = new UsuarioDao(session);

		dono = new Usuario("Maurício", "mauricio@mauricio");

	}

	@After
	public void encerraTeste() {
		session.getTransaction().rollback();
		session.close();
	}

	@Test
	public void deveRetornarUmLeilaoNaoEncerradoNoTotal() {
		leilao = new LeilaoBuilder().comDono(dono).constroi();
		leilao2 = new LeilaoBuilder().comDono(dono).constroi();

		leilao2.encerra();

		usuarioDao.salvar(dono);

		leilaoDao.salvar(leilao);
		leilaoDao.salvar(leilao2);

		long totalDeLeiloesNaoEncerrados = leilaoDao.total();

		assertEquals(1, totalDeLeiloesNaoEncerrados);

	}

	@Test
	public void deveRetornarNenhumLeilaoNaoEncerradoNoTotal() {

		leilao = new LeilaoBuilder().comDono(dono).constroi();
		leilao.encerra();
		leilao2 = new LeilaoBuilder().comDono(dono).constroi();
		leilao2.encerra();

		usuarioDao.salvar(dono);

		leilaoDao.salvar(leilao);
		leilaoDao.salvar(leilao2);

		long totalDeLeiloesNaoEncerrados = leilaoDao.total();

		assertEquals(0, totalDeLeiloesNaoEncerrados);

	}

	@Test
	public void retornaUmLeilaoNovo() {
		leilao = new LeilaoBuilder().comDono(dono).constroi();
		leilao2 = new LeilaoBuilder().usado().comDono(dono).constroi();

		usuarioDao.salvar(dono);

		leilaoDao.salvar(leilao);
		leilaoDao.salvar(leilao2);

		List<Leilao> novos = leilaoDao.novos();

		assertEquals(1, novos.size());
		assertEquals(leilao.getNome(), novos.get(0).getNome());

	}

	@Test
	public void retornaAntigosNaDataLimite() {

		leilao = new LeilaoBuilder().comDono(dono).diasAtras(7).constroi();
		leilao2 = new LeilaoBuilder().usado().comDono(dono).constroi();
		Leilao leilao3 = new LeilaoBuilder().usado().diasAtras(7).comDono(dono).constroi();

		usuarioDao.salvar(dono);

		leilaoDao.salvar(leilao);
		leilaoDao.salvar(leilao2);
		leilaoDao.salvar(leilao3);

		List<Leilao> antigos = leilaoDao.antigos();

		assertEquals(2, antigos.size());

		assertEquals(leilao.getNome(), antigos.get(0).getNome());
		assertEquals(leilao3.getNome(), antigos.get(1).getNome());
	}

	@Test
	public void retornaNaoEncerradosDentroDoPeriodo() {
		leilao = new LeilaoBuilder().comDono(dono).constroi();
		leilao2 = new LeilaoBuilder().usado().diasAtras(20).comDono(dono).constroi();

		usuarioDao.salvar(dono);

		leilaoDao.salvar(leilao);
		leilaoDao.salvar(leilao2);

		List<Leilao> leilaosDentroDoPeriodo = leilaoDao.porPeriodo(diasRetorcedidosDaDataAtual(10),
			diasRetorcedidosDaDataAtual(0));

		assertEquals(1, leilaosDentroDoPeriodo.size());
		assertEquals(leilao.getNome(), leilaosDentroDoPeriodo.get(0).getNome());

	}

	@Test
	public void naoDeveRetornarEncerradosDentroDoPeriodo() {
		leilao = new LeilaoBuilder().comDono(dono).encerrado().constroi();
		leilao2 = new LeilaoBuilder().usado().diasAtras(20).encerrado().comDono(dono).constroi();

		usuarioDao.salvar(dono);

		leilaoDao.salvar(leilao);
		leilaoDao.salvar(leilao2);

		List<Leilao> leilaosDentroDoPeriodo = leilaoDao.porPeriodo(diasRetorcedidosDaDataAtual(10),
			diasRetorcedidosDaDataAtual(0));

		assertEquals(0, leilaosDentroDoPeriodo.size());

	}

	@Test
	public void disputadosEntreNaoRetornaLeiloesForaDoIntervalorDeValores() {

		leilao = new LeilaoBuilder().comNome("Play Station").comDono(dono).comValor(600).constroi();
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 4000, leilao));
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 400, leilao));
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 40000, leilao));
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 40, leilao));

		usuarioDao.salvar(dono);
		leilaoDao.salvar(leilao);

		List<Leilao> disputadosEntre = leilaoDao.disputadosEntre(100, 300);

		assertEquals(0, disputadosEntre.size());
	}

	@Test
	public void disputadosEntreNaoRetornaLeiloesComMenosDe3Lances() {

		leilao = new LeilaoBuilder().comNome("Play Station").comDono(dono).comValor(200).constroi();
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 4000, leilao));
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 400, leilao));

		usuarioDao.salvar(dono);
		leilaoDao.salvar(leilao);

		List<Leilao> disputadosEntre = leilaoDao.disputadosEntre(100, 300);

		assertEquals(0, disputadosEntre.size());
	}

	@Test
	public void disputadosEntreNaoRetornaLeiloesEncerrados() {

		leilao = new LeilaoBuilder().comNome("Play Station").comDono(dono).comValor(200).encerrado().constroi();
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 4000, leilao));
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 400, leilao));
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 40000, leilao));
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 40, leilao));

		usuarioDao.salvar(dono);
		leilaoDao.salvar(leilao);

		List<Leilao> disputadosEntre = leilaoDao.disputadosEntre(100, 300);

		assertEquals(0, disputadosEntre.size());
	}

	@Test
	public void validaDisputadosEntre() {

		leilao = new LeilaoBuilder().comNome("Play Station").comDono(dono).comValor(200).constroi();
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 4000, leilao));
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 400, leilao));
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 40000, leilao));

		leilao2 = new LeilaoBuilder().comNome("Mesa").comDono(dono).comValor(150).constroi();
		leilao2.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 4000, leilao));
		leilao2.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 400, leilao));
		leilao2.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 40000, leilao));
		leilao2.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 409, leilao));

		usuarioDao.salvar(dono);
		leilaoDao.salvar(leilao);
		leilaoDao.salvar(leilao2);

		List<Leilao> disputadosEntre = leilaoDao.disputadosEntre(100, 300);

		assertEquals(2, disputadosEntre.size());

		assertEquals(leilao.getNome(), disputadosEntre.get(0).getNome());
		assertEquals(leilao2.getNome(), disputadosEntre.get(1).getNome());
	}

	@Test
	public void listaDeLeiloesDoUsuarioQueNaoDeuNenhumLance() {
		leilao = new LeilaoBuilder().comNome("Play Station").comDono(dono).comValor(200).constroi();
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 4000, leilao));
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 400, leilao));
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 40000, leilao));

		Usuario usuarioPobre = new Usuario("Sem lances", "sem@lances");

		usuarioDao.salvar(usuarioPobre);
		usuarioDao.salvar(dono);
		leilaoDao.salvar(leilao);

		List<Leilao> disputadosEntre = leilaoDao.listaLeiloesDoUsuario(usuarioPobre);

		assertEquals(0, disputadosEntre.size());

	}

	@Test
	public void validaListaDeLeiloesEValorMedioInicialDoUsuario() {
		leilao = new LeilaoBuilder().comNome("Play Station").comDono(dono).comValor(100).constroi();
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 4000, leilao));
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 400, leilao));
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 40000, leilao));

		leilao2 = new LeilaoBuilder().comNome("Mesa").comDono(dono).comValor(250).constroi();
		leilao2.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 4000, leilao2));

		Leilao leilao3 = new LeilaoBuilder().comNome("Mesa").comDono(dono).comValor(250).constroi();
		leilao3.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 4000, leilao3));

		usuarioDao.salvar(dono);
		leilaoDao.salvar(leilao);
		leilaoDao.salvar(leilao2);
		leilaoDao.salvar(leilao3);

		List<Leilao> disputadosEntre = leilaoDao.listaLeiloesDoUsuario(dono);

		assertEquals(3, disputadosEntre.size());

		assertEquals(leilao.getNome(), disputadosEntre.get(0).getNome());
		assertEquals(leilao2.getNome(), disputadosEntre.get(1).getNome());
		assertEquals(leilao3.getNome(), disputadosEntre.get(2).getNome());

	}

	@Test
	public void validaValorMedioInicialDosLancesDoUsuarioEmApenasUmLeilao() {
		leilao = new LeilaoBuilder().comNome("Play Station").comDono(dono).comValor(250).constroi();
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 4000, leilao));
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 400, leilao));
		leilao.adicionaLance(new Lance(diasRetorcedidosDaDataAtual(0), dono, 40000, leilao));

		usuarioDao.salvar(dono);
		leilaoDao.salvar(leilao);

		double valorInicialMedioDoUsuario = leilaoDao.getValorInicialMedioDoUsuario(dono);

		assertEquals(250, valorInicialMedioDoUsuario, 0.0001);

	}

	@Test
	public void validaDeletar() {
		leilao = new LeilaoBuilder().comNome("Play Station").comDono(dono).comValor(250).constroi();

		usuarioDao.salvar(dono);
		leilaoDao.salvar(leilao);

		leilaoDao.deleta(leilao);

		session.flush();
		session.clear();

		assertNull(leilaoDao.porId(leilao.getId()));
	}

	private Calendar diasRetorcedidosDaDataAtual(int dias) {
		Calendar data = Calendar.getInstance();
		data.add(Calendar.DAY_OF_MONTH, -dias);
		return data;
	}

}
