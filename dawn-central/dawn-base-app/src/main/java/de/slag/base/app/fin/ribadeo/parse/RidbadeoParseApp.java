package de.slag.base.app.fin.ribadeo.parse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

import de.slag.base.app.fin.FinLogicExecption;
import de.slag.base.tools.CliOptionsUtils;

public class RidbadeoParseApp {

	private static final String OPTION_WORKDIR = "w";
	
	private static final String VERSION = "0.0.1";

	public static void main(String[] args) throws ParseException, IOException {
		final Options options = CliOptionsUtils.createOptions();
		options.addOption(OPTION_WORKDIR, true, "workdir");

		final CommandLine parse = CliOptionsUtils.parse(options, args);

		final String workdir = parse.getOptionValue(OPTION_WORKDIR);
		if (StringUtils.isEmpty(workdir)) {
			CliOptionsUtils.printHelpAndExit(options);
		}

		final Stream<Path> walk = Files.walk(Paths.get(workdir));
		final List<Path> collect = walk
			.filter(Files::isRegularFile)
			.filter((Predicate<Path>) e -> e.getFileName().endsWith("Aktie.html"))
			.collect(Collectors.toList());
		final Collection<RibadeoImportHolder> collection = new ArrayList<>();
		collect.forEach(f -> {
			final Path path = Paths.get(f.getFileName().toString());
			final byte[] readAllBytes;
			try {
				readAllBytes = Files.readAllBytes(path);
			} catch (IOException e) {
				throw new FinLogicExecption(e);
			}
			collection.add(RibadeoParseLogicUtils.parseAktie(new String(readAllBytes)));
		});
		collection.forEach(System.out::println);
	}
}
