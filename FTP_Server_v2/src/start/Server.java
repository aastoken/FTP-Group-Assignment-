package start;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import functionality.FileUtils;
import functionality.StatusCodes;

public class Server {
	
	protected ServerSocket controlSocket;
	protected ServerSocket dataSocket;
	
	
	protected Socket clientControlSocket;
	protected Socket clientDataSocket;
	
	
	protected boolean alive = true;//Controls the persistence of the listen() while loop.
	
	
	protected BufferedReader input;
	protected PrintWriter output;
	
	protected DataInputStream inputStream;
	protected DataOutputStream outputStream;
	
	private int defaultDataPort = 20;
	
	private String currentFolder= "";
	private static final FileUtils file_utils
    = FileUtils.getInstance();
	
	
	/**
	 * Opens the required sockets and sets up the input/output according to the portNum argument.
	 * Using port 21 automatically sets a control connection, using any other port sets a data connection.
	 * @param portNum
	 */
	public void runServer(){
		
		try {
			// Create the socket
			controlSocket = new ServerSocket(21); 
			System.out.println("Server waiting for connection on port 21");

			
			// Accept a connection and create the socket for the transmission with the client
			clientControlSocket = controlSocket.accept();
			System.out.println("Connection accepted");
			
			// Get the input/output from the socket
			
			input = new BufferedReader(new InputStreamReader(clientControlSocket.getInputStream()));
			output = new PrintWriter(clientControlSocket.getOutputStream(), true);
			
			output.println("220 Service ready for new user.");
			
			while(alive) {
				
				String command = input.readLine();
				System.out.println("Received command: "+command);
				interpretCommand(command);
				
			}
			
			//System.out.println("Server waiting for commands");
			
			
		/*else{//Reads and prints bytes		
				inputStream = new DataInputStream(socket.getInputStream());
				outputStream = new DataOutputStream(socket.getOutputStream());
				System.out.println("Data Connection waiting for requests");
				//Send back confirmation to client so that it can connect.
			}
		*/	

			
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
				//Abrir conexi�n de datos
				int port = Integer.parseInt(words[1]);
				openDataConnection(port);
				
				break;
			case "PASV":
				openDataConnection(defaultDataPort);
				
				break;
			case "LIST":
				ListCommand();
				break;
			case "RETR":
				
				// Usar conexi�n de datos
				
				// ...
				
				// Cerrar conexi�n de datos
				
				break;
			case "STOR":
				
				// Usar conexi�n de datos
				
				// ...
				
				// Cerrar conexi�n de datos
				
				break;
			case "QUIT":
				output.println(StatusCodes.code_221);
				closeConnection();
				break;
			default:
				output.println(StatusCodes.code_500);
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
			clientControlSocket.close();
			controlSocket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openDataConnection(int portNum) throws Exception {
		//Abrir conexi�n de datos
		dataSocket = new ServerSocket(portNum);
		output.println(StatusCodes.code_200);
		clientDataSocket = dataSocket.accept();
	}
	
	private void ListCommand()
	{
		File[] listedFiles = file_utils.listFilesFromDir(currentFolder);
		System.out.println(listedFiles);
		StringBuilder sb = new StringBuilder();
		
		for(File singleFile : listedFiles)
		{
			sb.append(singleFile.getName());
            sb.append(" ");
            if(singleFile.isDirectory())
            {
            	sb.append("(<-dir)");
            	sb.append(" ");
            }
            
		}
		output.println(sb.toString());
	}
	
	
}
