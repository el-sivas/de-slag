package de.slag.central.model.adm;

import org.apache.commons.lang3.BooleanUtils;

import de.slag.base.tools.HashUtils;
import de.slag.base.tools.HashUtils.Algorythm;
import de.slag.central.model.ApplicationBean;

public class User extends ApplicationBean {

	private String username;

	private String passwordHash;

	private Boolean active;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.passwordHash = hash(password);
	}

	private String hash(String password) {
		return HashUtils.hash(password, Algorythm.SHA256);
	}

	public boolean isPasswordCorrect(String password) {
		return passwordHash.equals(hash(password));
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public boolean isActive() {
		return BooleanUtils.isTrue(active);
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
