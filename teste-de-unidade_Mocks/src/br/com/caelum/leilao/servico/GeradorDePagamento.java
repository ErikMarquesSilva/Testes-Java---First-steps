package br.com.caelum.leilao.servico;

import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;

import java.util.Calendar;
import java.util.List;

import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Pagamento;
import br.com.caelum.leilao.infra.dao.RepositorioDeLeiloes;
import br.com.caelum.leilao.infra.dao.RepositorioDePagamentos;

public class GeradorDePagamento {

	private final Avaliador avaliador;
	private final RepositorioDeLeiloes repositorioDeLeiloes;
	private final RepositorioDePagamentos repositorioDePagamentos;
	private Relogio relogio;

	public GeradorDePagamento(Avaliador avaliador, RepositorioDeLeiloes repositorioDeLeiloes,
		RepositorioDePagamentos repositorioDePagamentos, Relogio relogio) {
		this.avaliador = avaliador;
		this.repositorioDeLeiloes = repositorioDeLeiloes;
		this.repositorioDePagamentos = repositorioDePagamentos;
		this.relogio = relogio;
	}

	public void gerar() {
		List<Leilao> leilaosEncerrados = repositorioDeLeiloes.encerrados();

		for (Leilao leilao : leilaosEncerrados) {
			avaliador.avalia(leilao);
			Pagamento pagamento = new Pagamento(avaliador.getMaiorLance(), diaValido());
			repositorioDePagamentos.salvar(pagamento);
		}
	}

	private Calendar diaValido() {

		if (eh(SATURDAY)) {
			relogio.hoje().add(Calendar.DAY_OF_WEEK, 2);
		}
		else {
			if (eh(SUNDAY)) {
				relogio.hoje().add(Calendar.DAY_OF_WEEK, 1);
			}
		}
		return relogio.hoje();
	}

	private boolean eh(int dia) {
		return relogio.hoje().get(Calendar.DAY_OF_WEEK) == dia;
	}
}
