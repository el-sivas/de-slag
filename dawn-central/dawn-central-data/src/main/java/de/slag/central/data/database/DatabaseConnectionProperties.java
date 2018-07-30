package de.slag.central.data.database;

public class DatabaseConnectionProperties {

	private String dbUser;

	private String dbPassword;

	private DatabaseDialect dbDialect;

	private DatabaseDriverClass dbDriver;

	private String dbUrl;
	
	private DatabaseConnectionProperties() {
		
	}

	public static DatabaseConnectionProperties creating(String user, String pw, DatabaseDialect dialect,
			DatabaseDriverClass driverClass, String url) {
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

	public DatabaseDialect getDbDialect() {
		return dbDialect;
	}

	public DatabaseDriverClass getDbDriver() {
		return dbDriver;
	}

	public String getDbUrl() {
		return dbUrl;
	}

}
