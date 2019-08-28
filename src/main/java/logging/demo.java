package logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

public class demo {
	
	private static Logger log=LogManager.getLogger(demo.class.getName());
	
	@Test
	public void test() {
		log.error("This is error");
		log.debug("debug log");
	}
	
	
	

}
