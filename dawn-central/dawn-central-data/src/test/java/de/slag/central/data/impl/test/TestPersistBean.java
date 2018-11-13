package de.slag.central.data.impl.test;

import de.slag.central.data.impl.PersistBean;

public class TestPersistBean extends PersistBean {

	final String TEST_STRING = "TEST_STRING";

	public String getTestString() {
		return getElseString(TEST_STRING);
	}

	public void setTestString(String testString) {
		setElseString(TEST_STRING, testString);
	}

}
