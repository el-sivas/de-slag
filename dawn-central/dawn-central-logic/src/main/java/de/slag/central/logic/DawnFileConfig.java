package de.slag.central.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.Properties;

import de.slag.central.data.config.DawnConfig;

public class DawnFileConfig implements DawnConfig {

	private static final String DEFAULT_CONFIG_FILE = "slag-config.properties";

	private Properties properties;

	private DawnFileConfig(final Properties properties) {
		this.properties = properties;
	}

	@Override
	public Object getValue(String key) {
		return properties.get(key);
	}

	public static DawnConfig instantiating() {
		return instantiating(System.getProperty("user.home") + File.separator + DEFAULT_CONFIG_FILE);
	}

	private static DawnConfig instantiating(final String configFileName) {
		Objects.requireNonNull(configFileName, "config file not set");

		final Properties properties = new Properties();
		try (final FileInputStream fis = new FileInputStream(configFileName)) {
			properties.load(fis);
		} catch (IOException e) {
			throw new LogicException(e);
		}
		return new DawnFileConfig(properties);
	}

	@Override
	public Collection<Object> keys() {
		return properties.keySet();
	}

}
