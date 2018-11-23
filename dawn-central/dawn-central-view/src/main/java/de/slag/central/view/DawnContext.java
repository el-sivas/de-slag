package de.slag.central.view;

import de.slag.central.model.adm.User;

public interface DawnContext {

	User getCurrentUser();

	String getDatabase();	

}
