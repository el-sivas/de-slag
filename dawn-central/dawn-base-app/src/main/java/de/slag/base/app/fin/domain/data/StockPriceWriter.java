package de.slag.base.app.fin.domain.data;

import java.nio.file.Path;
import java.util.Collection;
import java.util.function.BiConsumer;

import de.slag.base.app.fin.domain.model.StockPrice;

public interface StockPriceWriter extends BiConsumer<Collection<StockPrice>, Path> {

}
