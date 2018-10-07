package de.slag.central.view.dbtool.actions;

public interface DbActionResult {
	
	boolean isErrors();
	
	static DbActionResult noErrors() {
		return new DbActionResult() {
			
			@Override
			public boolean isErrors() {
				return false;
			}
		};
	}
	
	static DbActionResult withErrors() {
		return new DbActionResult() {
			
			@Override
			public boolean isErrors() {
				return true;
			}
		};
	}

}
