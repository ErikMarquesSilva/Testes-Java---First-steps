package br.com.caelum.leilao.dominio.model;

public enum DadosRestritivosDoLeilao {

	Limite_De_Lances_individuais(5);

	private int limite;

	private DadosRestritivosDoLeilao(int limite) {
		this.limite = limite;
	}

	public int getValor() {
		return limite;
	}
}
