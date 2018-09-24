package de.slag.base.app.fin.domain.data;

import java.io.File;
import java.util.Collection;
import java.util.function.Function;

import de.slag.base.app.fin.domain.model.StockPrice;

public interface StockPriceReader extends Function<String, Collection<StockPrice>> {
	
}
