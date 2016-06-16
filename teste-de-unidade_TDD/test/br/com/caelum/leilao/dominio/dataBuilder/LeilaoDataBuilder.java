package br.com.caelum.leilao.dominio.dataBuilder;

import br.com.caelum.leilao.dominio.model.Lance;
import br.com.caelum.leilao.dominio.model.Leilao;

public class LeilaoDataBuilder {

	private Leilao leilao;

	public LeilaoDataBuilder para(String descricao) {
		this.leilao = new Leilao(descricao);
		return this;
	}

	public LeilaoDataBuilder com(Lance lance) {
		this.leilao.propor(lance);
		return this;
	}

	public Leilao construir() {
		return this.leilao;
	}

}
