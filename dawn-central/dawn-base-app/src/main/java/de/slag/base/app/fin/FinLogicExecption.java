package de.slag.base.app.fin;

public class FinLogicExecption extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FinLogicExecption(String s) {
		super(s);
	}
	
	public FinLogicExecption(Throwable t) {
		super(t);
	}

}
