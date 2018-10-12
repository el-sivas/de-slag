package de.slag.finance.view;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import de.slag.central.service.ApplicationBeanService;
import de.slag.central.view.controller.ApplicationBeanController;
import de.slag.finance.model.SeValuePricePoint;
import de.slag.finance.service.SeValuePricePointService;

@Controller
public class SeValuePriceController extends ApplicationBeanController<SeValuePricePoint> {

	private static final long serialVersionUID = 1L;

	@Resource
	private SeValuePricePointService seValuePricePointService;

	private final Collection<SeValuePricePoint> points = new ArrayList<>();

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

	@Override
	protected ApplicationBeanService<SeValuePricePoint> getApplicationBeanService() {
		return seValuePricePointService;
	}

	public Collection<SeValuePricePoint> getPoints() {
		return points;
	}
}
