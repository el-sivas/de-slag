package de.slag.base.app.fin.quito;

public class QuitoLogicException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public QuitoLogicException(Throwable t) {
		super(t);
	}
	
	public QuitoLogicException(String s) {
		super(s);
	}

}
