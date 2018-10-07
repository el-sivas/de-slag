package de.slag.central;

import java.time.LocalDateTime;
import java.util.Date;

import de.slag.base.tools.DateUtils;

public class DawnConstants {
	
	public static final Date END_OF_DEKAMILLENIAL = DateUtils.toDate(LocalDateTime.of(9999, 12, 31, 23, 59, 59));
	
	public class Database {
		public static final int ORACLE_MAX_LENGTH_VARCHAR_2 = 4000;
	}

}
