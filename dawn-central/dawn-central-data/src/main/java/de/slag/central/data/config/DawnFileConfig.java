package de.slag.central.data.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import de.slag.central.data.DataException;


public class DawnFileConfig implements DawnConfig {

	private static final String DEFAULT_CONFIG_FILE = "slag-config.properties";
	
	private static DawnConfig instance;

	private Properties properties;

	private DawnFileConfig(final Properties properties) {
		this.properties = properties;
	}

	@Override
	public Object getValue(String key) {
		return properties.get(key);
	}
	
	public static DawnConfig instance() {
		if(instance == null) {
			instance = instantiating();
		}
		return instance;
	}

	private static DawnConfig instantiating() {
		return instantiating(System.getProperty("user.home") + File.separator + DEFAULT_CONFIG_FILE);
	}

	private static DawnConfig instantiating(final String configFileName) {
		Objects.requireNonNull(configFileName, "config file not set");

		final Properties properties = new Properties();
		try (final FileInputStream fis = new FileInputStream(configFileName)) {
			properties.load(fis);
		} catch (IOException e) {
			throw new DataException(e);
		}
		return new DawnFileConfig(properties);
	}

}
