package de.slag.base.app.fin.ribadeo.parse;

import java.time.LocalDate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import de.slag.base.tools.DateUtils;

public class RibadeoParseLogicUtils {

	public static RibadeoImportHolder parseAktie(String html) {
		final Document document = Jsoup.parse(html);
		return new RibadeoImportHolder() {

			@Override
			public Double getPrice() {
				return RibadeoSeptember2018ParseFunctions.DOCUMENT_TO_PRICE.apply(document);
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
				return RibadeoSeptember2018ParseFunctions.DOCUMENT_TO_CURRENCY.apply(document);
			}

			@Override
			public String toString() {
				return toLabel();
			}
		};
	}

}
