package de.slag.base.app.fin.palermo.parse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.slag.base.app.fin.Constants;
import de.slag.base.tools.CsvUtils;

public class Parser {

	public static void toCsv(String inFilename, String outFilename) throws IOException {
		final Document document = document(inFilename);
		Element table = table(document);
		Collection<Element> rows = rows(table);
		Collection<Collection<String>> values = values(rows);
		CsvUtils.write(outFilename, Constants.HEADER, values);
	}

	private static Collection<Collection<String>> values(Collection<Element> rows) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Collection<Element> rows(Element table) {
		return ((Function<Element, Collection<Element>>) t -> {
			return t.getElementsByTag("tr").stream().filter(e -> !"ovbAdRow".equals(e.className()))
					.collect(Collectors.toList());
		}).apply(table);
	}

	private static Element table(Document document) {
		return ((Function<Document, Element>) d -> {
			final Elements elementsByClass = d.getElementsByClass("table_box_content_zebra");
			if (elementsByClass.size() != 1) {
				throw new RuntimeException("expected 1");
			}
			return elementsByClass.first();
		}).apply(document);
	}

	private static Document document(String inFilename) throws IOException {
		return Jsoup.parse(new String(Files.readAllBytes(Paths.get(inFilename))));
	}
}
