package de.slag.base.tools;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.slag.base.BaseException;

public class FieldMappingUtils {

	public static void map(Object from, Object to, Collection<String> skipFields, boolean tolerant) {
		final Collection<Field> fields = ReflectionUtils.collectFields(from.getClass());
		final List<Field> relevantFields = fields.stream().filter(f -> !skipFields.contains(f.getName()))
				.collect(Collectors.toList());

		final Collection<Field> targetFields = ReflectionUtils.collectFields(to.getClass());
		for (final Field sourceField : relevantFields) {
			final String sourceFieldName = sourceField.getName();
			final Optional<Field> target = targetFields.stream().filter(f -> sourceFieldName.equals(f.getName()))
					.findFirst();
			if (!target.isPresent()) {
				if (!tolerant) {
					throw new BaseException("target field not found: " + sourceFieldName);
				}
				continue;
			}
			final Field targetField = target.get();
			final Class<?> type = sourceField.getType();
			final Class<?> type2 = targetField.getType();
			if (!type.equals(type2)) {
				throw new BaseException("types not compatible");
			}
			sourceField.setAccessible(true);
			Object fieldValue;
			try {
				fieldValue = sourceField.get(from);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new BaseException(e);
			}
			sourceField.setAccessible(false);

			targetField.setAccessible(true);
			try {
				targetField.set(to, fieldValue);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new BaseException(e);
			}
			targetField.setAccessible(false);
		}

	}
}
