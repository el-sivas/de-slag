import org.junit.Assert;
import org.junit.Test;

import de.slag.central.data.config.DawnConfig;
import de.slag.central.data.config.DawnFileConfig;

public class DawnFileConfigTest {
	
	@Test
	public void test() {
		final DawnConfig creating = DawnFileConfig.instance();
		Assert.assertNotNull(creating);
	}

}
