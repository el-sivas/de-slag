package de.slag.base.app.fin.test;

import org.junit.Test;

import de.slag.base.app.tasks.TaskManager;

public class TaskManagerTest {
	
	@Test
	public void test() {
		TaskManager.getInstance("/home/sivas/test/tasks.csv");
	}

}
