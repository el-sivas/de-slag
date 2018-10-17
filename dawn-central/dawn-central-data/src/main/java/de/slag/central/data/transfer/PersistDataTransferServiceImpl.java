package de.slag.central.data.transfer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.stereotype.Service;

import de.slag.base.tools.FieldMappingUtils;
import de.slag.central.data.impl.PersistBean;
import de.slag.central.model.ApplicationBean;

@Service
public class PersistDataTransferServiceImpl implements PersistDataTransferService {

	@Override
	public void transferData(PersistBean from, ApplicationBean to) {
		transferDataInternal(from, to, Collections.emptyList());
	}

	@Override
	public void transferData(ApplicationBean from, PersistBean to) {
		transferDataInternal(from, to, Arrays.asList("id"));
	}

	private void transferDataInternal(Object from, Object to, Collection<String> excludedFields) {
		FieldMappingUtils.map(from, to, excludedFields, true);
	}

//
//	private AB unpersist(PB persistBean, Supplier<AB> supplier) {
//		final AB ab = supplier.get();
//		final Collection<PropertyGetter> determineGetter = ReflectionUtils.determineGetter(persistBean);
//		final Collection<PropertySetter> determineSetter = ReflectionUtils.determineSetter(ab);
//
//		for (PropertySetter propertySetter : determineSetter) {
//			final String name = propertySetter.getName();
//			final PropertyGetter propertyGetter = determineGetter.stream().filter(s -> s.getName().equals(name))
//					.findFirst().get();
//			final FieldMapper<?> fieldMapper = getFieldMapper(propertyGetter.getType(), propertySetter.getType());
//			propertySetter.set(fieldMapper.equals(propertyGetter.get()));
//		}
//		return ab;
//
//	}
}
