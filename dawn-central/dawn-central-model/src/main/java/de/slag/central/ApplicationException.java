package de.slag.central;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApplicationException(final String s, final Throwable t) {
		super(s, t);
	}

	public ApplicationException(final String s) {
		super(s);
	}

	public ApplicationException(final Throwable t) {
		super(t);
	}
}
