package de.slag.finance.data;

import java.util.Collection;
import java.util.function.Supplier;

import de.slag.finance.model.SeValuePricePoint;

public interface SeValuePricePointImportService {


	/**
	 * Import but dont saves
	 */
	Collection<SeValuePricePoint> importData(Collection<String> isins, Supplier<SeValuePricePoint> s);
}
