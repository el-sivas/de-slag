package de.slag.base.app.fin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import de.slag.base.tools.DateUtils;

public interface ImportDataset {

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final int COL_DATE = 0;
	public static final int COL_ISIN = 1;
	public static final int COL_PRICE = 2;
	public static final int COL_CURRENCY = 3;

	String getIsin();

	LocalDate getDate();

	Double getPrice();

	String getCurrency();

	default String toLabel() {
		return getDate() + " " + getIsin() + " " + getPrice() + " " + getCurrency();
	}

	static Collection<String> deflat(ImportDataset importDataset) {
		return Arrays.asList(
				new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(DateUtils.toDate(importDataset.getDate())),
				importDataset.getIsin(), importDataset.getPrice().toString(), importDataset.getCurrency());
	}

	static ImportDataset creating(CSVRecord record) {
		return new ImportDataset() {

			@Override
			public Double getPrice() {
				return Double.valueOf(record.get(COL_PRICE));
			}

			@Override
			public String getIsin() {
				return record.get(COL_ISIN);
			}

			@Override
			public LocalDate getDate() {
				return parse(record.get(COL_DATE));
			}

			@Override
			public String getCurrency() {
				return record.get(COL_CURRENCY);
			}
		};
	}

	static LocalDate parse(String asString) {
		try {
			return DateUtils.toLocalDate(new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(asString));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	static ImportDataset creating(Collection<String> asCollection) {
		final List<String> asList = new ArrayList<>(asCollection);
		return new ImportDataset() {

			@Override
			public Double getPrice() {
				return Double.valueOf(asList.get(COL_PRICE));
			}

			@Override
			public String getIsin() {
				return asList.get(COL_ISIN);
			}

			@Override
			public LocalDate getDate() {
				return parse(asList.get(COL_DATE));
			}

			@Override
			public String getCurrency() {
				return asList.get(COL_CURRENCY);
			}
		};
	}

}
