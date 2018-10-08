package de.slag.central.data.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.base.BaseException;
import de.slag.central.data.impl.DataException;

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

	private static final Log LOG = LogFactory.getLog(DawnFileConfig.class);

	private static final String DEFAULT_CONFIG_FILE = "dawn-config.properties";

	private static DawnConfig instance;

	private Properties properties;

	private DawnFileConfig(final Properties properties) {
		this.properties = properties;
	}

	@Override
	public Object getValue(String key) {
		final Object object = properties.get(key);
		LOG.info("property: '" + key + "', value: '" + object + "'");
		return object;
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

		if (!new File(configFileName).exists()) {
			throw new BaseException("no config file found at: '" + configFileName + "'");
		}

		final Properties properties = new Properties();
		try (final FileInputStream fis = new FileInputStream(configFileName)) {
			properties.load(fis);
		} catch (IOException e) {
			throw new DataException(e);
		}
		return new DawnFileConfig(properties);
	}

	@Override
	public Collection<Object> keys() {
		return Collections.unmodifiableCollection(properties.keySet());
	}

}
