package de.slag.central.view.dbtool.actions;

import java.util.function.Supplier;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DbActionFactory {

	private static final Log LOG = LogFactory.getLog(DbActionFactory.class);

	public static DbAction create(Supplier<DbActionResult> dbActionFunction, String description) {
		return new DbAction() {

			private Boolean done;

			private Boolean errors;

			@Override
			public void run() {
				if (isDone()) {
					return;
				}
				DbActionResult dbActionResult = null;
				try {
					dbActionResult = dbActionFunction.get();
				} catch (Throwable t) {
					LOG.error(t);
					errors = true;
				}
				done = true;
				errors = dbActionResult.isErrors();
			}

			@Override
			public boolean isErrors() {
				return BooleanUtils.isTrue(errors);
			}

			@Override
			public boolean isDone() {
				return BooleanUtils.isTrue(done);
			}

			@Override
			public String getLabel() {
				return description;
			}
		};
	}

}
