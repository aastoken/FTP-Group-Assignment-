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
	
	
	public static CharacterServer getInstance() {
		
		if (instance == null) {
            instance = new CharacterServer();
        }
        return instance;
	}
	
	public void startConnection()
	{
		String data = "";

		try {

			// Create the socket
			sServ = new ServerSocket(port);
			System.out.println("Character Server waiting for requests");

			while (data.compareTo("END") != 0) {
				// Accept a connection and create the socket for the transmission with the
				// client
				sCon = sServ.accept();
				System.out.println("Connection accepted");

				// Get the input/output from the socket
				input = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
				output = new PrintWriter(sCon.getOutputStream(), true);

				// Read the data sent by the client
				data = input.readLine();
				System.out.println("Server receives: " + data);

				while (data != null) {
					if (readCommand(data)) {
						output.println("Exiting ftp application with command:  " + data);
						break;
					}
				}

				// Send the text
				output.println(data);

				// Close the IO
				input.close();
				output.close();

				// Close the socket
				sCon.close();
			}

			// Close the server socket
			sServ.close();
		} catch (SocketException se) {
			System.out.println("Socket Error: " + se);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
	
	private boolean readCommand(String cmd) {
		String[] words = cmd.split(" ");
		String command = words[0].toUpperCase();
		
		//TODO: MAKE A LOOP HERE 

		switch (command) {
		case "PORT":
			portCommand(words[1], true);
			break;
		case "LIST":
			break;
		case "RETR":
			break;
		case "STOR":
			break;
		case "QUIT":
			return true;
		default:
			output.println("Error 500 mamawebo");
			return true;
		

		}
		return false;
	}
	
  private void portCommand (String number, boolean active)
  {
	  try {
		 if(active)
		 {
			 System.out.println("port successfully received");
		 }
		 int portNumber = Integer.parseInt(number);
		 sServ = new ServerSocket(portNumber);
		 Socket socket = sServ.accept();
		 input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		 output = new PrintWriter(socket.getOutputStream());
		 dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		 dataOut = new DataOutputStream(socket.getOutputStream());

	  }
	  catch(IOException e)
	  {
		  e.printStackTrace();
	  }
	  
  }
  
  
  
  
	
}
