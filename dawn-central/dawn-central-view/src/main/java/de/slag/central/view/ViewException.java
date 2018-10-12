package de.slag.central.view;

import de.slag.base.Labelable;

public class ViewException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ViewException(String s) {
		super(s);
	}

	public ViewException(String errorMessage, String affectedObjectDescription) {
		super(errorMessage + ": '" + affectedObjectDescription + "'");
	}

	public ViewException(String errorMessage, Labelable labelable) {
		this(errorMessage, labelable.getLabel());
	}
}
