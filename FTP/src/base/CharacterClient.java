package base;

// Example: Client that receives and sends characters
// CharacterClient.java
 
import java.net.*;
import java.io.*;

public class CharacterClient {
	
	int port = 21;
	Socket sCon = null;
	BufferedReader input;
	PrintWriter output;
	
	BufferedReader inputKeyboard;
	String data = "";
	String result;
	
	ByteClient byteClient = new ByteClient();
	
	public void startCharacterClient() {
		
		
		try {
			
				// Connect to the server
				sCon = new Socket("localhost", port);
				
				// Get the input/output from the socket
				input = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
				output = new PrintWriter(sCon.getOutputStream(), true);
				while(data.compareTo("END") != 0)
				{
				// Get text from the keyboard
				inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Write text (END to close the server): ");
				data = inputKeyboard.readLine();
				
				
			
				// Send data to the server
				output.println(data);
				// Read data from the server
				result = input.readLine();
				
				System.out.println("Data = " + data + " --- Result = " + result);		
				
				checkStartByteServer(data, result);
				}
				// Close the connection
				sCon.close();
			
		}  catch(IOException e) {
			System.out.println("Error: " + e);		
	   }
   } // main	
	
	void checkStartByteServer(String data, String result) {
		String[] cmdWords = data.split(" ");
		String command = cmdWords[0].toUpperCase();
		String[] resultWords = data.split(" ");
		
		
		if(command == "PORT")
		{
			byteClient.startByteClient(Integer.parseInt(cmdWords[1]));//launch with specified port
			System.out.println("ByteClient started with portnum " + cmdWords[1]);
		}
		
		if(command == "PASV" && resultWords[0]=="227")
		{
			byteClient.startByteClient(Integer.parseInt(resultWords[4]));//assuming the string "227 Entering Passive Mode portnum"
			System.out.println("ByteClient started with portnum " + resultWords[4]);
		}
				
	}
} // class CharacterClient

