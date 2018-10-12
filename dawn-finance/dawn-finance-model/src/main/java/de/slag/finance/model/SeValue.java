package de.slag.finance.model;

import org.apache.commons.lang3.BooleanUtils;

import de.slag.base.Labelable;
import de.slag.central.model.ApplicationBean;

public class SeValue extends ApplicationBean implements Labelable {

	private String isin;

	private String name;
	
	private Boolean testdaten;

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

	public boolean getTestdaten() {
		return BooleanUtils.isTrue(testdaten);
	}

	public void setTestdaten(boolean testdaten) {
		this.testdaten = testdaten;
	}

}
