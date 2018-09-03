package de.slag.base.tools;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionUtils {

	public static Collection<Method> getAllGetterMethodsOf(Class<? extends Object> c) {
		final List<Method> asList = Arrays.asList(c.getDeclaredMethods());
		final List<Method> getterMethods = asList.stream().filter(m -> {
			final String methodName = m.getName().toLowerCase();
			return methodName.startsWith("get") || methodName.startsWith("is");
		}).collect(Collectors.toList());
		return getterMethods;
	}

}
