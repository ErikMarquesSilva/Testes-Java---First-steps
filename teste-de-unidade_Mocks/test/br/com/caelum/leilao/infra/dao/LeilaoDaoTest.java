package br.com.caelum.leilao.infra.dao;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class LeilaoDaoTest {

	@Test
	public void testTeste() {

		mock(LeilaoDao.class);
		when(LeilaoDao.teste()).thenReturn("Oi");

		String teste = LeilaoDao.teste();
		assertThat(teste, equalTo("Oi"));
	}

}
