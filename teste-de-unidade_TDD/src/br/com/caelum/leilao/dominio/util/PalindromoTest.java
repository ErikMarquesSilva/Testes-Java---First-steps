package br.com.caelum.leilao.dominio.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PalindromoTest {

	@Test
	public void testEhPalindromo() {

		Palindromo palindromo = new Palindromo();
		String fraseCerta = "Socorram-me subi no onibus em Marrocos";
		String fraseErrada = "teste";

		assertTrue(palindromo.ehPalindromo(fraseCerta));
		assertFalse(palindromo.ehPalindromo(fraseErrada));

	}

}
