package de.slag.base.app.fin.ribadeo.base;

import java.util.Collection;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.slag.base.app.fin.FinLogicExecption;
import de.slag.base.app.fin.domain.data.DataDefault;
import de.slag.base.app.fin.domain.data.StockPriceReader;
import de.slag.base.app.fin.domain.model.StockPrice;
import de.slag.base.tools.CliOptionsUtils;

public class RibadeoBaseIntegrationApp {

	private static final String BASE_FILE = "b";
	private static final String INPUT_FILE = "i";

	public static void main(String[] args) {
		final Options options = CliOptionsUtils.createOptions();		
		options.addOption(INPUT_FILE, true, "input file");
		options.addOption(BASE_FILE, true, "base file");
		final CommandLine parse;
		try {
			parse = CliOptionsUtils.parse(options, args);
		} catch (ParseException e) {
			throw new FinLogicExecption(e);
		}
		
		if(!parse.hasOption(INPUT_FILE) || !parse.hasOption(BASE_FILE)) {
			CliOptionsUtils.printHelpAndExit(options);
		}
		
		
		final String readFile = parse.getOptionValue(INPUT_FILE);
		final String baseFile = parse.getOptionValue(BASE_FILE);
		
		final StockPriceReader defaultReader = DataDefault.defaultReader();
		final Collection<StockPrice> toIntegrate = defaultReader.apply(readFile);

		RibadeoBaseIntegrationLogicUtils.integrate(toIntegrate, baseFile);

	}

}
