package br.com.caelum.leilao.dominio.util.leilao;

import static br.com.caelum.leilao.dominio.model.DadosRestritivosDoLeilao.Limite_De_Lances_individuais;

import java.util.List;

import br.com.caelum.leilao.dominio.model.Lance;
import br.com.caelum.leilao.dominio.model.Usuario;

public class ValidadorDeLances {

	private List<Lance> lances;

	public ValidadorDeLances(List<Lance> lances) {
		this.lances = lances;
	}

	public boolean aceitarOLanceDo(Usuario usuario) {

		if (ehOPrimeiroLance() || (naoEhLanceEmSequenciaDoMesmo(usuario)
			&& limiteDeLancesNaoFoiExcedidoPelo(usuario))) {
			return true;
		}

		return false;
	}

	private boolean limiteDeLancesNaoFoiExcedidoPelo(Usuario usuario) {
		int totalDeLancesDoUsuario = 0;

		for (Lance lance : lances) {
			if (lance.getUsuario().equals(usuario)) {
				totalDeLancesDoUsuario++;
			}
		}

		return totalDeLancesDoUsuario < Limite_De_Lances_individuais.getValor();
	}

	private boolean naoEhLanceEmSequenciaDoMesmo(Usuario usuario) {
		return !usuario.equals(usuarioQueOfertouOUltimoLance());
	}

	private Usuario usuarioQueOfertouOUltimoLance() {
		return lances.get(this.lances.size() - 1).getUsuario();
	}

	private boolean ehOPrimeiroLance() {
		return lances.size() == 0;
	}
}
