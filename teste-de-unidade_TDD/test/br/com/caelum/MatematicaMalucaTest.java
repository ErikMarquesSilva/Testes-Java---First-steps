package br.com.caelum;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MatematicaMalucaTest {

	private MatematicaMaluca matematicaMaluca = new MatematicaMaluca();

	@Test
	public void testContaMalucaComNumeroMaior30() {
		assertEquals(160, contaMalucaCom(40));
	}

	@Test
	public void testContaMalucaComNumeroIgual30() {
		assertEquals(90, contaMalucaCom(30));
	}

	@Test
	public void testContaMalucaComNumeroIgual10() {
		assertEquals(20, contaMalucaCom(10));
	}

	private int contaMalucaCom(int numero) {
		return matematicaMaluca.contaMaluca(numero);
	}

}
