package de.slag.base.app.fin.ribadeo.parse;

import java.time.LocalDate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import de.slag.base.tools.DateUtils;

public class RibadeoParseLogicUtils {

	public static RibadeoImportHolder parse(String html) {
		final Document document = Jsoup.parse(html);
		return new RibadeoImportHolder() {

			@Override
			public Double getPrice() {
				final Element priceTable = RibadeoSeptember2018ParseFunctions.DOCUMENT_TO_PRICE_TABLE.apply(document);

				return RibadeoSeptember2018ParseFunctions.PRICE_TABLE_TO_PRICE.apply(priceTable);
			}

			@Override
			public String getIsin() {
				return RibadeoSeptember2018ParseFunctions.DOCUMENT_TO_ISIN.apply(document);
			}

			@Override
			public LocalDate getDate() {
				return DateUtils
						.toLocalDate(RibadeoSeptember2018ParseFunctions.DOCUMENT_TO_TODAYS_DATE.apply(document));
			}

			@Override
			public String getCurrency() {
				final Element priceTable = RibadeoSeptember2018ParseFunctions.DOCUMENT_TO_PRICE_TABLE.apply(document);
				return RibadeoSeptember2018ParseFunctions.PRICE_TABLE_TO_CURRENCY.apply(priceTable);
			}

			@Override
			public String toString() {
				return toLabel();
			}
		};
	}

}
