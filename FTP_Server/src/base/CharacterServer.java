package base;

// Example: Server that receive and sends characters. It converts the text to upper case.
// CharacterServer.java

import java.net.*;

import java.io.*;

public class CharacterServer {

	private static ServerSocket sServ;
	private static Socket sCon;

	private BufferedReader input;
	private PrintWriter output;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;

	public void main(String args[]) {

//		ServerSocket sServ;
//		Socket sCon;
		int port = 1400;

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
	} // main

	private boolean readCommand(String cmd) {
		String[] words = cmd.split(" ");
		words[0].toUpperCase();

		switch (words[0]) {
		case "PORT":
			System.out.println("port command succesful");
			// portCommand(words[1], true, s, null, null);
			// portCommand(words[1], true);
			break;
		case "QUIT":
			return true;
		default:
			System.out.println("Error 500 mamawebo");

		}
		return false;
	}

//  private void portCommand (String number, boolean active)
//  {
//	  try {
//		 if(active)
//		 {
//			 System.out.println("port succesfully received");
//		 }
//		 int portNumber = Integer.parseInt(number);
//		 sServ = new ServerSocket(portNumber);
//		 Socket socket = sServ.accept();
//		 input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		 output = new PrintWriter(socket.getOutputStream());
//		 
//		 
//		 
//	  }
//	  catch(IOException e)
//	  {
//		  e.printStackTrace();
//	  }
//	  
//  }
} // class CharacterServer
