package de.slag.base.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import de.slag.base.BaseException;

public class ReflectionUtils {

	public static Collection<Method> getAllGetterMethodsOf(Class<? extends Object> c) {
		final List<Method> asList = Arrays.asList(c.getDeclaredMethods());
		final List<Method> getterMethods = asList.stream().filter(m -> {
			final String methodName = m.getName().toLowerCase();
			return methodName.startsWith("get") || methodName.startsWith("is");
		}).collect(Collectors.toList());
		return getterMethods;
	}

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
	
	static Collection<Field> collectFields(Object o) {
		return collectFields(o.getClass());
	}

	static Collection<Field> collectFields(Class<?> clazz) {
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
