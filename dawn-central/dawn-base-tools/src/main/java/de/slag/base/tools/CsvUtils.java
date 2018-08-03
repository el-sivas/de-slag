package de.slag.base.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CsvUtils {

	private static final Log LOG = LogFactory.getLog(CsvUtils.class);

	public static void write(final String filename, Collection<String> header, Collection<Collection<String>> lines)
			throws IOException {
		final BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename));
		final CSVFormat format = CSVFormat.newFormat(';').withHeader(header.toArray(new String[0]))
				.withRecordSeparator("\r\n");
		final CSVPrinter csvPrinter = new CSVPrinter(writer, format);
		for (Collection<String> collection : lines) {
			csvPrinter.printRecord(collection);
		}
		csvPrinter.flush();
		csvPrinter.close();

	}

	public static Collection<CSVRecord> getRecords(final String filename, Collection<String> header)
			throws IOException {
		return getRecords(filename, header.toArray(new String[0]));
	}

	public static Collection<CSVRecord> getRecords(final String filename) throws IOException {
		final BufferedReader in = Files.newBufferedReader(Paths.get(filename));
		final CSVFormat format = CSVFormat.newFormat(';').withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim();
		final CSVParser parse = format.parse(in);
		return parse.getRecords();
	}

	public static Collection<CSVRecord> getRecords(final String filename, String... header) throws IOException {
		final BufferedReader in = Files.newBufferedReader(Paths.get(filename));
		final CSVFormat format = CSVFormat.newFormat(';').withHeader(header);
		final CSVParser parse = format.parse(in);
		final List<CSVRecord> records = parse.getRecords();
		validate(records, header);
		return records;
	}

	public static void validate(final List<CSVRecord> records, String... header) {
		final Collection<String> s = new ArrayList<>();
		for (int record = 0; record < records.size(); record++) {
			for (int col = 0; col < header.length; col++) {
				final String columnName = header[col];
				final CSVRecord csvRecord = records.get(record);
				try {
					csvRecord.get(columnName);
				} catch (IllegalArgumentException e) {
					LOG.debug(e);
					s.add("record: " + record + ", col: " + col + ": " + e.getMessage());
				}
			}
		}
		if (s.isEmpty()) {
			return;
		}
		throw new RuntimeException(String.join("\n", s));
	}
}
