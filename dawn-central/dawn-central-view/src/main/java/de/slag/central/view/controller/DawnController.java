package de.slag.central.view.controller;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public interface DawnController extends Serializable {

	static final Log LOG = LogFactory.getLog(DawnController.class);

	/**
	 * Complete reset of this. Implement if needed.
	 */
	default void reset() {
		
	}

}
