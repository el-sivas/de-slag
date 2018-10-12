package de.slag.central.data;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.tools.ant.types.Mapper;

import de.slag.base.tools.mapping.FieldMapper;

public class FieldMapperSupport {

	private static FieldMapperSupport instance;

	private Collection<FieldMapper<?>> mappers = new ArrayList<>();

	public static FieldMapperSupport instance() {
		if (instance == null) {
			instance = new FieldMapperSupport();
		}
		return instance();
	}

	public void addMappers(Collection<FieldMapper<?>> mappers) {
		this.mappers.addAll(mappers);
	}

	public Collection<FieldMapper<?>> getMappers() {
		return new ArrayList<>(mappers);
	}

	public FieldMapper<?> get(Class<?> from, Class<?> to) {
		for (FieldMapper<?> fieldMapper : mappers) {
			if (fieldMapper.inputClass() == from && fieldMapper.outputClass() == to) {
				return fieldMapper;
			}
		}
		return null;
	}

}
