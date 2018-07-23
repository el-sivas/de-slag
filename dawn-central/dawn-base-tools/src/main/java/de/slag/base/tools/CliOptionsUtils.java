package de.slag.base.tools;

import java.util.function.Consumer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

public class CliOptionsUtils {

	public static String HELP = "h";

	public static Options createOptions() {
		final Options o = new Options();
		o.addOption(HELP, false, "prints this help page");
		return o;
	}

	public static CommandLine parse(Options o, String[] args) throws ParseException {
		return new DefaultParser().parse(o, args);
	}

	public static void printHelpAndExit(Options o) {
		final Consumer<Option> oc = new Consumer<Option>() {

			@Override
			public void accept(Option option) {
				final StringBuilder sb = new StringBuilder();
				final String opt = option.getOpt();
				sb.append(opt);
				sb.append(StringUtils.repeat(" ", 8 - opt.length()));
				sb.append(option.getDescription());
				System.out.println(sb);
			}
		};
		o.getOptions().forEach(oc);
		System.exit(0);
	}
}
