package nick.main;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nick.util.Logging;
import nick.util.NickMaker;

public class Nick {
	static final Logger logger = LogManager.getLogger(Nick.class.getName());
	public static void main(String[] args) {
			new NickMaker(0);
		
	}

}
