package de.slag.base.app.fin.domain.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;

import de.slag.base.app.fin.FinLogicExecption;
import de.slag.base.app.fin.domain.model.StockPrice;
import de.slag.base.tools.CsvUtils;
import de.slag.base.tools.DateUtils;

public class DataDefault {

	private static final String PRICE = "PRICE";

	private static final String ISIN = "ISIN";

	private static final String CURRENCY = "CURRENCY";

	private static final String DATE = "DATE";

	private static final Collection<String> HEADER = Arrays.asList(DATE, ISIN, PRICE, CURRENCY);

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private static final Function<CSVRecord, StockPrice> CSV_RECORD_TO_STOCK_PRICE = record -> {
		final StockPrice stockPrice = new StockPrice();

		final String string = record.get(DATE);
		final Date parse;
		try {
			parse = new SimpleDateFormat(DATE_FORMAT).parse(string);
		} catch (ParseException e) {
			throw new FinLogicExecption(e);
		}
		stockPrice.setDate(DateUtils.toLocalDate(parse));

		stockPrice.setPrice(BigDecimal.valueOf(Double.valueOf(record.get(PRICE))));

		stockPrice.setIsin(record.get(ISIN));
		stockPrice.setCurrency(record.get(CURRENCY));
		return stockPrice;
	};

	protected static final Function<StockPrice, Collection<String>> STOCK_PRICE_TO_LINE = stockPrice -> {
		final String date = new SimpleDateFormat(DATE_FORMAT).format(DateUtils.toDate(stockPrice.getDate()));
		final String isin = stockPrice.getIsin();
		final String price = Double.toString(stockPrice.getPrice().doubleValue());
		final String currency = stockPrice.getCurrency();

		return Arrays.asList(date, isin, price, currency);
	};

	public static StockPriceReader defaultReader() {
		return new StockPriceReader() {

			@Override
			public Collection<StockPrice> apply(final String filename) {
				final List<CSVRecord> records = new ArrayList<>();
				try {
					records.addAll(CsvUtils.getRecords(filename, HEADER));
				} catch (IOException e) {
					throw new FinLogicExecption(e);
				}
				if (records.isEmpty()) {
					return Collections.emptyList();
				}
				final List<CSVRecord> recordsWithoutHeadline = records.subList(1, records.size());
				return recordsWithoutHeadline.stream().map(CSV_RECORD_TO_STOCK_PRICE).collect(Collectors.toList());
			}

		};
	}

	public static StockPriceWriter defaultWriter() {
		return new StockPriceWriter() {

			@Override
			public void accept(Collection<StockPrice> stockPrices, Path path) {
				Collection<Collection<String>> lines = stockPrices.stream().map(STOCK_PRICE_TO_LINE)
						.collect(Collectors.toList());
				try {
					CsvUtils.write(HEADER, lines, path);
				} catch (IOException e) {
					throw new FinLogicExecption(e);
				}
			}
		};
	}
}
