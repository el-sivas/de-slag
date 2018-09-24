package de.slag.base.context;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SlagContext {

	private static SlagContext ctx;

	public static void init() {
		synchronized (SlagContext.class) {
			if (ctx != null) {
				throw new SlagContextException("already initalized");
			}
		}
	}

	private AnnotationConfigApplicationContext appCtx;

	private void setContext(AnnotationConfigApplicationContext appCtx) {

		this.appCtx = appCtx;
	}

}
