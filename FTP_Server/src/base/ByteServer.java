package base;

// Server that receives and sends bytes.
// ByteServer
 
import java.net.*;
import java.io.*;

public class ByteServer {

	private static ByteServer instance;
	
	public static ByteServer getInstance() {
		
		if (instance == null) {
            instance = new ByteServer();
        }
        return instance;
	}
	
	int port = 20;
    ServerSocket sServ;
    Socket sCon;
    DataInputStream input;
    DataOutputStream output;
    
  public void startByteServer(int portNum) {
    int data = -1;
    port = portNum;
    try {
		// Create the socket
		sServ = new ServerSocket(port);
		System.out.println("Byte Server waiting for requests");
		
		while(data != 0) {
			// Accept a connection  and create the socket for the transmission with the client
			sCon = sServ.accept();
			System.out.println("Connection accepted");
			
			// Get the input/output from the socket
			input = new DataInputStream(sCon.getInputStream());
			output = new DataOutputStream(sCon.getOutputStream());
			
			// Read data sent by the client
			data =  input.readInt();
			System.out.println("Received: "+data);
			
			// Send the result to the client
			output.writeFloat(data);
			// Close the socket
			sCon.close();
		}    
		// Close the server socket
		sServ.close();
		} catch(IOException e) {
			System.out.println("Error: " + e);
		}
	} // main
} // class ByteServer

