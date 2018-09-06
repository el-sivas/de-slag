package de.slag.base.app.fin.quito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.apache.commons.csv.CSVRecord;

import de.slag.base.tools.DateUtils;

public interface QuitoCsvRecord {

	String getCompany();

	String getIsin();

	Double getPrice();

	String getCurrency();

	LocalDate getDate();

	static QuitoCsvRecord create(CSVRecord record) {
		return new QuitoCsvRecord() {
			
			@Override
			public Double getPrice() {
				return Double.valueOf(record.get(QuitoConstants.PRICE));
			}
			
			@Override
			public String getIsin() {
				return record.get(QuitoConstants.ISIN);
			}
			
			@Override
			public LocalDate getDate() {
				try {
					return DateUtils.toLocalDate(new SimpleDateFormat("yyyy-MM-dd").parse(record.get(QuitoConstants.DATE)));
				} catch (ParseException e) {
					throw new QuitoLogicException(e);
				}
			}
			
			@Override
			public String getCurrency() {
				return record.get(QuitoConstants.CURRENCY);
			}
			
			@Override
			public String getCompany() {
				return null;
			}
			
		};
	}
}
