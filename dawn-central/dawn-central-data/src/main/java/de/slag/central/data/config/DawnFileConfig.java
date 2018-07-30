package de.slag.central.data.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import de.slag.central.data.DataException;

/**
 * Provides a singleton instance of a properties file. There are three ways to
 * use. Note: Per config file one instance will be created.
 * <p>
 * 1. Use method <b>instantiating()</b> without any configuration. The based
 * config file is <b>dawn-config.properties</b> in user home as default.
 * </p>
 * <p>
 * 2. Use method <b>instantiating()</b> with starting the JRE with system
 * parameter <b>-Ddawn-config-file</b>=<i>path/to/file</i> and this file will be
 * used.
 * </p>
 * <p>
 * 3. Use method <b>instantiating(String)</b> to use the given parameter as
 * location for properties.
 * </p>
 */
public class DawnFileConfig implements DawnConfig {

	private static final String DEFAULT_CONFIG_FILE = "dawn-config.properties";

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
		if (instance == null) {
			instance = instantiating();
		}
		return instance;
	}

	private static DawnConfig instantiating() {
		final String dawnConfigFile = System.getProperty("dawn-config-file");
		if (!StringUtils.isBlank(dawnConfigFile)) {
			return instantiating(dawnConfigFile);
		}
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
