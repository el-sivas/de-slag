package de.slag.base.app.fin.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.Test;

import de.slag.base.app.fin.ImportDataset;
import de.slag.base.app.fin.parser.OmahaParseUtils;
import de.slag.base.tools.CsvUtils;

public class HtmlTest {

	private static final String SEMICOLON = ";";
	private static final String BASE_DIR = "/home/sivas/dev/git/de-slag-dawn/dawn-central/dawn-base-app/src/test/resources/";
	private static final String IMPORT_DIR = "/home/sivas/Dropbox/invest/data-import/";
	
	@Test
	public void test2() throws IOException {
		byte[] readAllBytes = Files.readAllBytes(Paths.get(IMPORT_DIR + "mdax.html"));
		String string = new String(readAllBytes, "utf-8");
		Collection<ImportDataset> parse = OmahaParseUtils.parse(string);
		parse.forEach(System.out::println);
	
		final Collection<String> header = Arrays.asList("DATE","ISIN","PRICE","CURRENCY");
		final Collection<Collection<String>> lines = parse.stream().map(i -> Arrays.asList(i.getDate().toString(), i.getIsin(), i.getPrice().toString(), i.getCurrency())).collect(Collectors.toList());

		String filename = "mdax-raw.csv";
		CsvUtils.write(filename, header, lines);
	}

}
