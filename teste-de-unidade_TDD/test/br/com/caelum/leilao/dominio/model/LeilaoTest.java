package br.com.caelum.leilao.dominio.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LeilaoTest {

	private Usuario ronaldo = new Usuario("Ronaldo");
	private Usuario denilson = new Usuario("Denilson");
	private Usuario ulisses = new Usuario("Ulisses");

	@Test
	public void naoAceitaLancesEmSequenciaDoMesmoUsuarioComApenasUmUsuarioOfertando() {
		Leilao leilao = new Leilao("CD autografado");

		leilao.propor(new Lance(ronaldo, 100));
		leilao.propor(new Lance(ronaldo, 250));
		leilao.propor(new Lance(ronaldo, 23));

		assertEquals(1, leilao.getLances().size());
		assertEquals(100, leilao.getLances().get(0).getValor(), 0.00001);
	}

	@Test
	public void naoAceitaLancesEmSequenciaDoMesmoUsuarioComVariosUsuariosOfertando() {
		Leilao leilao = new Leilao("CD autografado");

		leilao.propor(new Lance(ronaldo, 100));
		leilao.propor(new Lance(denilson, 700));
		leilao.propor(new Lance(ulisses, 500));
		leilao.propor(new Lance(denilson, 200));
		leilao.propor(new Lance(denilson, 400));// não deve aceitar
		leilao.propor(new Lance(ulisses, 500));
		leilao.propor(new Lance(ulisses, 800));// não deve aceitar
		leilao.propor(new Lance(ronaldo, 900));
		leilao.propor(new Lance(ronaldo, 1000));// não deve aceitar

		assertEquals(6, leilao.getLances().size());
		assertEquals(100, leilao.getLances().get(0).getValor(), 0.00001);
		assertEquals(700, leilao.getLances().get(1).getValor(), 0.00001);
		assertEquals(200, leilao.getLances().get(3).getValor(), 0.00001);
		assertEquals(500, leilao.getLances().get(4).getValor(), 0.00001);
		assertEquals(900, leilao.getLances().get(5).getValor(), 0.00001);
	}

	@Test
	public void naoAceitaMaisQueCincoLancesDoMesmoUsuarioComLancePosteriorDeOutroUsuario() {
		Leilao leilao = new Leilao("Geladeira");

		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));
		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));
		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));
		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));
		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));

		leilao.propor(new Lance(ulisses, 300));

		// não deve aceitar estes
		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));

		assertEquals(11, leilao.getLances().size());
	}

	@Test
	public void naoAceitaMaisQueCincoLancesDoMesmoUsuarioComLancePosteriorDeOutroUsuarioAposLancesInvalidos() {
		Leilao leilao = new Leilao("Geladeira");

		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));
		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));
		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));
		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));
		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));

		// não deve aceitar estes 2
		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));

		leilao.propor(new Lance(ulisses, 300));

		assertEquals(11, leilao.getLances().size());
	}

	@Test
	public void naoAceitaMaisQueCincoLancesDoMesmoUsuarioSemLancesPosterioresDeOutrosUsuarios() {
		Leilao leilao = new Leilao("Geladeira");

		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));
		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));
		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));
		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));
		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));

		// não deve aceitar estes
		leilao.propor(new Lance(denilson, 100));
		leilao.propor(new Lance(ronaldo, 300));

		assertEquals(10, leilao.getLances().size());
	}

	@Test
	public void dobrarUltimoLanceDoUsuarioComDoisUsuariosOfertandoEUmDobrando() {

		Leilao leilao = new Leilao("CD autografado");

		leilao.propor(new Lance(ronaldo, 100));
		leilao.propor(new Lance(ulisses, 120));
		leilao.dobrarLanceDo(ronaldo);

		assertEquals(3, leilao.getLances().size());

		assertEquals(new Lance(ronaldo, 100), leilao.getLances().get(0));

		assertEquals(new Lance(ronaldo, 200), leilao.getLances().get(leilao.getLances().size() - 1));

	}

	@Test
	public void dobrarUltimoLanceDoUsuarioComVariosUsuariosEDobrandoSeusLances() {

		Leilao leilao = new Leilao("CD autografado");

		leilao.propor(new Lance(ronaldo, 100));
		leilao.propor(new Lance(ulisses, 120));
		leilao.dobrarLanceDo(ronaldo);
		leilao.dobrarLanceDo(ulisses);
		leilao.dobrarLanceDo(ronaldo);
		leilao.dobrarLanceDo(ulisses);

		assertEquals(6, leilao.getLances().size());

		assertEquals(new Lance(ronaldo, 100), leilao.getLances().get(0));
		assertEquals(new Lance(ulisses, 240), leilao.getLances().get(3));
		assertEquals(new Lance(ulisses, 480), leilao.getLances().get(leilao.getLances().size() - 1));

	}

	@Test
	public void naoAceitadobrarUltimoLanceDoUsuarioDuasVezesSeguidas() {

		Leilao leilao = new Leilao("CD autografado");

		leilao.propor(new Lance(ronaldo, 100));
		leilao.dobrarLanceDo(ronaldo);
		leilao.dobrarLanceDo(ronaldo);

		assertEquals(1, leilao.getLances().size());
		assertEquals(new Lance(ronaldo, 100), leilao.getLances().get(0));

	}

	@Test
	public void naoAceitadobrarUltimoLanceDeUsuarioQueAindaNaoOfertou() {

		Leilao leilao = new Leilao("CD autografado");

		leilao.dobrarLanceDo(ronaldo);

		assertEquals(0, leilao.getLances().size());

	}
}
