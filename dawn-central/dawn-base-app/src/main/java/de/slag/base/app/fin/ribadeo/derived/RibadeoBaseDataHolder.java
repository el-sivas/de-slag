package de.slag.base.app.fin.ribadeo.derived;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

import de.slag.base.app.fin.domain.data.DataDefault;
import de.slag.base.app.fin.domain.data.StockPriceReader;
import de.slag.base.app.fin.domain.model.StockPrice;

public class RibadeoBaseDataHolder {
	
	private final Collection<StockPrice> baseData = new ArrayList<>();
	
	public RibadeoBaseDataHolder(String fileName) {
		final StockPriceReader reader = DataDefault.defaultReader();
		this.baseData.addAll(reader.apply(fileName));
	}
}
