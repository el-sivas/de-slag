package de.slag.central.view.dbtool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Controller;

import de.slag.base.tools.LoggingUtils;
import de.slag.base.tools.SystemUtils;
import de.slag.central.DawnApplicationContext;
import de.slag.central.service.adm.DbInfoService;
import de.slag.central.view.controller.DawnController;
import de.slag.central.view.dbtool.actions.DbAction;
import de.slag.central.view.dbtool.actions.DbActionFactory;
import de.slag.central.view.dbtool.actions.DbActionResult;
import de.slag.central.view.dbtool.content.DbUserService;
import de.slag.central.view.dbtool.structure.DbUpdateService;

@Controller
public class DbToolController implements DawnController {

	private static final long serialVersionUID = 1L;

	@Resource
	private DbInfoService dbInfoService;

	private Map<Long, String> info = new HashMap<>();

	private Collection<DbAction> dbActions = new ArrayList<>();

	@PostConstruct
	public void init() {
		LoggingUtils.activateLogging();
		addDbActions();
	}

	public void addDbActions() {
		dbActions.add(DbActionFactory.create(new Supplier<DbActionResult>() {

			@Override
			public DbActionResult get() {
				initContext();
				return DbActionResult.noErrors();
			}
		}, "Init application context"));

		dbActions.add(DbActionFactory.create(new Supplier<DbActionResult>() {

			@Override
			public DbActionResult get() {
				assertDatabaseUpdated();
				return DbActionResult.noErrors();
			}
		}, "Database update"));

		dbActions.add(DbActionFactory.create(new Supplier<DbActionResult>() {

			@Override
			public DbActionResult get() {
				assertAdminUser();
				return DbActionResult.noErrors();
			}
		}, "Admin Users"));

	}

	public void addInfo(String s) {
		info.put(System.nanoTime(), s);
	}

	public String getInfo() {
		final List<Long> keys = new ArrayList<>(info.keySet());
		Collections.sort(keys, Collections.reverseOrder());

		final StringBuilder sb = new StringBuilder();
		keys.forEach(l -> sb.append(info.get(l) + "\n"));

		return sb.toString();
	}

	public Date getTime() {
		return SystemUtils.getStartTime();
	}

	public void initContext() {
		DawnApplicationContext.getContext();
		addInfo("application context initialized");
	}

	public void assertDatabaseUpdated() {
		getBeanFactory().getBean(DbUpdateService.class).updateDatabase();
		addInfo("db update done");
	}

	public void assertAdminUser() {
		getBeanFactory().getBean(DbUserService.class).assertAdminUsers();
		addInfo("admin users asserted");
	}

	public Collection<DbAction> getDbActions() {
		return dbActions;
	}

	public BeanFactory getBeanFactory() {
		return DawnApplicationContext.getContext().getAutowireCapableBeanFactory();
	}

	public void startAll() {
		for (DbAction dbAction : dbActions) {
			dbAction.run();
			dbInfoService.saveInfo("executed: " + dbAction.getLabel());
		}
	}
}
