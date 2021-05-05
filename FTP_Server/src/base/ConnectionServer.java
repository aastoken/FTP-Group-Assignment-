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

public abstract class ConnectionServer {
	
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
	
	
	/**
	 * Opens the required sockets and sets up the input/output according to the portNum argument.
	 * Using port 21 automatically sets a control connection, using any other port sets a data connection.
	 * @param portNum
	 */
	public void setupConnection(int portNum) {
		port = portNum;
		try {
			// Create the socket
			serverSocket = new ServerSocket(port); 
			System.out.println("Creating Server Socket on port "+port);

			
			// Accept a connection and create the socket for the transmission with the client
			socket = serverSocket.accept();
			System.out.println("Connection accepted");
			
			// Get the input/output from the socket
			if(isControlConnection()){//Reads and prints text
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				output = new PrintWriter(socket.getOutputStream(), true);
				System.out.println("Control Connection waiting for requests");
			}
			else{//Reads and prints bytes		
				inputStream = new DataInputStream(socket.getInputStream());
				outputStream = new DataOutputStream(socket.getOutputStream());
				System.out.println("Data Connection waiting for requests");
				//Send back confirmation to client so that it can connect.
			}
			
			alive = true;
			
			} catch (SocketException se) {
				System.out.println("Socket Error: " + se);
			} catch (IOException e) {
				System.out.println("Error: " + e);
			}
		listen();
	}
	
	/**
	 * Closes the I/O and sockets associated to this connection
	 */
	public void closeConnection() {
		try {
		// Close the IO
		if (isControlConnection()) {
			input.close();
			output.close();
		}
		else {
			inputStream.close();
			outputStream.close();
		}
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
	
	
	/**
	 * Must be overwritten in the concrete child classes
	 */
	public void listen() {
		while(alive)
		{
			//listen to random shit bro
			//(Implement this method on each child class)
		}
	}
	
	public void sendString(String data) {
		if(output != null) {
			output.println(data);
		}
	}
	
	public void sendData(byte[] data) throws IOException {
		if(outputStream != null) {
			outputStream.write(data);
		}
	}
	
	/**
	 * @return true if the port is 21, false otherwise.
	 */
	public boolean isControlConnection()
	{
		return port==21?true:false;
	}
	
}
