package de.slag.central.data.impl;

public class DataException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	

	public DataException(Throwable t) {
		super(t);
	}
	
	public DataException(String s) {
		super(s);
	}
}
