package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CliControl extends ConnectionClient {

	ClientCommandHandler cmdHandler = ClientMain.cmdHandler;
	
	
	public void listen() {
		String data = "";
		String result = "";
		BufferedReader inputKeyboard;
		while(alive)//Switch to "alive" check?
		{
			// Get text from the keyboard
			inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Write text (QUIT to close the server): ");
			try {
				data = inputKeyboard.readLine();
			} catch (IOException e) {			
				e.printStackTrace();
			}
			System.out.println("Data is "+data);
			// Send data to the server
			output.println(data);
			//Handles the commands the client writes
			cmdHandler.interpreter(data, output);
	
			
			// Read data from the server
			try {
				result = input.readLine();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			System.out.println("Data = " + data + " --- Result = " + result);
			
			
			
			
		}
	}
	
	
}
