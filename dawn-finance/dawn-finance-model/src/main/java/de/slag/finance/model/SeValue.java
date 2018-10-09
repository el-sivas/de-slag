package de.slag.finance.model;

import de.slag.central.model.ApplicationBean;
import de.slag.central.model.Labelable;

public class SeValue extends ApplicationBean implements Labelable {

	private String isin;

	private String name;

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	@Override
	public String getLabel() {
		return isin + ", " + name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
