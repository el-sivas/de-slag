package de.slag.central.view.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;

import de.slag.central.view.context.ContextSource;

@Controller
public class ContextSourceController implements DawnController {

	private static final long serialVersionUID = 1L;

	private Map<String, ContextSource> contextSources = new HashMap<>();
	
	

}
