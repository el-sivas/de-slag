package de.slag.finance.service.impl.example;

import de.slag.finance.data.example.ExampleEntityDao;
import de.slag.finance.model.example.ExampleEntity;
import de.slag.finance.service.example.ExampleEntityService;

public class ExampleEntityServiceImpl implements ExampleEntityService {

	private ExampleEntityDao exampleEntityDao;
	
	public ExampleEntity create() {
		return new ExampleEntity();
	}
	
	public void save(ExampleEntity e) {
		exampleEntityDao.save(e);
	}
}
