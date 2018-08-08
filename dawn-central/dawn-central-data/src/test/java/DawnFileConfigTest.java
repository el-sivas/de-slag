

import org.junit.Test;

import de.slag.central.data.config.DawnConfig;
import de.slag.central.data.config.DawnFileConfig;

public class DawnFileConfigTest {
	
	@Test
	public void test() {
		final DawnConfig instance = DawnFileConfig.instance();
		instance.keys().forEach(k -> System.out.println(k + ":" + instance.getStringValue((String) k)));
	}

}
