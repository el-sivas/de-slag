package de.slag.base.tools.test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import de.slag.base.tools.CsvUtils;

public class CsvUtilsTest {

	private static final String C_TMP_TEST_CSV = "c:\\tmp\\test.csv";

	@Test
	public void test3() throws IOException {
		Collection<String> header = Arrays.asList("d", "e", "f");
		Collection<Collection<String>> lines = new ArrayList<>();
		lines.add(Arrays.asList("1", "2", "3"));
		lines.add(Arrays.asList("x", "", "z"));
		CsvUtils.write(C_TMP_TEST_CSV + ".out", header, lines);
	}

	@Test
	public void test() throws IOException {
		final Collection<CSVRecord> records = CsvUtils.getRecords(C_TMP_TEST_CSV, Arrays.asList("a", "b", "c"));
		for (CSVRecord csvRecord : records) {
			String lastName = csvRecord.get("a");
			String firstName = csvRecord.get("b");
			String third = csvRecord.get("c");
			System.out.println(lastName + "|" + firstName + "|" + third);
		}
	}

	@Test
	public void test2() throws IOException {
		final Collection<CSVRecord> records = CsvUtils.getRecords(C_TMP_TEST_CSV);
		for (CSVRecord csvRecord : records) {
			String lastName = csvRecord.get("a");
			String firstName = csvRecord.get("c");
			String third = csvRecord.get("b");
			System.out.println(lastName + "|" + firstName + "|" + third);
		}
	}
}
