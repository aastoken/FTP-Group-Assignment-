package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	
	//private final String defaultPath = "C:\\Users\\pedro\\git\\FTP-Group-Assignment-\\FTP_Client_v2\\root_data\\";
	private final String defaultPath = System.getProperty("user.dir") + "\\root_data\\";
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
				System.out.println(input.readLine());
				break;
			case "RETR":
				
				// Elegir como llamar al archivo
				// Se puede usar un substring del path
				String fileName = words[1];
				retrCommand(defaultPath + fileName);
				System.out.println(input.readLine());
				// Cerrar la conexión
				dataSocket.close();
								
				break;
			case "STOR":
				
				// Elegir como llamar al archivo
				// Se puede usar un substring del path
				String fileName2 = words[1];
				storCommand(defaultPath + fileName2);
				System.out.println(input.readLine());
				// Cerrar la conexión
				dataSocket.close(); 
				
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
	
	private void retrCommand(String path) {
		// Gestionar los bytes que recibimos del server en el socket de datos
		// Generar un archivo donde copiamos los bytes
		try {
			File downloadedFile = new File(path);
			downloadedFile.createNewFile();
			
			BufferedInputStream originalBuffer = new BufferedInputStream(dataSocket.getInputStream());
			
			FileOutputStream  copy = new FileOutputStream (downloadedFile);
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
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void storCommand(String path) {
		try {
			
			// 1 - Coger datos desde archivo
			
			FileInputStream original = new FileInputStream(path);
			BufferedInputStream originalBuffer = new BufferedInputStream(original);
			
			// 2 - Mandarlo por el socket de datos
						
			BufferedOutputStream copyBuffer = new BufferedOutputStream(dataSocket.getOutputStream());
						
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
			
		}catch(Exception e) {
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
