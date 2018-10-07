package de.slag.base.tools;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.slag.base.BaseException;

public class FieldMappingUtils {

	public static void setField(Object obj, String fieldname, Object value) {
		final Collection<Field> collectFields = collectFields(obj);
		for (Field field : collectFields) {
			if (fieldname.equals(field.getName())) {
				field.setAccessible(true);
				try {
					field.set(obj, value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new BaseException(e);
				}
				field.setAccessible(false);
			}
		}
	}

	public static void map(Object from, Object to, Collection<String> skipFields, boolean tolerant) {
		final Collection<Field> fields = collectFields(from);
		final List<Field> relevantFields = fields.stream().filter(f -> !skipFields.contains(f.getName()))
				.collect(Collectors.toList());

		final Collection<Field> targetFields = collectFields(to);
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

	private static Collection<Field> collectFields(Object o) {
		return collectFields(o.getClass());
	}

	private static Collection<Field> collectFields(Class<?> clazz) {
		final Class<?> superclass = clazz.getSuperclass();
		final Collection<Field> fields = new ArrayList<>();
		if (superclass != null) {
			fields.addAll(collectFields(superclass));
		}

		final List<Field> fieldsOfThisClass = Arrays.asList(clazz.getDeclaredFields());
		fields.addAll(fieldsOfThisClass);
		return fields;
	}
}
