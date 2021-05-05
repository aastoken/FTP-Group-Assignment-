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

public abstract class ConnectionClient {
	
	
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
			socket = new Socket("localhost", port);
			

			
			// Get the input/output from the socket
			if(isControlConnection()){//Reads and prints text
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				output = new PrintWriter(socket.getOutputStream(), true);
				System.out.println("Control Connection ready to send requests, type something");
			}
			else{//Reads and prints bytes		
				inputStream = new DataInputStream(socket.getInputStream());
				outputStream = new DataOutputStream(socket.getOutputStream());
				System.out.println("Data Connection ready");
			}
			
			alive = true;
			
			} catch (SocketException se) {
				System.out.println("Socket Error: " + se);
			} catch (IOException e) {
				System.out.println("Error: " + e);
			}
		listen();
	}
	
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
		
		alive = false;
		} catch (SocketException se) {
			System.out.println("Socket Error: " + se);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void listen() {
//		while(alive)
//		{
			//listen to random shit bro
			//(Implement this method on each child class)
//		}
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
	
	public boolean isControlConnection()
	{
		return port==21?true:false;
	}
	
}
