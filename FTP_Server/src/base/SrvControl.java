package base;

import java.io.IOException;

public class SrvControl extends ConnectionServer{

	ServerCommandHandler cmdHandler = ServerMain.cmdHandler;
	
	//listen() auto-executes after the setupConnection() is complete.
	public void listen() {
		String data = "";
		while(alive) {
			// Read the data sent by the client
			System.out.println("0-Data is "+data);
			try {
				
				data = input.readLine();
				System.out.println("1-Data is "+data);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			sleep();
			System.out.println("2-Data is "+data);
			//Handles the commands the server receives
			cmdHandler.interpreter(data, output);
			sleep();

			// Send the text
			output.println(data);
		}
		
		super.closeConnection();
	}
	
	void sleep() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
