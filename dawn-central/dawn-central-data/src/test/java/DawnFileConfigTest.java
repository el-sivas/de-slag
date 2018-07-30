import org.junit.jupiter.api.Test;

import de.slag.central.data.config.DawnConfig;
import de.slag.central.data.config.DawnFileConfig;

public class DawnFileConfigTest {
	
	@Test
	public void test() {
		final DawnConfig instance = DawnFileConfig.instance();
		System.out.println(instance.getStringValue("test"));
	}

}
