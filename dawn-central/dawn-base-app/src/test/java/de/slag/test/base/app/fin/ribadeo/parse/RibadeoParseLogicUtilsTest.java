package de.slag.test.base.app.fin.ribadeo.parse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import de.slag.base.app.fin.ribadeo.parse.RibadeoImportHolder;
import de.slag.base.app.fin.ribadeo.parse.RibadeoParseLogicUtils;

public class RibadeoParseLogicUtilsTest {

	@Test
	public void test() throws IOException {
		final Path path = Paths.get("/tmp/NL0000235190.html");
		final byte[] readAllBytes = Files.readAllBytes(path);
		final String html = new String(readAllBytes);
				
		final RibadeoImportHolder parse = RibadeoParseLogicUtils.parse(html);
		System.out.println(parse);
		
	}
	
}
