package de.slag.central.data.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HqlSelect {

	private static final String FROM = "FROM";
	private static final String BLANK = " ";
	private static final String PB = "PB";
	private static final String WHERE = "WHERE";
	private static final String EQUAL_SIGN = "=";
	private static final String AND = " AND ";

	public static String getHql(Class<? extends PersistBean> c) {
		return getHql(c, Collections.emptyMap());
	}

	public static String getHql(Class<? extends PersistBean> c, Map<String, Object> attributeValues) {
		final StringBuilder sb = new StringBuilder();
		sb.append(FROM + BLANK);

		sb.append(c.getName() + BLANK);
		sb.append(PB + BLANK);
		sb.append(WHERE + BLANK);

		sb.append("valid_until > sysdate()");

		if (!attributeValues.isEmpty()) {
			sb.append(AND);
			
			final Function<? super String, ? extends String> mapper = (Function<? super String, ? extends String>) attribute -> attribute
					+ EQUAL_SIGN + "'" + attributeValues.get(attribute) + "'";

			final List<String> attributeValueExpressions = attributeValues.keySet().stream().map(mapper)
					.collect(Collectors.toList());

			sb.append(String.join(AND, attributeValueExpressions));
		}

		return sb.toString();
	}

}
