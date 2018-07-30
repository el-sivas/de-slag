package de.slag.central.data.database;

import de.slag.central.data.config.DawnConfig;

public class DatabaseConnectionProperties {

	private String dbUser;

	private String dbPassword;

	private String dbDialect;

	private String dbDriver;

	private String dbUrl;

	private DatabaseConnectionProperties() {

	}

	public static DatabaseConnectionProperties creating(DawnConfig config) {
		String user = config.getStringValue(DawnConfig.HIBERNATE_CONNECTION_USERNAME);
		String pw = config.getStringValue(DawnConfig.HIBERNATE_CONNECTION_PASSWORD);
		String dialect = config.getStringValue(DawnConfig.HIBERNATE_DIALECT);
		String driverClass = config.getStringValue(DawnConfig.HIBERNATE_CONNECTION_DRIVER_CLASS);
		String url = config.getStringValue(DawnConfig.HIBERNATE_CONNECTION_URL);
		return creating(user, pw, dialect, driverClass, url);
	}

	public static DatabaseConnectionProperties creating(String user, String pw, String dialect,
			String driverClass, String url) {
		final DatabaseConnectionProperties properties = new DatabaseConnectionProperties();
		properties.dbUser = user;
		properties.dbPassword = pw;
		properties.dbDialect = dialect;
		properties.dbDriver = driverClass;
		properties.dbUrl = url;
		return properties;
	}

	public String getDbUser() {
		return dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public String getDbDialect() {
		return dbDialect;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public String getDbUrl() {
		return dbUrl;
	}

}
