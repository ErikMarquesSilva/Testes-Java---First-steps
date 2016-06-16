package br.com.caelum.leilao.dominio.util;

public class AnoBissexto {

	public boolean ehBissexto(int ano) {
		boolean afirmativa = false;
		if (ehMultiploDe4(ano) && naoFazParteDosMultiplosDasCentenas(ano)) {
			afirmativa = true;
		}
		return afirmativa;
	}

	private boolean naoFazParteDosMultiplosDasCentenas(int ano) {
		return !(ehMultiploDe100(ano) && naoEhMultiploDe400(ano));
	}

	private boolean naoEhMultiploDe400(int ano) {
		return !(ano % 400 == 0);
	}

	private boolean ehMultiploDe100(int ano) {
		return ano % 100 == 0;
	}

	private boolean ehMultiploDe4(int ano) {
		return ano % 4 == 0;
	}
}
