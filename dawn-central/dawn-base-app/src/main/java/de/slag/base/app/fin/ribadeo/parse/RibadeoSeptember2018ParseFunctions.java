package de.slag.base.app.fin.ribadeo.parse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.slag.base.app.fin.FinLogicExecption;

public class RibadeoSeptember2018ParseFunctions {

	public static final Function<Document, Element> DOCUMENT_TO_PRICE_TABLE = d -> {
		return getUniqueElementByClass(d, "GER");
	};

	public static final Function<Element, Double> PRICE_TABLE_TO_PRICE = t -> {
		String s = betragsfeld(t)[0];
		return Double.valueOf(s.replaceAll(",", "."));
	};

	public static final Function<Element, String> PRICE_TABLE_TO_CURRENCY = t -> {
		return betragsfeld(t)[1];
	};

	private static Element getUniqueElementByClass(Document doc, String className) {
		final Elements elements = doc.getElementsByClass(className);
		if (elements.size() != 1) {
			throw new FinLogicExecption("elements expected: 1, current: " + elements.size());
		}
		return elements.first();
	}

	public static Function<Document, Date> DOCUMENT_TO_TODAYS_DATE = d -> {
		final Element tableGer = getUniqueElementByClass(d, "GER");
		final Element dateField = tableGer.child(5);
		final Element dateTag = dateField.child(0);
		final String dateTimeAttribute = dateTag.attr("datetime");
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(dateTimeAttribute);
		} catch (ParseException e) {
			throw new FinLogicExecption(e);
		}

	};

	public static Function<Element, String> DOCUMENT_TO_ISIN = d -> {
		final Elements elements = d.getElementsByClass("STAMMDATEN");
		if (elements.size() != 1) {
			throw new FinLogicExecption("elements expected: 1, current: " + elements.size());
		}

		final Element stammdaten = elements.first();
		final Element hasBackground = stammdaten.getAllElements().get(2);
		final Element dl = hasBackground.getAllElements().get(1);
		final Element isinField = dl.getAllElements().get(4);
		return isinField.text();
	};

	// TODO refactoring
	private static String[] betragsfeld(Element t) {
		final Elements elements = t.getElementsByClass("ZAHL POSITIV");
		Predicate<Element> p = e -> e.text().contains(" ");

		final List<Element> collect = elements.stream().filter(p).collect(Collectors.toList());
		if (collect.size() != 1) {
			throw new FinLogicExecption("elements expected: 1, current: " + collect.size());
		}
		final Element element = collect.get(0);
		final String text = element.text();
		final String[] split = text.split(" ");
		return split;
	}
}
