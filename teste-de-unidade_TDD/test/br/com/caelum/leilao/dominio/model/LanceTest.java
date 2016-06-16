package br.com.caelum.leilao.dominio.model;

import org.junit.Test;

public class LanceTest {

	private Usuario usuario = new Usuario("Usuário");

	@Test(expected = IllegalArgumentException.class)
	public void naoAceitaLanceNegativo() {
		new Lance(usuario, -2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void naoAceitaValorIgualAZero() {
		new Lance(usuario, 0);
	}

	@Test
	public void aceitaLanceValido() {
		new Lance(usuario, 10);
	}

}
