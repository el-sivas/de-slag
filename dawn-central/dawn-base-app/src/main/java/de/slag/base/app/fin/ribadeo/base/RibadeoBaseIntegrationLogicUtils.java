package de.slag.base.app.fin.ribadeo.base;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import de.slag.base.app.fin.domain.data.DataDefault;
import de.slag.base.app.fin.domain.model.StockPrice;

public class RibadeoBaseIntegrationLogicUtils {

	public static void integrate(Collection<StockPrice> toIntegrate, String baseFile) {
		final Set<StockPrice> uniqueData = new TreeSet<>(StockPrice.comparing());
		final Collection<StockPrice> baseData = DataDefault.defaultReader().apply(baseFile);
		uniqueData.addAll(baseData);
		uniqueData.addAll(toIntegrate);
		DataDefault.defaultWriter().accept(uniqueData, Paths.get(baseFile));
	}

}
