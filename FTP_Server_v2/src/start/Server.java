package start;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import functionality.Constants;
import functionality.FileUtils;
import functionality.Logger;
import functionality.StatusCodes;

public class Server {
	
	protected ServerSocket controlSocket;
	protected ServerSocket dataSocket;
	
	
	protected Socket clientControlSocket;
	protected Socket clientDataSocket;
	
	
	protected boolean alive = true;//Controls the persistence of the listen() while loop.
	protected boolean usernameReceived = false;
	protected boolean authenticated = false; //Controls the authentication state
	
	protected BufferedReader input;
	protected PrintWriter output;
	
	protected DataInputStream inputStream;
	protected DataOutputStream outputStream;
	
	private int defaultDataPort = 20;
	
	
	//private final String defaultPath = "C:\\Users\\pedro\\git\\FTP-Group-Assignment-\\FTP_Server_v2\\root_data\\";
	
	
	private String currentFolder= "";
	private static final FileUtils file_utils = FileUtils.getInstance();
	private final String defaultPath = System.getProperty("user.dir") + "\\root_data\\";
	
	/**
	 * Opens the required sockets and sets up the input/output according to the portNum argument.
	 * Using port 21 automatically sets a control connection, using any other port sets a data connection.
	 * @param portNum
	 */
	public void runServer(){
		
		try {
			Logger.log("Client connected to the default port");
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
			
			Logger.log(String.format("Command: %s", cmd));
			if(!authenticated) {
				switch (command) {
					case "USER":
						if (words[1]!= null)
						{
							usernameReceived = true;
							output.println(StatusCodes.code_331);//Username ok need password
						}
						else output.println("ERROR: Username can't be empty");//This is actually useless because there's an out of bounds exception thrown before we can output anything.
					
						break;
				
					case "PASS":
						if(words[1]!= null && usernameReceived)
						{
							authenticated = true;
							output.println(StatusCodes.code_230);//User logged in
						}
						else output.println("ERROR: You need to provide a USER first");
						
						break;
					default:
						output.println(StatusCodes.code_530);//NOT LOGGED IN
						break;
				}
			}
			else{
				switch (command) {
				
				case "PRT":				
					//Abrir conexión de datos
					int port = Integer.parseInt(words[1]);
					openDataConnection(port);
					
					break;
				case "PASV":
					openDataConnection(defaultDataPort);
					
					break;
				case "LIST":
						listCommand();
					break;
				case "RETR":
					
					// Usar conexión de datos
					
					
					// Recuperar ruta del archivo
					String fileName = words[1];
					retrCommand(defaultPath + fileName);
					
					// Cerrar conexión de datos
					closeDataConnection();
					
					break;
				case "STOR":
					
					// Usar conexión de datos
					
					// ...
					String fileName2 = words[1];
					storCommand(defaultPath + fileName2);
					// Cerrar conexión de datos
					closeDataConnection();
					
					break;
				case "QUIT":
					output.println(StatusCodes.code_221);
					closeConnection();
					break;
				default:
					output.println(StatusCodes.code_500);
					break;
				}
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void closeConnection() {
		try {
			Logger.log("Client disconnected");
			alive = false;
			usernameReceived = false;
			authenticated = false;
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
	
	public void openDataConnection(int portNum) {
		//Abrir conexión de datos
		try {
		dataSocket = new ServerSocket(portNum);
		output.println(StatusCodes.code_200);
		clientDataSocket = dataSocket.accept();
		Logger.log(String.format("Client connected to port: %s", portNum));
		}
		catch(BindException be)
		{
			Logger.log("Trying to connect to an alredy bind socket");
		}
		catch (IOException e) {
			output.println(StatusCodes.code_425);
            e.printStackTrace();
        }
	}
	
	public void closeDataConnection() {
		try {
			
			// Close the socket
			clientDataSocket.close();
			dataSocket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	private void listCommand()
	{
	
		File[]	listedFiles = file_utils.listFilesFromDir(currentFolder);
		
		System.out.println(listedFiles);
		
		output.println(StatusCodes.code_150);
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
		Logger.log(String.format("User listed the files from %s", defaultPath));
		output.println(StatusCodes.code_226);
		output.println(sb.toString());
	}
	
	private void retrCommand(String path){
		try {
			
			// 1 - Coger datos desde archivo
			System.out.println(path);
			FileInputStream original = new FileInputStream(path);
			BufferedInputStream originalBuffer = new BufferedInputStream(original);
			
			// 2 - Mandarlo por el socket de datos
			
			BufferedOutputStream copyBuffer = new BufferedOutputStream(clientDataSocket.getOutputStream());
			
			// Loop to read a file and write in another
			byte [] array = new byte[1000];
			int n_bytes = originalBuffer.read(array);
			while (n_bytes > 0)
			{
				copyBuffer.write(array,0,n_bytes);
				n_bytes=originalBuffer.read(array);
			}

			// Close the files
			originalBuffer.close();
			copyBuffer.close();
			
			Logger.log(String.format("User downloaded file: %s", path));
			// 3 - Una vez que se haya mandado el archivo entero --> mandar el código por el socket de control
			output.println("success");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void storCommand(String path) {
		try {
			
			// Gestionar los bytes que recibimos del server en el socket de datos
			// Generar un archivo donde copiamos los bytes
				File storeFile = new File(path);
				storeFile.createNewFile();
				
				BufferedInputStream originalBuffer = new BufferedInputStream(clientDataSocket.getInputStream());
				
				FileOutputStream  copy = new FileOutputStream (storeFile);
				BufferedOutputStream copyBuffer = new BufferedOutputStream(copy);
				
				// Loop to read a file and write in another
				byte [] array = new byte[1000];
				int n_bytes = originalBuffer.read(array);
				while (n_bytes > 0)
				{
					copyBuffer.write(array,0,n_bytes);
					n_bytes=originalBuffer.read(array);
				}
				
				originalBuffer.close();
				copyBuffer.close();
				
				Logger.log(String.format("User uploaded file: %s", path));
				
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
