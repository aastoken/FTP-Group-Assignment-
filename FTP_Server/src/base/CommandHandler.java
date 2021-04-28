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

public class CommandHandler{
	
	private CharacterServer charServ = CharacterServer.getInstance();
	
	public static void interpreter(String cmd, PrintWriter output) {
		String[] words = cmd.split(" ");
		String command = words[0].toUpperCase();
		
		//TODO: MAKE A LOOP HERE 

		switch (command) {
		case "PORT":
			setActiveMode(words[1]);
			break;
		case "LIST":
			break;
		case "RETR":
			break;
		case "STOR":
			break;
		case "QUIT":
			//this should call a function in the server to exit the loop. 
			break;
		default:
			output.println("Error 500 mamawebo");
			break;
		

		}
	}
	
	private static void setActiveMode (String portNum)
	  {
		  ByteServer.getInstance().startByteServer(Integer.parseInt(portNum));	  
	  }
}
