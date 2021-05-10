package main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	
	
	protected Socket controlSocket;
	protected Socket dataSocket;
	
	protected int port;
	protected boolean alive = true;
	
	
	protected BufferedReader input;
	protected PrintWriter output;
	
	protected BufferedReader inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
	
	
	protected DataInputStream inputStream;
	protected DataOutputStream outputStream;
	
	
	private int defaultDataPort = 20;
	
	public void setPort(int portNum) {
		port = portNum;
	}
	public int getPort(){
		return port;
	}
	
	public void runClient() {
		
		try {
			// Create the socket
			controlSocket = new Socket("localhost", 21);
			input = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
			output = new PrintWriter(controlSocket.getOutputStream(), true);
			System.out.println(input.readLine());
			
			
			while(alive) {
				
				System.out.println("Write a command (QUIT to end connection):");
				String command = inputKeyboard.readLine();
				output.println(command);
				
				interpretCommand(command);
				
			}
			
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void interpretCommand(String cmd) {
		try {
			String[] words = cmd.split(" ");
			String command = words[0].toUpperCase();

			switch (command) {
			case "PRT":
				int port = Integer.parseInt(words[1]);
				System.out.println(input.readLine());
				dataSocket = new Socket("localhost", port);
				break;
			case "PASV":
				System.out.println(input.readLine());
				dataSocket = new Socket("localhost", defaultDataPort);
				break;
			case "LIST":
				break;
			case "RETR":
				break;
			case "STOR":
				break;
			case "QUIT":
				System.out.println(input.readLine());
				closeConnection();
				break;
			default:
				System.out.println(input.readLine());
				break;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void closeConnection() {
		try {
			
			alive = false;
		
			// Close the IO
			input.close();
			output.close();
			
			// Close the socket
			controlSocket.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
