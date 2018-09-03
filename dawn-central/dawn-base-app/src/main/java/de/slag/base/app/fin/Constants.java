package de.slag.base.app.fin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface Constants {

	String COL_INDEX = "INDEX";
String COL_COMPANY = "COMPANY";
	String COL_CURRENCY = "CURRENCY";
	String COL_PRICE = "PRICE";
	String COL_ISIN = "ISIN";
	String COL_DATE = "DATE";
	String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	
	List<String> HEADER = Collections
			.unmodifiableList(Arrays.asList(COL_DATE, COL_ISIN, COL_PRICE, COL_CURRENCY, COL_COMPANY, COL_INDEX));

}
