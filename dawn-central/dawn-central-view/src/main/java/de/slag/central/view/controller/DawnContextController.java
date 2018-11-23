package de.slag.central.view.controller;

import de.slag.central.view.DawnContext;

/**
 * All controller with any context source <b>have to</b> implement this controller.
 *
 */
public interface DawnContextController extends DawnController {
	
	DawnContext getContext();


}
