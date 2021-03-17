package base;

// Example: Client that receives and sends bytes
// ByteClient
 
import java.net.*;
import java.io.*;

public class ByteClient {

	public static void main(String args[]) {
		int port = 1300;
		Socket sCon = null;
		DataInputStream input;
		DataOutputStream output;
		
		BufferedReader inputKeybord;
		int data;
		float result;
		
		try {
			// Connect to the server
			sCon = new Socket("localhost", port);
			
			// Get the input/output from the socket
			input = new DataInputStream(sCon.getInputStream());
			output = new DataOutputStream(sCon.getOutputStream());
			
			// Get text from the keyboard
			inputKeybord = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Write a number (0 to close the server): ");
			data = Integer.parseInt(inputKeybord.readLine());
			
			// Send data to the server
			output.writeInt(data);
			// Read data from the server
			result = input.readFloat();
			
			System.out.println("Data = " + data + " --- Result = " + result);		
			
			// Close the connection
			sCon.close();

		} catch(NumberFormatException ne) {
			System.out.println("Wrong number: "+ne);
		} catch(IOException e) {
			System.out.println("Error: " + e);
		}
	} // main
} // clase ByteClient

