package base;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class CharacterServer {
	
	private static CharacterServer instance;
	
	private  ServerSocket sServ;
	private  Socket sCon;
	private int port = 21;
	
	private BufferedReader input;
	private PrintWriter output;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	
	public ServerSocket getServerSocket() {
		return sServ;
	}

	public void setServerSocket(ServerSocket sServ) {
		this.sServ = sServ;
	}

	public static CharacterServer getInstance() {
		
		if (instance == null) {
            instance = new CharacterServer();
        }
        return instance;
	}
	
	CharacterServer()
	{
		//openConnection(); //To initialize the connection directly when creating the server instance.
	}
	
	public void openConnection()
	{
		
		try {
			
			// Create the socket
			setServerSocket(new ServerSocket(port)); 
			System.out.println("Character Server waiting for requests");

			
			// Accept a connection and create the socket for the transmission with the client
			sCon = getServerSocket().accept();
			System.out.println("Connection accepted");

			// Get the input/output from the socket
			input = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
			output = new PrintWriter(sCon.getOutputStream(), true);
			
			listen();
				
			

			
		} catch (SocketException se) {
			System.out.println("Socket Error: " + se);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void closeConnection() {
		try {
		// Close the IO
		input.close();
		output.close();

		// Close the socket
		sCon.close();
		// Close the server socket
		getServerSocket().close();
		} catch (SocketException se) {
			System.out.println("Socket Error: " + se);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
  
	public void listen() {
		String data = "";
		while (data.compareTo("END") != 0) {
			// Read the data sent by the client
			try {
				data = input.readLine();
			} catch (IOException e) {				
				e.printStackTrace();
			}
			System.out.println("Server receives: " + data);

			
			CommandHandler.interpreter(data, output);
			

			// Send the text
			output.println(data);
		}
		
		closeConnection();
	}
  
  
	
}
