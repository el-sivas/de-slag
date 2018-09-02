package de.slag.base.app.fin.parser;

import java.util.Collection;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import de.slag.base.app.fin.ImportDataset;

public class OmahaParseUtils {

	public static Collection<ImportDataset> parse(String html) {
		Document parse = Jsoup.parse(html);
		Element table = OmahaParser2018August.DOCUMENT_TO_TABLE.apply(parse);
		Collection<Element> apply = OmahaParser2018August.TABLE_TO_ROWS.apply(table);
		return apply.stream().map(OmahaParser2018August.ROW_TO_DATASET).collect(Collectors.toList());
	}

}
