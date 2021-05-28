package start;

import functionality.FileUtils;
import functionality.Logger;

public class SvMain {
	private static Logger logger = new Logger();
	public static void main(String[] args) {
	
		Server myServer = new Server();
		Logger myLogger = new Logger();
		myServer.runServer();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            FileUtils.writeLogs(logger);
        }));
	}

}
