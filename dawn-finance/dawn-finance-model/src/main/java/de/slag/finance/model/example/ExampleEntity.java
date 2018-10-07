package de.slag.finance.model.example;

import de.slag.central.model.ApplicationBean;

public class ExampleEntity extends ApplicationBean {

	@Override
	public String getLabel() {
		return getCreatedAt().toString();
	}

}
