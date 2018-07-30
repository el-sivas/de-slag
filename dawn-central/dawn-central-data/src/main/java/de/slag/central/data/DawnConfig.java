package de.slag.central.data;

public interface DawnConfig {

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
