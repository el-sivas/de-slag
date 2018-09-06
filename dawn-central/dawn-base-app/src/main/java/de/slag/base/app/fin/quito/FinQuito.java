package de.slag.base.app.fin.quito;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.base.tools.CliOptionsUtils;
import de.slag.base.tools.LoggingUtils;

public class FinQuito {
	
	private static final Log LOG = LogFactory.getLog(FinQuito.class);

	private static final String ODS_SINK = "s";
	private static final String CSV_SOURCE = "c";

	public static void main(String[] args) {
		LoggingUtils.activateLogging();		

		final Options options = CliOptionsUtils.createOptions();
		options.addOption(CSV_SOURCE, true, ".csv source");
		options.addOption(ODS_SINK, true, ".ods sink");

		final CommandLine commandLine = parse(args, options);
		if (commandLine.hasOption(CliOptionsUtils.HELP)) {
			LOG.info("help called");
			CliOptionsUtils.printHelpAndExit(options);
		}

		final String csvSource = commandLine.getOptionValue(CSV_SOURCE);
		if (StringUtils.isBlank(csvSource)) {
			throw new QuitoLogicException("csv source not setted");
		}
		final String odsSink = commandLine.getOptionValue(ODS_SINK);
		if (StringUtils.isBlank(odsSink)) {
			throw new QuitoLogicException("ods sink not setted");
		}
		importFile(csvSource, odsSink);
	}

	private static void importFile(final String csvSource, final String odsSink) {
		try {
			QuitoCsvToOdtImportLogicUtils.importFile(csvSource, odsSink);
		} catch (IOException e) {
			throw new QuitoLogicException(e);
		}
	}

	private static CommandLine parse(String[] args, final Options options) {
		try {
			return CliOptionsUtils.parse(options, args);
		} catch (ParseException e) {
			throw new QuitoLogicException(e);
		}
	}
}
