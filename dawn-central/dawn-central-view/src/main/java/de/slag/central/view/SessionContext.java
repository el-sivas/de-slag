package de.slag.central.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.slag.central.model.adm.User;

@SessionScoped
@ManagedBean(name="sessionContext")
public class SessionContext {
	
	private User currentUser;
	
	private String database = "default";

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User user) {
		this.currentUser = user;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

}
