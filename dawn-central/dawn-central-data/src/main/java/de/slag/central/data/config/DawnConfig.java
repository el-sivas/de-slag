package de.slag.central.data.config;

public interface DawnConfig {

	String HIBERNATE_DIALECT = "hibernate.dialect";
	String HIBERNATE_CONNECTION_PASSWORD = "hibernate.connection.password";
	String HIBERNATE_CONNECTION_USERNAME = "hibernate.connection.username";
	String HIBERNATE_CONNECTION_URL = "hibernate.connection.url";
	String HIBERNATE_CONNECTION_DRIVER_CLASS = "hibernate.connection.driver_class";

	Object getValue(final String key);

	default <T> T getValue(final String key, Class<T> type) {
		return type.cast(getValue(key));
	}

	default <T> T getValue(final String key, Class<T> type, T defaultValue) {
		final T value = getValue(key, type);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	default String getStringValue(final String key) {
		return getValue(key, String.class);
	}

}
