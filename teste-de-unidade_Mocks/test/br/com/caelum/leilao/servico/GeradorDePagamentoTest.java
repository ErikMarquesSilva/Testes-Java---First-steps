package br.com.caelum.leilao.servico;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
// import static org.mockito.Matchers.any;
// import static org.mockito.Mockito.doThrow;
// import static org.mockito.Mockito.inOrder;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.never;
// import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
//
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Calendar;
// import java.util.GregorianCalendar;
// import java.util.List;
//
// import org.junit.After;
// import org.junit.Before;
// import org.junit.Test;
// import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Pagamento;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.infra.dao.RepositorioDeLeiloes;
import br.com.caelum.leilao.infra.dao.RepositorioDePagamentos;

public class GeradorDePagamentoTest {

	private Avaliador avaliador;
	private RepositorioDeLeiloes repositorioDeLeiloes;
	private RepositorioDePagamentos repositorioDePagamentos;
	private Usuario usuario1;
	private Usuario usuario2;
	private List<Leilao> minhaListaDeLeiloes;
	private GeradorDePagamento geradorDePagamento;
	private Relogio relogio;

	@Before
	public void setUp() {
		avaliador = new Avaliador();
		repositorioDeLeiloes = mock(RepositorioDeLeiloes.class);
		repositorioDePagamentos = mock(RepositorioDePagamentos.class);
		relogio = mock(Relogio.class);
		geradorDePagamento = new GeradorDePagamento(avaliador, repositorioDeLeiloes, repositorioDePagamentos, relogio);
		usuario1 = new Usuario("Usuário Um");
		usuario2 = new Usuario("Usuário Dois");
		minhaListaDeLeiloes = new ArrayList<>();
	}

	@After
	public void limpar() {
		minhaListaDeLeiloes = null;
	}

	@Test
	public void testGerar() {
		Calendar dataVencida = Calendar.getInstance();
		dataVencida.add(Calendar.DAY_OF_MONTH, -20);

		Leilao leilao1 = new CriadorDeLeilao().para("Notebook").naData(dataVencida).lance(usuario1, 100)
			.lance(usuario2, 300).constroi();
		minhaListaDeLeiloes = Arrays.asList(leilao1);

		when(repositorioDeLeiloes.encerrados()).thenReturn(minhaListaDeLeiloes);
		when(relogio.hoje()).thenReturn(Calendar.getInstance());

		geradorDePagamento.gerar();

		ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
		verify(repositorioDePagamentos).salvar(argumento.capture());

		Pagamento pagamentoGerado = argumento.getValue();

		assertThat(pagamentoGerado.getValor(), equalTo(300d));

	}

	@Test
	public void lancaPagamentoFeitoNoSabadoParaSegunda() {
		Calendar dataVencida = Calendar.getInstance();
		dataVencida.add(Calendar.DAY_OF_MONTH, -20);

		Leilao leilao1 = new CriadorDeLeilao().para("Notebook").naData(dataVencida).lance(usuario1, 100)
			.lance(usuario2, 300).constroi();

		minhaListaDeLeiloes = Arrays.asList(leilao1);

		when(repositorioDeLeiloes.encerrados()).thenReturn(minhaListaDeLeiloes);
		when(relogio.hoje()).thenReturn(new GregorianCalendar(2016, Calendar.FEBRUARY, 13));

		geradorDePagamento.gerar();

		ArgumentCaptor<Pagamento> captor = ArgumentCaptor.forClass(Pagamento.class);
		verify(repositorioDePagamentos).salvar(captor.capture());

		Pagamento pagamento = captor.getValue();

		assertThat(pagamento.getData().get(Calendar.DAY_OF_MONTH), equalTo(15));
		assertThat(pagamento.getData().get(Calendar.MONTH), equalTo(Calendar.FEBRUARY));
		assertThat(pagamento.getData().get(Calendar.YEAR), equalTo(2016));

		assertThat(pagamento.getData().get(Calendar.DAY_OF_WEEK), equalTo(Calendar.MONDAY));

	}

	@Test
	public void lancaPagamentoFeitoNoDomingoParaSegunda() {
		Calendar dataVencida = Calendar.getInstance();
		dataVencida.add(Calendar.DAY_OF_MONTH, -20);

		Leilao leilao1 = new CriadorDeLeilao().para("Notebook").naData(dataVencida).lance(usuario1, 100)
			.lance(usuario2, 300).constroi();

		minhaListaDeLeiloes = Arrays.asList(leilao1);

		when(repositorioDeLeiloes.encerrados()).thenReturn(minhaListaDeLeiloes);
		when(relogio.hoje()).thenReturn(new GregorianCalendar(2016, Calendar.FEBRUARY, 14));

		geradorDePagamento.gerar();

		ArgumentCaptor<Pagamento> captor = ArgumentCaptor.forClass(Pagamento.class);
		verify(repositorioDePagamentos).salvar(captor.capture());

		Pagamento pagamento = captor.getValue();

		assertThat(pagamento.getData().get(Calendar.DAY_OF_MONTH), equalTo(15));
		assertThat(pagamento.getData().get(Calendar.MONTH), equalTo(Calendar.FEBRUARY));
		assertThat(pagamento.getData().get(Calendar.YEAR), equalTo(2016));

		assertThat(pagamento.getData().get(Calendar.DAY_OF_WEEK), equalTo(Calendar.MONDAY));

	}
}
