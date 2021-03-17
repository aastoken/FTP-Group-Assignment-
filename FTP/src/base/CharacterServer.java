package base;

// Example: Server that receive and sends characters. It converts the text to upper case.
// CharacterServer.java
 
import java.net.*;
import java.io.*;

public class CharacterServer {

  public static void main(String args[]) {
     int port = 1400;
     ServerSocket sServ;
     Socket sCon;
     BufferedReader input;
     PrintWriter output;
	
     String data = "";
	
     try {
	// Create the socket
	sServ = new ServerSocket(port);
	System.out.println("Character Server waiting for requests");
	
	while(data.compareTo("END") != 0) {
	   // Accept a connection  and create the socket for the transmission with the client
	   sCon = sServ.accept();
	   System.out.println("Connection accepted");
		
		// Get the input/output from the socket
	   input = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
	   output = new PrintWriter(sCon.getOutputStream(), true);
		
		// Read the data sent by the client
	   data =  input.readLine();
       System.out.println("Server receives: "+data);
		// Convert text to Upper Case
	   data = data.toUpperCase();
		// Send the text
	   output.println(data);
		
		// Close the socket
	   sCon.close();		
	}
		
	// Close the server socket
	sServ.close();
     } catch(IOException e) {
	System.out.println("Error: " + e);
       }
  } // main
} // class CharacterServer

