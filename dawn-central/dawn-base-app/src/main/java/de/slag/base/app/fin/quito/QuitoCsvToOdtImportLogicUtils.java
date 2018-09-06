package de.slag.base.app.fin.quito;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.odftoolkit.simple.table.Cell;

import de.slag.base.tools.CsvUtils;
import de.slag.base.tools.DateUtils;
import de.slag.base.tools.odf.Ods;
import de.slag.base.tools.odf.Ods.DataSheet;

public class QuitoCsvToOdtImportLogicUtils {

	private static final Log LOG = LogFactory.getLog(QuitoCsvToOdtImportLogicUtils.class);

	private static final Comparator<QuitoCsvRecord> QUITO_CSV_RECORD_COMPARATOR = new Comparator<QuitoCsvRecord>() {

		@Override
		public int compare(QuitoCsvRecord o1, QuitoCsvRecord o2) {
			return o1.getDate().compareTo(o2.getDate());
		}
	};

	public static void importFile(String csvFilename, String odsFilename) throws IOException {
		final Collection<CSVRecord> records = CsvUtils.getRecords(csvFilename);

		final List<QuitoCsvRecord> quitoRecords = records.stream().map(r -> QuitoCsvRecord.create(r))
				.collect(Collectors.toList());

		final Map<String, Collection<QuitoCsvRecord>> isinMappedRecords = new HashMap<>();
		quitoRecords.forEach(r -> addToIsinMap(r, isinMappedRecords));

		try (Ods ods = Ods.opening(odsFilename)) {
			final DataSheet sheet = ods.getDataSheet("import");
			importData(isinMappedRecords, sheet);
		}
	}

	private static void importData(Map<String, Collection<QuitoCsvRecord>> isinMappedRecords, DataSheet sheet) {

		final int controlVariable = 1; // sic!Configurator.setLevel("de.slag", Level.INFO);
		for (int i = controlVariable; true; i++) {
			final Cell isinCell = sheet.getDataCell(i, QuitoConstants.ISIN);
			final String isin = isinCell.getStringValue();

			if (StringUtils.isBlank(isin)) {
				break;
			}

			if (!isinMappedRecords.containsKey(isin)) {
				throw new QuitoLogicException("csv records are not containing isin: " + isin);
			}

			final QuitoCsvRecord quitoCsvRecord = determineNewestRecord(isinMappedRecords.get(isin));
			if (quitoCsvRecord == null) {
				throw new QuitoLogicException("no record found, isin: " + isin);
			}

			final Date date = DateUtils.toDate(quitoCsvRecord.getDate());
			final Double price = quitoCsvRecord.getPrice();
			final String currency = quitoCsvRecord.getCurrency();

			final String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
			LOG.info(isin + ": " + price + ", " + currency + ", " + formattedDate);

			sheet.getDataCell(i, QuitoConstants.DATE).setStringValue(formattedDate);
			sheet.getDataCell(i, QuitoConstants.PRICE).setDoubleValue(price);
			sheet.getDataCell(i, QuitoConstants.CURRENCY).setStringValue(currency);
		}
	}

	private static QuitoCsvRecord determineNewestRecord(final Collection<QuitoCsvRecord> collection) {
		final List<QuitoCsvRecord> sorted = new ArrayList<>(collection);
		Collections.sort(sorted, QUITO_CSV_RECORD_COMPARATOR.reversed());
		return sorted.get(0);
	}

	private static void addToIsinMap(QuitoCsvRecord record, Map<String, Collection<QuitoCsvRecord>> isinMappedRecords) {
		final String isin = record.getIsin();
		if (!isinMappedRecords.containsKey(isin)) {
			isinMappedRecords.put(isin, new ArrayList<>());
		}
		isinMappedRecords.get(isin).add(record);
	}
}
