package de.slag.base.app.fin.play;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.base.app.fin.Constants;
import de.slag.base.app.fin.ImportDataset;
import de.slag.base.tools.DateUtils;

public class ImportDatasetImpl implements ImportDataset {

	private static final Log LOG = LogFactory.getLog(ImportDatasetImpl.class);

	private final List<String> header;

	private final List<String> values;

	public ImportDatasetImpl(List<String> header) {
		Set<String> set = new TreeSet<>(header);
		if (set.size() != header.size()) {
			throw new RuntimeException("double header entrys: " + header);
		}
		this.header = header;
		this.values = new ArrayList<>(header.size());
	}

	public void setData(CSVRecord csvRecord) {
		header.forEach(h -> setValue(h, value(csvRecord, h)));
	}

	private String value(CSVRecord csvRecord, String h) {
		try {
			return csvRecord.get(h);
		} catch (IllegalStateException | IllegalArgumentException e) {
			LOG.debug("no value in record for: " + h);
		}
		return null;
	}

	public List<String> toValues() {
		return Collections.unmodifiableList(values);
	}

	public String getValue(String colName) {
		return values.get(header.indexOf(colName));
	}

	public void setValue(String colName, String value) {
		values.set(header.indexOf(colName), value);
	}

	public String getIsin() {
		return getValue(Constants.COL_ISIN);
	}

	public LocalDate getDate() {
		try {
			return DateUtils
					.toLocalDate(new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT).parse(getValue(Constants.COL_DATE)));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public Double getPrice() {
		return Double.valueOf(getValue(Constants.COL_PRICE));
	}

	public String getCurrency() {
		return getValue(Constants.COL_CURRENCY);
	}
}
