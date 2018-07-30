package de.slag.central.data.user;

import javax.persistence.Column;
import javax.persistence.Entity;

import de.slag.central.data.PersistBean;

@Entity
public class User extends PersistBean {

	private static final long serialVersionUID = 1L;
	
	@Column
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
