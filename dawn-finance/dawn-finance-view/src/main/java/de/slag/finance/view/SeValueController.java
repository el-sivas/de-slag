package de.slag.finance.view;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import de.slag.central.service.ApplicationBeanService;
import de.slag.central.view.controller.ApplicationBeanController;
import de.slag.finance.model.SEValue;
import de.slag.finance.service.IsinValidator;
import de.slag.finance.service.SEValueService;

@Controller
public class SeValueController extends ApplicationBeanController<SEValue> {

	private static final long serialVersionUID = 1L;

	@Resource
	private SEValueService sEValueService;

	private Collection<SEValue> seValues = new ArrayList<>();

	private String isin;

	private String name;

	@Override
	public SEValue create() {
		if (!new IsinValidator().test(isin)) {
			addInfo("isin is not valid: '" + isin + "'");
			return null;
		}
		if (StringUtils.isBlank(name)) {
			addInfo("name is null");
			return null;
		}

		final SEValue bean = super.create();
		bean.setIsin(isin);
		bean.setName(name);
		save(bean);
		addInfo("successfully created");
		isin = null;
		name = null;

		return null;
	}

	public void loadAll() {
		seValues.clear();
		seValues.addAll(sEValueService.findAll());
		addInfo(seValues.size() + " loaded");
	}

	@Override
	protected ApplicationBeanService<SEValue> getApplicationBeanService() {
		return sEValueService;
	}

	public Collection<SEValue> getSeValues() {
		return seValues;
	}

	public String getIsin() {
		return isin;
	}
	
	@Override
	public void reset() {
		super.reset();
		loadAll();
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
