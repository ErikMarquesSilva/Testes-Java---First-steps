package br.com.caelum.leilao.dominio.util.leilao.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import br.com.caelum.leilao.dominio.model.Lance;
import br.com.caelum.leilao.dominio.model.Leilao;

public class LeilaoMatcher extends TypeSafeMatcher<Leilao> {

	private final Lance lance;

	public LeilaoMatcher(Lance lance) {
		this.lance = lance;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("leilao com lance " + lance.getValor());
	}

	@Override
	protected boolean matchesSafely(Leilao leilao) {
		return leilao.getLances().contains(lance);
	}

	public static Matcher<Leilao> temO(Lance lance) {
		return new LeilaoMatcher(lance);
	}

}
