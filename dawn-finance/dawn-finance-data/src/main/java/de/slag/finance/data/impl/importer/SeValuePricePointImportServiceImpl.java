package de.slag.finance.data.impl.importer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.money.MonetaryAmountFactory;

import org.springframework.stereotype.Service;

import de.slag.finance.DawnFinanceMonetaryAmountFactorySupport;
import de.slag.finance.MonetaryAmount;
import de.slag.finance.data.SeValuePricePointImportService;
import de.slag.finance.model.SeValuePricePoint;
import de.slag.finance.service.SeValueService;

@Service
public class SeValuePricePointImportServiceImpl implements SeValuePricePointImportService {

	@Resource
	private ImpFinValuePointDao impFinValuePointDao;

	@Resource
	private SeValueService seValueService;

	public SeValuePricePoint create(Supplier<SeValuePricePoint> s, ImpFinValuePoint i) {
		final SeValuePricePoint persist = s.get();
		persist.setPriceDate(i.getDate());

		// TODO nullsafe
		persist.setSeValue(seValueService.loadByIsin(i.getIsin()).get());

		persist.setPrice(DawnFinanceMonetaryAmountFactorySupport.create(i.getPrice()));

		return persist;
	}

	@Override
	public Collection<SeValuePricePoint> importData(Collection<String> isins, Supplier<SeValuePricePoint> s) {
		final Collection<ImpFinValuePoint> findBy = findBy(isins);

		Collection<SeValuePricePoint> persists = new ArrayList<>();

		findBy.forEach(i -> persists.add(create(s, i)));

		return persists;

	}

	private Collection<ImpFinValuePoint> findBy(Collection<String> isins) {
		final Collection<ImpFinValuePoint> allImports = impFinValuePointDao.findAll();
		Stream<ImpFinValuePoint> filter = allImports.stream().filter(i -> isins.contains(i.getIsin()));
		return filter.collect(Collectors.toList());
	}

}
