package de.slag.base.app.fin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.odftoolkit.simple.table.Cell;

import de.slag.base.tools.CliOptionsUtils;
import de.slag.base.tools.CsvUtils;
import de.slag.base.tools.Debug;
import de.slag.base.tools.odf.Ods;
import de.slag.base.tools.odf.Ods.DataSheet;

public class OpenDocumentIntegrationApp {

	private static final Log LOG = LogFactory.getLog(OpenDocumentIntegrationApp.class);

	private static final String CLI_OUTPUT = "o";
	private static final String CLI_INPUT = "i";
	private String pattern = "yy-MM-dd";

	public static void main(String[] args) throws Exception {
		Configurator.setLevel("de.slag", Level.DEBUG);
		final Options options = CliOptionsUtils.createOptions();
		options.addOption(CLI_INPUT, true, "input file (csv)");
		options.addOption(CLI_OUTPUT, true, "output file (ods)");

		final CommandLine cli = CliOptionsUtils.parse(options, args);

		final String inputFile = cli.getOptionValue(CLI_INPUT);
		final String outputFile = cli.getOptionValue(CLI_OUTPUT);

		Objects.requireNonNull(inputFile, "input file not setted");
		Objects.requireNonNull(outputFile, "output file not setted");

		Collection<String> header = Arrays.asList("DATE", "ISIN", "PRICE", "CURRENCY");
		new OpenDocumentIntegrationApp().integrate(header, inputFile, outputFile);
	}

	public void integrate(Collection<String> header, String csvFilename, String odsFilename) throws Exception {
	System.out.println("integrate...");
		try (final Ods ods = Ods.opening(odsFilename)) {
			integrate(ods.getDataSheet("input"), header, csvFilename);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}

		Debug.hurra();
	}

	private void integrate(DataSheet dataSheet, Collection<String> header, String csvFilename) throws IOException {
		System.out.println("determine isins...");
		final Collection<String> isins = determineIsins(dataSheet);
		System.out.println("get records...");
		final Collection<CSVRecord> records = CsvUtils.getRecords(csvFilename);
		final Map<String, Collection<CSVRecord>> map = new HashMap<>();
		isins.forEach(
				i -> map.put(i, records.stream().filter(r -> r.get("ISIN").equals(i)).collect(Collectors.toList())));
		final int rowCount = dataSheet.getTable().getRowCount();
		final int start = 1; // sic!
		System.out.println("los...");
		for (int rowNo = start; rowNo < rowCount; rowNo++) {
			final Cell isinCell = dataSheet.getDataCell(rowNo, "ISIN");
			final String isin = isinCell.getStringValue();
			if(StringUtils.isBlank(isin)) {
				break;
			}
			final Collection<CSVRecord> collection = map.get(isin);
			if (collection == null || collection.isEmpty()) {
				LOG.warn("no data for ISIN: " + isin);
				continue;
			}
			final ArrayList<CSVRecord> list = new ArrayList<>(collection);
			Collections.sort(list, new Comparator<CSVRecord>() {


				@Override
				public int compare(CSVRecord arg0, CSVRecord arg1) {
					try {
						final Date date1 = new SimpleDateFormat(pattern).parse(arg0.get("DATE"));
						final Date date2 = new SimpleDateFormat(pattern).parse(arg1.get("DATE"));
						return date1.compareTo(date2);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});
			final CSVRecord csvRecord = list.get(0);
			final Cell priceCell = dataSheet.getDataCell(rowNo, "PRICE");
			priceCell.setDoubleValue(Double.valueOf(csvRecord.get("PRICE")));
			
			final Cell currencyCell = dataSheet.getDataCell(rowNo, "CURRENCY");
			currencyCell.setStringValue(csvRecord.get("CURRENCY"));
			
			final Calendar instance = Calendar.getInstance();
			try {
				instance.setTime(new SimpleDateFormat(pattern).parse(csvRecord.get("DATE")));
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			dataSheet.getDataCell(rowNo, "DATE").setDateValue(instance);
		}

	}

	private Collection<String> determineIsins(DataSheet dataSheet) {
		final int rowCount = dataSheet.getTable().getRowCount();
		System.out.println("row count: " + rowCount);
		final Collection<Cell> cells = new ArrayList<>();
		final int start = 1;// sic!
		for (int i = start; i < rowCount; i++) {
			System.out.println("row: " + i);
			final Cell dataCell = dataSheet.getDataCell(i, "ISIN");
			if (StringUtils.isBlank(dataCell.getStringValue())) {
				break;
			}
			cells.add(dataCell);
		}
		return cells.stream().map(c -> c.getStringValue()).collect(Collectors.toList());
	}
}
