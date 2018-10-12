package de.slag.finance.data.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.money.MonetaryAmount;

import de.slag.base.tools.mapping.FieldMapper;
import de.slag.base.tools.mapping.FieldMapperFactory;
import de.slag.finance.MonetaryAmountFactorySupplier;

public class MonetaryAmountMapperFactory {

	public static Collection<FieldMapper<?>> createMonetaryAmountMapper() {
		final Collection<FieldMapper<?>> mapper = new ArrayList<>();

		mapper.add(FieldMapperFactory.create(MonetaryAmount.class, BigDecimal.class, BigDecimal.ZERO));
		mapper.add(FieldMapperFactory.create(BigDecimal.class, MonetaryAmount.class,
				MonetaryAmountFactorySupplier.creating().create()));

		return mapper;
	}

}
