package de.slag.central.view;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import de.slag.central.model.adm.User;

@SessionScoped
@Component
public class SessionContext implements DawnContext {

	private User currentUser;

	private String database = "default";

	private String contextFile;

	@PostConstruct
	public void init() {
		contextFile = System.getProperty("context");
	}
	
	public boolean isContextAvaliable() {
		return StringUtils.isNotBlank(contextFile);
	}

	@Override
	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User user) {
		this.currentUser = user;
	}

	@Override
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

}
