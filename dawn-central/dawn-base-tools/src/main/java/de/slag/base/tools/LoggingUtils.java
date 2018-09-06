package de.slag.base.tools;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

public class LoggingUtils {
	
	public static void activateLogging() {
		activateLogging(Level.INFO, "de.slag");
	}

	public static void activateLogging(final Level info, final String loggerName) {
		Configurator.setLevel(loggerName, info);
	}
	

	
	

}
