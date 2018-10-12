package de.slag.central.view.controller.message;

import de.slag.base.Labelable;

public class ControllerMessage implements Labelable {
	
	private String text;
	
	private Severity severity;
	
	public static ControllerMessage creating(Severity severity, String text) {
		final ControllerMessage controllerMessage = new ControllerMessage();
		controllerMessage.setSeverity(severity);
		controllerMessage.setText(text);
		return controllerMessage;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}
	
	public enum Severity {
		INFO,
		WARN,
		ERROR
	}

	@Override
	public String getLabel() {
		return severity + ": "+ text;
	}

}
