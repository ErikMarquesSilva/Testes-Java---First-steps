package br.com.caelum.leilao.dominio.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.caelum.leilao.dominio.util.leilao.ValidadorDeLances;

public class Leilao {

	private String descricao;
	private List<Lance> lances;

	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}

	public void propor(Lance lance) {
		Usuario usuario = lance.getUsuario();
		ValidadorDeLances validador = new ValidadorDeLances(lances);

		if (validador.aceitarOLanceDo(usuario)) {
			lances.add(lance);
		}

	}

	public void dobrarLanceDo(Usuario usuario) {
		Lance lanceASerDobrado = ultimoLanceDo(usuario);

		if (lanceASerDobrado != null) {
			Lance lanceDobrado = new Lance(lanceASerDobrado.getUsuario(), lanceASerDobrado.getValor() * 2);
			propor(lanceDobrado);
		}
	}

	private Lance ultimoLanceDo(Usuario usuario) {
		Lance lanceASerDobrado = null;

		for (Lance lance : lances) {
			if (lance.getUsuario().equals(usuario)) {
				lanceASerDobrado = lance;
			}
		}

		return lanceASerDobrado;
	}

}
