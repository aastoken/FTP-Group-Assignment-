package base;

import java.io.IOException;

public class SrvControl extends Connection{

	CommandHandler cmdHandler = ServerMain.cmdHandler;
	
	public void listen() {
		String data = "";
		while(alive) {
			// Read the data sent by the client
			try {
				data = input.readLine();
			} catch (IOException e) {				
				e.printStackTrace();
			}
			System.out.println("Server receives: " + data);

			
			cmdHandler.interpreter(data, output);
			

			// Send the text
			output.println(data);
		}
		
		super.closeConnection();
	}
	
}
