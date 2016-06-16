package br.com.caelum.leilao.dominio.util;

import static br.com.caelum.leilao.dominio.util.leilao.matcher.LeilaoMatcher.temO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.leilao.dominio.dataBuilder.LeilaoDataBuilder;
import br.com.caelum.leilao.dominio.model.Lance;
import br.com.caelum.leilao.dominio.model.Leilao;
import br.com.caelum.leilao.dominio.model.Usuario;
import br.com.caelum.leilao.dominio.util.leilao.Avaliador;

public class AvaliadorTest {

	private Usuario jonas;
	private Usuario pedro;
	private Usuario paulo;
	private Avaliador avaliador;
	private LeilaoDataBuilder criadorDeleilao;

	@Before
	public void setUp() {
		this.jonas = new Usuario("Jonas");
		this.pedro = new Usuario("Pedro");
		this.paulo = new Usuario("Paulo");
		this.avaliador = new Avaliador();
		this.criadorDeleilao = new LeilaoDataBuilder();
		// System.out.println("inicializando teste!");
	}

	@After
	public void finaliza() {
		// System.out.println("finalizando teste!");
	}

	@BeforeClass
	public static void testandoBeforeClass() {
		// System.out.println("before class");
	}

	@AfterClass
	public static void testandoAfterClass() {
		// System.out.println("after class");
	}

	@Test
	public void testAvaliarEmOrdemCrescente() throws Exception {
		Lance lance1 = new Lance(jonas, 50);
		Lance lance2 = new Lance(pedro, 150);
		Lance lance3 = new Lance(paulo, 400);

		Leilao leilao = criadorDeleilao.para("X box").com(lance1).com(lance2).com(lance3).construir();

		double mediaDosValores = 200;
		avaliador.avaliar(leilao);

		assertThat(leilao.getLances().size(), equalTo(3));
		assertThat(avaliador.getMaioresLances().size(), equalTo(3));

		assertThat(avaliador.getMaiorLance(), equalTo(lance3.getValor()));
		assertThat(avaliador.getMenorLance(), equalTo(lance1.getValor()));
		assertThat(avaliador.getMediaDeValorDosLances(), equalTo(mediaDosValores));

		assertThat(avaliador.getMaioresLances(), hasItems(lance1, lance2, lance3));

		assertThat(avaliador.getMaioresLances().get(0), equalTo(lance3));
		assertThat(avaliador.getMaioresLances().get(1), equalTo(lance2));
		assertThat(avaliador.getMaioresLances().get(2), equalTo(lance1));

	}

	@Test
	public void testAvaliarApenasUmLance() throws Exception {

		Leilao leilao = criadorDeleilao.para("Casa na praia").com(new Lance(jonas, 50000)).construir();

		double valorDoUnicoLance = 50000;

		avaliador.avaliar(leilao);

		assertThat(leilao.getLances().size(), equalTo(1));
		assertThat(avaliador.getMaioresLances().size(), equalTo(1));

		assertThat(avaliador.getMaiorLance(), equalTo(valorDoUnicoLance));
		assertThat(avaliador.getMenorLance(), equalTo(valorDoUnicoLance));
		assertThat(avaliador.getMediaDeValorDosLances(), equalTo(valorDoUnicoLance));

		assertThat(avaliador.getMaioresLances(), hasItem(leilao.getLances().get(0)));

	}

	@Test
	public void testAvaliarValoresRandomicos() throws Exception {

		Leilao leilao = criadorDeleilao.para("TV 30 pol").com(new Lance(jonas, 200)).com(new Lance(pedro, 450))
			.com(new Lance(paulo, 120)).com(new Lance(pedro, 700)).com(new Lance(paulo, 630)).com(new Lance(jonas, 230))
			.construir();

		avaliador.avaliar(leilao);

		assertEquals(6, leilao.getLances().size());
		assertEquals(120, avaliador.getMenorLance(), 0.00001);
		assertEquals(700, avaliador.getMaiorLance(), 0.00001);
		assertEquals(388.3333333, avaliador.getMediaDeValorDosLances(), 0.00001);

		assertEquals(3, avaliador.getMaioresLances().size());
		assertEquals(700, avaliador.getMaioresLances().get(0).getValor(), 0.00001);
		assertEquals(630, avaliador.getMaioresLances().get(1).getValor(), 0.00001);
		assertEquals(450, avaliador.getMaioresLances().get(2).getValor(), 0.00001);

	}

	@Test
	public void testAvaliarEmOrdemDecrescente() throws Exception {

		Leilao leilao = criadorDeleilao.para("Camiseta autografada").com(new Lance(paulo, 400))
			.com(new Lance(pedro, 300)).com(new Lance(paulo, 200)).com(new Lance(pedro, 100)).construir();

		avaliador.avaliar(leilao);

		assertThat(leilao.getLances().size(), equalTo(4));

		assertThat(avaliador.getMaiorLance(), equalTo(400d));
		assertThat(avaliador.getMenorLance(), equalTo(100d));
		assertThat(avaliador.getMediaDeValorDosLances(), equalTo(250d));

		Lance lance = new Lance(pedro, 300);

		assertThat(leilao, temO(lance));

	}

	@Test(expected = Exception.class)
	public void testAvaliarSemLances() throws Exception {
		Leilao leilao = criadorDeleilao.para("Carro muito velho").construir();
		avaliador.avaliar(leilao);
	}
}
