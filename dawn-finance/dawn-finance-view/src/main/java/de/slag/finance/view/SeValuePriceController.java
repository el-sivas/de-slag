package de.slag.finance.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import de.slag.central.service.ApplicationBeanService;
import de.slag.central.view.controller.ApplicationBeanController;
import de.slag.finance.model.SeValuePricePoint;
import de.slag.finance.service.IsinValidator;
import de.slag.finance.service.SeValuePricePointService;
import de.slag.finance.service.SeValueService;

@Controller
public class SeValuePriceController extends ApplicationBeanController<SeValuePricePoint> {

	private static final long serialVersionUID = 1L;

	@Resource
	private SeValuePricePointService seValuePricePointService;

	@Resource
	private SeValueService seValueService;

	private final Collection<SeValuePricePoint> points = new ArrayList<>();

	private String importIsins;

	@Override
	public void reset() {
		super.reset();
		loadAll();
	}

	private void loadAll() {
		points.clear();
		points.addAll(seValuePricePointService.findAll());
		addInfo(points.size() + " loaded");
	}

	public void importValues() {

		final List<String> asList = Arrays.asList(importIsins.split(","));
		final IsinValidator isinValidator = IsinValidator.instance();

		for (String string : asList) {
			if (!isinValidator.test(string)) {
				addError(string + " is not a valid ISIN");
			}
		}
		if (isErrorsOccured()) {
			return;
		}
		addInfo("Import: " + importIsins + "...");

		// hier gehts weiter: testen
		seValuePricePointService.importFromStaging(asList);
		addInfo("Import without errors");

		importIsins = null;
	}

	@Override
	protected ApplicationBeanService<SeValuePricePoint> getApplicationBeanService() {
		return seValuePricePointService;
	}

	public Collection<SeValuePricePoint> getPoints() {
		return points;
	}

	public String getImportIsins() {
		return importIsins;
	}

	public void setImportIsins(String importIsins) {
		this.importIsins = importIsins;
	}
}
