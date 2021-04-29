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

public class ServerMain {
	public static CharacterServer charServer;
	public boolean ctrlAlive = false;
	public boolean dataAlive = false;
	
	//Control Connection
	private  ServerSocket ctrlServerSocket;
	private  Socket ctrlSocket;
	private int ctrlPort = 21;
	
	private BufferedReader ctrlInput;
	private PrintWriter ctrlOutput;
	private DataInputStream ctrlInputStream;
	private DataOutputStream ctrlOutputStream;
	
	private CommandHandler cmdHandler;
	
	//Data Connection
	private  ServerSocket dataServerSocket;
	private  Socket dataSocket;
	private int dataPort = 20;
	
	private BufferedReader dataInput;
	private PrintWriter dataOutput;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	
	
	public static void main(String[] args) {		
		charServer = CharacterServer.getInstance();
		startCharacterServer();
		
		
	}

	//Create a CharacterServer
	private static void startCharacterServer() {
		charServer.openConnection();
	}
	
	private void setupControlConnection() {
		try {
		// Create the socket
		ctrlServerSocket = new ServerSocket(ctrlPort); 
		System.out.println("Control Connection waiting for requests");

		
		// Accept a connection and create the socket for the transmission with the client
		ctrlSocket = ctrlServerSocket.accept();
		System.out.println("Connection accepted");

		// Get the input/output from the socket
		ctrlInput = new BufferedReader(new InputStreamReader(ctrlSocket.getInputStream()));
		ctrlOutput = new PrintWriter(ctrlSocket.getOutputStream(), true);
		
		ctrlAlive = true;
		
		} catch (SocketException se) {
			System.out.println("Socket Error: " + se);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
	
	private void setupDataConnection() {
		try {
		// Create the socket
		dataServerSocket = new ServerSocket(dataPort);
		System.out.println("Data Connection waiting for requests");
		
		// Accept a connection and create the socket for the data transmission with the client
		dataSocket = dataServerSocket.accept();
		System.out.println("Data connection accepted");
		
		dataInput = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
		dataOutput = new PrintWriter(ctrlSocket.getOutputStream(), true);
		
		dataAlive = true;
		
		} catch (SocketException se) {
			System.out.println("Socket Error: " + se);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void setDataPort(int portNum) {
		dataPort = portNum;
	}
}
