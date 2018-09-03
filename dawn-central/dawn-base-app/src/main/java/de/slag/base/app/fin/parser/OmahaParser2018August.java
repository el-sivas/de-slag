package de.slag.base.app.fin.parser;

import java.time.LocalDate;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.slag.base.app.fin.ImportDataset;

public class OmahaParser2018August {

	private static final Log LOG = LogFactory.getLog(OmahaParser2018August.class);

	public static final Function<Document, Element> DOCUMENT_TO_TABLE = d -> {
		final Elements elementsByClass = d.getElementsByClass("table_box_content_zebra");
		if (elementsByClass.size() != 1) {
			throw new RuntimeException("expected 1");
		}
		return elementsByClass.first();
	};

	public static final Function<Element, Collection<Element>> TABLE_TO_ROWS = table -> {
		return table.getElementsByTag("tr")
				.stream()
				.filter(e -> !"ovbAdRow".equals(e.className()))
				.collect(Collectors.toList());
	};

	public static final Function<Element, ImportDataset> ROW_TO_DATASET = row -> {
		try {
			return internal(row);
		} catch (Throwable t) {
			LOG.error(row);
			throw t;
		}
	};

	private static ImportDataset internal(Element row) {
		Element col0 = row.child(0);
		Element child = col0.child(0);
		String href = child.attributes().get("href");
		String isin = href.substring(href.length() - Constants.ISIN_LENGHT, href.length());

		Element col4 = row.child(4);
		Element amountTag = col4.child(0);
		String amount = amountTag.text().replaceAll(",", ".");
		
		Element currencyTag = col4.child(1);
		String currency = currencyTag.text();
		

		return importDataset(Double.valueOf(amount), currency, LocalDate.now(), isin);
	}

	private static ImportDataset importDataset(Double price, String currency, LocalDate date, String isin) {
		return new ImportDataset() {

			@Override
			public Double getPrice() {
				return price;
			}

			@Override
			public String getIsin() {
				return isin;
			}

			@Override
			public LocalDate getDate() {
				return date;
			}

			@Override
			public String getCurrency() {
				return currency;
			}

			@Override
			public String toString() {
				return toLabel();
			}
		};
	}

}
