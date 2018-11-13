package de.slag.central.view.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import de.slag.central.model.adm.User;
import de.slag.central.view.SessionContext;
import de.slag.central.view.controller.message.ControllerMessage;
import de.slag.central.view.controller.message.ControllerMessage.Severity;

public abstract class ApplicationController implements DawnController {

	private static final long serialVersionUID = 1L;

	@Resource
	private SessionContext sessionContext;

	private Collection<ControllerMessage> messages = new ArrayList<>();

	protected void addError(String text) {
		addMessage(Severity.ERROR, text);
	}

	protected void addWaring(String text) {
		addMessage(Severity.WARN, text);
	}

	protected void addInfo(String text) {
		addMessage(Severity.INFO, text);
	}

	private void addMessage(Severity severity, String text) {
		messages.add(ControllerMessage.creating(severity, text));
	}

	public final boolean isLoadLazyValuesExternal() {
		reset();
		loadLazyValues();
		return true;
	}

	protected void loadLazyValues() {
		// implement if needet
	}

	public String getMessage() {
		final StringBuilder sb = new StringBuilder();
		messages.forEach(m -> sb.append(m.getLabel() + "<br />"));
		messages.clear();

		return sb.toString().trim();
	}

	public boolean isMessageOccured() {
		return messages.size() > 0;
	}

	public User getCurrentUser() {
		return sessionContext.getCurrentUser();
	}

	public boolean isUserLoggedIn() {
		return getCurrentUser() != null;
	}

	public String logOut() {
		sessionContext.setCurrentUser(null);

		// das hier hilft leider nix
		return "index.html";
	}

	public boolean isErrorsOccured() {
		return messages.stream().filter(e -> e.getSeverity() == Severity.ERROR).findFirst().isPresent();
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
