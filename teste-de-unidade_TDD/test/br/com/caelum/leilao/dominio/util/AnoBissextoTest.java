package br.com.caelum.leilao.dominio.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AnoBissextoTest {

	@Test
	public void ehBissexto() {

		AnoBissexto anoBissexto = new AnoBissexto();
		boolean ehBissexto = anoBissexto.ehBissexto(2016);

		assertTrue(ehBissexto);
	}

	@Test
	public void naoEhBissexto() {

		AnoBissexto anoBissexto = new AnoBissexto();
		boolean ehBissexto = anoBissexto.ehBissexto(1995);

		assertFalse(ehBissexto);
	}

}
