package de.slag.finance.view.example;

import javax.faces.bean.ManagedBean;

import de.slag.finance.model.example.ExampleEntity;
import de.slag.finance.service.example.ExampleEntityService;

@ManagedBean
public class ExampleController {
	
	private ExampleEntityService exampleEntityService;
	
	public ExampleEntity createAndSave() {
		ExampleEntity e = exampleEntityService.create();
		exampleEntityService.save(e);
		return e;
	}

}
