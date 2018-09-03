package de.slag.base.app.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.csv.CSVRecord;

import de.slag.base.tools.CsvUtils;
import de.slag.base.tools.GuiUtils;
import de.slag.base.tools.SleepUtils;
import de.slag.base.tools.SystemUtils;

public class TaskManager {

	private static final Function<CSVRecord, Task> CSV_TO_TASK = c -> {
		return new Task() {

			private Collection<Task> subs = new ArrayList<>();

			@Override
			public long getId() {
				return Long.valueOf(c.get("ID"));
			}

			@Override
			public String getTask() {
				return c.get("TASK");
			}

			@Override
			public int getN() {
				return Integer.valueOf(c.get("N"));
			}

			@Override
			public double getP() {
				return Double.valueOf(c.get("P"));
			}

			@Override
			public int getDuration() {
				return Integer.valueOf(c.get("DUR"));
			}

			public String toString() {
				return toLabel();
			}

			@Override
			public void addSub(Task task) {
				subs.add(task);

			}

			@Override
			public Collection<Task> getSubs() {
				return subs;
			}

			@Override
			public Collection<Long> subOf() {
				final String string = c.get("SUB_OF");
				final String[] split = string.split(",");
				final List<String> asList = Arrays.asList(split);
				return asList.stream().map(s -> Long.valueOf(s)).collect(Collectors.toList());
			}

			@Override
			public String getResource() {
				return c.get("RESOURCE");
			}
		};
	};

	private TaskManager(String tasksFileName) throws IOException {
		if (!Files.exists(Paths.get(tasksFileName))) {
			throw new RuntimeException("file not found: " + tasksFileName);
		}
		final String userHome = SystemUtils.getUserHome();
		final String configFile = userHome + File.separator + "task-manager.cfg";
		final Path path = Paths.get(configFile);
		if (!Files.exists(path)) {
			Files.createFile(path);
		}
		final Collection<CSVRecord> records = CsvUtils.getRecords(tasksFileName);
		records.size();
		final List<Task> collect = records.stream().map(CSV_TO_TASK).collect(Collectors.toList());
		for (Task task : collect) {
			final long id = task.getId();
			final List<Task> subsOfTask = collect.stream().filter(t -> t.subOf().contains(id))
					.collect(Collectors.toList());
			subsOfTask.forEach(t -> task.addSub(t));
			System.out.println(task);
			task.getSubs().forEach(s -> System.out.println("sub: " + s));
		}
		System.out.println("\n\n\nstart\n\n");
		final List<Task> roots = collect.stream().filter(t -> t.subOf().contains(0l)).collect(Collectors.toList());
		

		GuiUtils.generateDialog("TEST", "text");

		executeTask(roots);
	}

	private void executeTask(Collection<Task> tasks) {
		List<Task> shuffeled = new ArrayList<>(tasks);
		Collections.shuffle(shuffeled);
		for (Task task : shuffeled) {
			final double dice = Math.random();
			if (dice > task.getP()) {
				System.out.println("not diced: " + task);
				continue;
			}

			final int durationInMinutes = task.getDuration();
			final double timeLap = 12.0;
			double currentDurationInMinutes = durationInMinutes / timeLap;
			int seconds = (int) (currentDurationInMinutes * 60);
			final String task2 = task.getTask();
			if (task2.startsWith("exec:")) {
				final String[] split = task2.split(":");
				final String exec = split[1];
				final String resource = task.getResource();
				try {
					final Process exec2 = Runtime.getRuntime().exec(exec + " " + resource);
					if (currentDurationInMinutes > 0.00001) {
						System.out.println(task + " " + seconds);
						SleepUtils.sleepFor(seconds * 1000);
						if (exec2.isAlive()) {
							exec2.destroy();
						}
					} else {
						while (exec2.isAlive()) {
							SleepUtils.sleepFor(500);
						}
					}
					System.out.println("end");
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			} else {
				System.out.println(task2 + ": " + seconds);
			}
			SleepUtils.sleepFor(seconds * 1000);

			executeTask(task.getSubs());
		}
	}

	public static TaskManager getInstance(final String tasksFileName) {
		try {
			return new TaskManager(tasksFileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private interface Task {

		long getId();

		String getTask();

		int getN();

		double getP();

		int getDuration();

		Collection<Long> subOf();

		default String toLabel() {
			return getTask();
		}

		void addSub(Task task);

		Collection<Task> getSubs();

		String getResource();

	}
}
