package base;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public abstract class Connection {
	
	protected  ServerSocket serverSocket;
	protected  Socket socket;
	protected int port;
	protected boolean alive;//Controls the persistence of the listen() while loop.
	
	
	protected BufferedReader input;
	protected PrintWriter output;
	protected DataInputStream inputStream;
	protected DataOutputStream outputStream;
	
	
	public void setPort(int portNum) {
		port = portNum;
	}
	public int getPort(){
		return port;
	}
	public void kill() {
		alive = false;
	}
	public void setupConnection(int portNum) {
		port = portNum;
		try {
			// Create the socket
			serverSocket = new ServerSocket(port); 
			System.out.println("Control Connection waiting for requests");

			
			// Accept a connection and create the socket for the transmission with the client
			socket = serverSocket.accept();
			System.out.println("Connection accepted");
			
			// Get the input/output from the socket
			if(isControlConnection()){//Reads and prints text
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				output = new PrintWriter(socket.getOutputStream(), true);
			}
			else{//Reads and prints bytes		
				inputStream = new DataInputStream(socket.getInputStream());
				outputStream = new DataOutputStream(socket.getOutputStream());
			}
			
			alive = true;
			
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
		socket.close();
		// Close the server socket
		serverSocket.close();
		alive = false;
		} catch (SocketException se) {
			System.out.println("Socket Error: " + se);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void listen() {
		while(alive)
		{
			//listen to random shit bro
			//(Implement this method on each child class)
		}
	}
	
	
	
	public boolean isControlConnection()
	{
		return port==21?true:false;
	}
	
}