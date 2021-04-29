package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CliControl extends ConnectionClient {

	public void listen() {
		String data = "";
		String result = "";
		BufferedReader inputKeyboard;
		while(data.compareTo("QUIT") != 0)//Switch to "alive" check
		{
		// Get text from the keyboard
		inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Write text (QUIT to close the server): ");
		try {
			data = inputKeyboard.readLine();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		
	
		// Send data to the server
		output.println(data);
		// Read data from the server
		try {
			result = input.readLine();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		System.out.println("Data = " + data + " --- Result = " + result);		
		
		
		}
	}
	
	void checkStartByteServer(String data, String result) {
		String[] cmdWords = data.split(" ");
		String command = cmdWords[0].toUpperCase();
		String[] resultWords = data.split(" ");
		
		
//		if(command == "PORT")
//		{
//			byteClient.startByteClient(Integer.parseInt(cmdWords[1]));//launch with specified port
//			System.out.println("ByteClient started on active mode with portnum " + cmdWords[1]);
//		}
//		
//		if(command == "PASV" && resultWords[0]=="227")
//		{
//			byteClient.startByteClient(Integer.parseInt(resultWords[4]));//assuming the string "227 Entering Passive Mode portnum"
//			System.out.println("ByteClient started on passive mode with portnum " + resultWords[4]);
//		}
				
	}
}
