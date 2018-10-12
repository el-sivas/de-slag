package de.slag.base.app.fin;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.base.tools.CliOptionsUtils;
import de.slag.base.tools.CsvUtils;
import de.slag.base.tools.reflection.ReflectionUtils;

public class DataImportApp {

	private static final Log LOG = LogFactory.getLog(DataImportApp.class);
	
	private static final String VERSION = "0.1";

	private static final List<String> HEADER = Arrays.asList("DATE", "ISIN", "PRICE", "CURRENCY");
	private static final String CLI_OUTPUT = "o";
	private static final String CLI_INPUT = "i";

	public static void main(String[] args) throws ParseException, IOException {
		LOG.info("Version: " + VERSION);

		final Options options = CliOptionsUtils.createOptions();
		options.addOption(CLI_INPUT, true, "input file name");
		options.addOption(CLI_OUTPUT, true, "output file name");

		final CommandLine clArgs = CliOptionsUtils.parse(options, args);

		if (args.length == 0) {
			CliOptionsUtils.printHelpAndExit(options);
		}

		final String inputFileName = clArgs.getOptionValue(CLI_INPUT);
		final String outputFileName = clArgs.getOptionValue(CLI_OUTPUT);

		Objects.requireNonNull(inputFileName, "input file not setted");
		Objects.requireNonNull(outputFileName, "output file not setted");

		final Collection<ImportDataset> alreadyImported = read(outputFileName);
		final Collection<ImportDataset> toImport = read(inputFileName);

		final Set<ImportDataset> set = new TreeSet<>(new ImportDatasetComparator());
		set.addAll(alreadyImported);
		final int sizeBeforeImportNew = set.size();

		set.addAll(toImport);

		LOG.info((set.size() - sizeBeforeImportNew) + " datasets imported");

		Collection<Collection<String>> all = set.stream().map(i -> ImportDataset.deflat(i))
				.collect(Collectors.toList());
		CsvUtils.write(outputFileName, HEADER, all);

	}

	private static Collection<ImportDataset> read(final String outputFileName) throws IOException {
		final Collection<CSVRecord> records = CsvUtils.getRecords(outputFileName, HEADER);
		if (records.isEmpty()) {
			return Collections.emptyList();
		}

		final List<ImportDataset> collect = records.stream().map(r -> ImportDataset.creating(r))
				.collect(Collectors.toList());

		return collect.stream().filter(i -> isValid(i)).collect(Collectors.toList());
	}

	private static boolean isValid(ImportDataset i) {
		final Collection<Method> getterMethods = ReflectionUtils.getAllGetterMethodsOf(i.getClass());
		for (Method method : getterMethods) {
			try {
				method.invoke(i);
			} catch (Throwable t) {
				final String message = "object invalid: " + i + " because of method: " + method;
				LOG.warn(message);
				LOG.debug(message, t);
				return false;
			}
		}
		return true;
	}

	private static class ImportDatasetComparator implements Comparator<ImportDataset> {

		@Override
		public int compare(ImportDataset o1, ImportDataset o2) {
			int dateCompare = o1.getDate().compareTo(o2.getDate());
			if (dateCompare != 0) {
				return dateCompare;
			}
			return o1.getIsin().compareTo(o2.getIsin());
		}

	}
}
