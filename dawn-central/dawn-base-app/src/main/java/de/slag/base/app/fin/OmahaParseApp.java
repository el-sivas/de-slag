package de.slag.base.app.fin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.base.app.fin.parser.OmahaParseUtils;
import de.slag.base.tools.CsvUtils;

public class OmahaParseApp {

	private static final Log LOG = LogFactory.getLog(OmahaParseApp.class);

	private static final String VERSION = "0.2";

	private static final Function<ImportDataset, List<String>> TO_CSV_LINE = i -> Arrays.asList(i.getDate().toString(),
			i.getIsin(), i.getPrice().toString(), i.getCurrency());

	public static void main(String[] args) throws IOException {
		LOG.info("Version: " + VERSION);
		LOG.info("read .html");
		byte[] readAllBytes = Files.readAllBytes(Paths.get("mdax.html"));
		final String string = new String(readAllBytes, "utf-8");

		LOG.info("parse to ImportDataset...");
		final Collection<ImportDataset> parse = OmahaParseUtils.parse(string);

		LOG.info("write to .csv");
		final Collection<Collection<String>> lines = parse.stream().map(TO_CSV_LINE).collect(Collectors.toList());
		final Collection<String> header = Arrays.asList("DATE", "ISIN", "PRICE", "CURRENCY");

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		CsvUtils.write("mdax-raw.csv", header, lines);
		LOG.info("all done!");
	}

}
