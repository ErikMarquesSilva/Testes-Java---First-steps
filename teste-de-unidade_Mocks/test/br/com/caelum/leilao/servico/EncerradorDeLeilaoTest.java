package br.com.caelum.leilao.servico;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.infra.dao.RepositorioDeLeiloes;

public class EncerradorDeLeilaoTest {

	private static Calendar hoje;
	private RepositorioDeLeiloes repositorioDeLeiloes;
	private List<Leilao> minhaListaDeLeiloes;
	private EncerradorDeLeilao encerradorDeLeilao;
	private EnviadorDeEmail enviadorDeEmail;
	private InOrder inOrder;

	@Before
	public void setUp() {
		hoje = Calendar.getInstance();
		repositorioDeLeiloes = mock(RepositorioDeLeiloes.class);
		enviadorDeEmail = mock(EnviadorDeEmail.class);
		encerradorDeLeilao = new EncerradorDeLeilao(repositorioDeLeiloes, enviadorDeEmail);
		inOrder = inOrder(enviadorDeEmail, repositorioDeLeiloes);
	}

	@After
	public void depoisDeCadaTeste() {
		minhaListaDeLeiloes = null;
	}

	@Test
	public void deveEncerrarEAtualizarApenas1LeilaoEntreVarios() {

		Calendar dataVencida = new GregorianCalendar(2012, 1, 10);

		Leilao leilao = new CriadorDeLeilao().para("TV de led").naData(dataVencida).constroi();
		Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(hoje).constroi();
		Leilao leilao3 = new CriadorDeLeilao().para("Cadeira").naData(hoje).constroi();
		Leilao leilao4 = new CriadorDeLeilao().para("Sofá").naData(hoje).constroi();

		minhaListaDeLeiloes = Arrays.asList(leilao, leilao2, leilao3, leilao4);
		when(repositorioDeLeiloes.correntes()).thenReturn(minhaListaDeLeiloes);

		encerradorDeLeilao.encerra();

		inOrder.verify(repositorioDeLeiloes, times(1)).atualiza(leilao);
		inOrder.verify(enviadorDeEmail, times(1)).envia(leilao);

		verify(repositorioDeLeiloes, never()).atualiza(leilao2);
		verify(repositorioDeLeiloes, never()).atualiza(leilao3);
		verify(repositorioDeLeiloes, never()).atualiza(leilao4);

		verify(enviadorDeEmail, never()).envia(leilao2);
		verify(enviadorDeEmail, never()).envia(leilao3);
		verify(enviadorDeEmail, never()).envia(leilao4);

		assertThat(encerradorDeLeilao.getTotalEncerrados(), equalTo(1));
		assertThat(minhaListaDeLeiloes.get(0).isEncerrado(), equalTo(true));
		assertThat(minhaListaDeLeiloes.get(1).isEncerrado(), equalTo(false));
		assertThat(minhaListaDeLeiloes.get(2).isEncerrado(), equalTo(false));
		assertThat(minhaListaDeLeiloes.get(3).isEncerrado(), equalTo(false));

	}

	@Test
	public void naoDeveEncerrarEAtualizarNenhumLeilaoEntreVarios() {

		Calendar ontem = new GregorianCalendar(hoje.get(Calendar.YEAR), hoje.get(Calendar.MONTH),
			hoje.get(Calendar.DAY_OF_MONTH) - 1);

		Leilao leilao = new CriadorDeLeilao().para("TV de led").naData(ontem).constroi();
		Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(ontem).constroi();

		minhaListaDeLeiloes = Arrays.asList(leilao, leilao2);
		when(repositorioDeLeiloes.correntes()).thenReturn(minhaListaDeLeiloes);

		encerradorDeLeilao.encerra();

		verify(repositorioDeLeiloes, never()).atualiza(leilao);
		verify(repositorioDeLeiloes, never()).atualiza(leilao2);

		verify(enviadorDeEmail, never()).envia(leilao);
		verify(enviadorDeEmail, never()).envia(leilao2);

		assertThat(minhaListaDeLeiloes.size(), equalTo(2));
		assertThat(encerradorDeLeilao.getTotalEncerrados(), equalTo(0));
		assertThat(encerradorDeLeilao.getTotalEncerrados(), equalTo(0));
		assertThat(minhaListaDeLeiloes.get(0).isEncerrado(), equalTo(false));
		assertThat(minhaListaDeLeiloes.get(1).isEncerrado(), equalTo(false));

	}

	@Test
	public void semLeiloesParaEncerrar() {

		minhaListaDeLeiloes = new ArrayList<>();
		when(repositorioDeLeiloes.correntes()).thenReturn(minhaListaDeLeiloes);

		encerradorDeLeilao.encerra();

		assertThat(minhaListaDeLeiloes.size(), equalTo(0));
		assertThat(encerradorDeLeilao.getTotalEncerrados(), equalTo(0));
	}

	@Test
	public void deveTratarExcecaoLancadaNoAtualizaEContinuarOFluxo() {
		Calendar dataVencida = GregorianCalendar.getInstance();
		dataVencida.add(Calendar.DAY_OF_MONTH, -30);

		Leilao leilao = new CriadorDeLeilao().para("TV de led").naData(dataVencida).constroi();
		Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(dataVencida).constroi();
		minhaListaDeLeiloes = Arrays.asList(leilao, leilao2);

		when(repositorioDeLeiloes.correntes()).thenReturn(minhaListaDeLeiloes);

		doThrow(new RuntimeException()).when(repositorioDeLeiloes).atualiza(leilao);

		encerradorDeLeilao.encerra();

		assertThat(encerradorDeLeilao.getTotalEncerrados(), equalTo(2));

		verify(repositorioDeLeiloes, times(1)).atualiza(leilao);
		verify(enviadorDeEmail, never()).envia(leilao);

		verify(repositorioDeLeiloes, times(1)).atualiza(leilao2);
		verify(enviadorDeEmail, times(1)).envia(leilao2);
	}

	@Test
	public void deveTratarExcecaoLancadaNoEnvioDeEmailEContinuarOFluxo() throws Exception {
		Calendar dataVencida = GregorianCalendar.getInstance();
		dataVencida.add(Calendar.DAY_OF_MONTH, -15);

		Leilao leilao = new CriadorDeLeilao().para("TV de led").naData(dataVencida).constroi();
		Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(dataVencida).constroi();
		minhaListaDeLeiloes = Arrays.asList(leilao, leilao2);

		when(repositorioDeLeiloes.correntes()).thenReturn(minhaListaDeLeiloes);

		doThrow(new RuntimeException()).when(enviadorDeEmail).envia(leilao);

		encerradorDeLeilao.encerra();

		assertThat(encerradorDeLeilao.getTotalEncerrados(), equalTo(2));
		inOrder.verify(repositorioDeLeiloes, times(1)).atualiza(leilao);
		inOrder.verify(enviadorDeEmail, times(1)).envia(leilao);
		inOrder.verify(repositorioDeLeiloes, times(1)).atualiza(leilao2);
		inOrder.verify(enviadorDeEmail, times(1)).envia(leilao2);

	}

	@Test
	public void deveTratarExcecaoLancadaNoAtualizaPorTodosOsLeiloesENaoEnviarNenhumEmail() throws Exception {
		Calendar dataVencida = GregorianCalendar.getInstance();
		dataVencida.add(Calendar.DAY_OF_MONTH, -15);

		Leilao leilao = new CriadorDeLeilao().para("TV de led").naData(dataVencida).constroi();
		Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(dataVencida).constroi();
		minhaListaDeLeiloes = Arrays.asList(leilao, leilao2);

		when(repositorioDeLeiloes.correntes()).thenReturn(minhaListaDeLeiloes);

		doThrow(new RuntimeException()).when(repositorioDeLeiloes).atualiza(any(Leilao.class));

		encerradorDeLeilao.encerra();

		verify(enviadorDeEmail, never()).envia(any(Leilao.class));
	}

}
