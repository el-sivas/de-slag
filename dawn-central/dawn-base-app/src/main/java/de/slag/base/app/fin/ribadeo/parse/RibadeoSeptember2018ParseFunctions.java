package de.slag.base.app.fin.ribadeo.parse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.slag.base.app.fin.FinLogicExecption;

public class RibadeoSeptember2018ParseFunctions {

	public static final Function<Document, Date> DOCUMENT_TO_TODAYS_DATE = d -> {
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

	public static final Function<Element, String> DOCUMENT_TO_ISIN = d -> {
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

	public static final Function<Document, Double> DOCUMENT_TO_PRICE = doc -> {
		final Element tableGer = getUniqueElementByClass(doc, "GER");
		final Element feldBetrag = tableGer.child(2);
		final String text = feldBetrag.text();
		return Double.valueOf(text.split(" ")[0].replaceAll(",", "."));

	};

	public static final Function<Document, String> DOCUMENT_TO_CURRENCY = d -> {
		final Element uniqueElementByClass = getUniqueElementByClass(d, "GER");
		final Element feldBetrag = uniqueElementByClass.child(2);
		return feldBetrag.text().split(" ")[1];
	};

	private static Element getUniqueElementByClass(Document doc, String className) {
		final Elements elements = doc.getElementsByClass(className);
		if (elements.size() != 1) {
			throw new FinLogicExecption("elements expected: 1, current: " + elements.size());
		}
		return elements.first();
	}
}
