package br.com.caelum.leilao.dominio.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.com.caelum.leilao.dominio.model.Lance;
import br.com.caelum.leilao.dominio.model.Usuario;
import br.com.caelum.leilao.dominio.util.lances.FiltroDeLances;

public class FiltroDeLancesTest {
	private Usuario joao = new Usuario("Joao");
	FiltroDeLances filtro = new FiltroDeLances();

	@Test
	public void deveSelecionarLancesEntre1000E3000() {

		List<Lance> resultado = filtro.filtra(
			Arrays.asList(new Lance(joao, 2000), new Lance(joao, 1000), new Lance(joao, 3000), new Lance(joao, 800)));

		assertEquals(1, resultado.size());
		assertEquals(2000, resultado.get(0).getValor(), 0.00001);
	}

	@Test
	public void deveSelecionarLancesEntre500E700() {
		List<Lance> resultado = filtro.filtra(
			Arrays.asList(new Lance(joao, 600), new Lance(joao, 500), new Lance(joao, 700), new Lance(joao, 800)));

		assertEquals(1, resultado.size());
		assertEquals(600, resultado.get(0).getValor(), 0.00001);
	}

	@Test
	public void deveSelecionarLancesMaioresQue5000() {
		List<Lance> resultado = filtro
			.filtra(Arrays.asList(new Lance(joao, 5000), new Lance(joao, 7000), new Lance(joao, 400)));

		assertEquals(1, resultado.size());
		assertEquals(7000, resultado.get(0).getValor(), 0.00001);
	}

	@Test
	public void deveSelecionarTodosOsLancesPropostos() {
		List<Lance> resultado = filtro
			.filtra(Arrays.asList(new Lance(joao, 2000), new Lance(joao, 600), new Lance(joao, 40000)));

		assertEquals(3, resultado.size());
		assertEquals(2000, resultado.get(0).getValor(), 0.00001);
		assertEquals(600, resultado.get(1).getValor(), 0.00001);
		assertEquals(40000, resultado.get(2).getValor(), 0.00001);
	}

	@Test
	public void deveNaoSelecionarTodosOsLancesPropostos() {
		List<Lance> resultado = filtro.filtra(Arrays.asList(new Lance(joao, 300), new Lance(joao, 4000)));

		assertEquals(0, resultado.size());
	}

}
