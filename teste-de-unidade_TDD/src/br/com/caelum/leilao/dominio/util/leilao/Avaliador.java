package br.com.caelum.leilao.dominio.util.leilao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.caelum.leilao.dominio.model.Lance;
import br.com.caelum.leilao.dominio.model.Leilao;

public class Avaliador {

	private double menorLance = Double.POSITIVE_INFINITY;
	private double maiorLance = Double.NEGATIVE_INFINITY;
	private double mediaDeValorDosLances = 0;
	private List<Lance> maioresLances = new ArrayList<>();

	public void avaliar(Leilao leilao) throws Exception {

		if (naoTeveLancesNo(leilao)) {
			throw new Exception("Precisa de ao menos um lance para ser avaliado!");
		}
		else {
			calcularMaiorEMenorLances(leilao);
			calcularMediaDeValoresDosLancesNo(leilao);
			calcularOsMaioresLancesDo(leilao);
		}

	}

	private boolean naoTeveLancesNo(Leilao leilao) {
		return leilao.getLances().size() == 0;
	}

	private void calcularMaiorEMenorLances(Leilao leilao) {
		for (Lance lance : leilao.getLances()) {
			recuperarMenor(lance);
			recuperarMaior(lance);
		}
	}

	private void recuperarMaior(Lance lance) {
		if (this.maiorLance < lance.getValor()) {
			this.maiorLance = lance.getValor();
		}
	}

	private void recuperarMenor(Lance lance) {
		if (this.menorLance > lance.getValor()) {
			this.menorLance = lance.getValor();
		}
	}

	private void calcularMediaDeValoresDosLancesNo(Leilao leilao) {
		this.mediaDeValorDosLances = totalDeValoresDeLancesOferecidosNo(leilao) / numeroDeLancesNo(leilao);
	}

	private int numeroDeLancesNo(Leilao leilao) {
		return leilao.getLances().size();
	}

	private double totalDeValoresDeLancesOferecidosNo(Leilao leilao) {
		double somaDoValorDosLances = 0;
		for (Lance lance : leilao.getLances()) {
			somaDoValorDosLances += lance.getValor();
		}
		return somaDoValorDosLances;
	}

	private void calcularOsMaioresLancesDo(Leilao leilao) {
		maioresLances = new ArrayList<Lance>(leilao.getLances());

		Collections.sort(maioresLances, new Comparator<Lance>() {
			@Override
			public int compare(Lance o1, Lance o2) {
				if (o1.getValor() < o2.getValor()) {
					return 1;
				}
				if (o1.getValor() > o2.getValor()) {
					return -1;
				}
				return 0;
			}
		});

		maioresLances = maioresLances.subList(0, maioresLances.size() > 3 ? 3 : maioresLances.size());
	}

	public List<Lance> getMaioresLances() {
		return Collections.unmodifiableList(maioresLances);
	}

	public double getMaiorLance() {
		return maiorLance;
	}

	public double getMenorLance() {
		return menorLance;
	}

	public double getMediaDeValorDosLances() {
		return mediaDeValorDosLances;
	}

}
