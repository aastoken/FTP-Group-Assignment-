package base;

// Example: Client that receives and sends characters
// CharacterClient.java
 
import java.net.*;
import java.io.*;

public class CharacterClient {

	public static void main(String args[]) {
		int port = 1400;
		Socket sCon = null;
		BufferedReader input;
		PrintWriter output;
		
		BufferedReader inputKeyboard;
		String data;
		String result;
		
		try {
			// Connect to the server
			sCon = new Socket("localhost", port);
			
			// Get the input/output from the socket
			input = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
			output = new PrintWriter(sCon.getOutputStream(), true);
			
			// Get text from the keyboard
			inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Write text (END to close the server): ");
			data = inputKeyboard.readLine();
			
			// Send data to the server
			output.println(data);
			// Read data from the server
			result = input.readLine();
			
			System.out.println("Data = " + data + " --- Result = " + result);		
			
			// Close the connection
			sCon.close();
		}  catch(IOException e) {
			System.out.println("Error: " + e);		
	   }
   } // main	   
} // class CharacterClient

