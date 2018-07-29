package de.slag.central.data;

public interface DawnConfig {

	Object getValue(final String key);

	default <T> T getValue(final String key, Class<T> type) {
		return type.cast(getValue(key));
	}

}
