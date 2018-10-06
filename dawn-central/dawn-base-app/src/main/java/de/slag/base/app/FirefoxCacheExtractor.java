package de.slag.base.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import de.slag.base.tools.ConsoleUtils;

public class FirefoxCacheExtractor {

	private static final String MIME_JPEG = "image/jpeg";

	public static void main(String[] args) throws IOException {
		final String baseDir = "/home/sivas/.cache/mozilla/firefox/ehsdkfkq.default/cache2/entries";
		File f = new File(baseDir);
		while (true) {

			final List<String> asList = Arrays.asList(f.list());

			for (String string : asList) {

				final String fullFileName = baseDir + "/" + string;
				final String runConsoleCommand = ConsoleUtils.runConsoleCommand("file --mime-type " + fullFileName);
				final String[] split = runConsoleCommand.split(": ");
				final String mimeType = split[1].trim();
				final String targetFile = "/tmp/pics/" + string + ".jpg";
				if (new File(targetFile).exists()) {
					out("already copied: " + targetFile);
					continue;
				}
				if (!MIME_JPEG.equals(mimeType)) {
					continue;
				}
				final long length = new File(fullFileName).length();
				if (length < 100000) {
					continue;
				}

				out("copy " + targetFile);
				Files.copy(Paths.get(fullFileName), Paths.get(targetFile));
			}
		}
	}

	private static void out(Object o) {
		System.out.println(o);
	}

}
