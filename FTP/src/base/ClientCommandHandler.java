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

public class ClientCommandHandler{
	
	// Reference to the control server
	CliControl cliControl;
	CliData	cliData;
	
	public void setCtrlServer(CliControl ctrl) {
		cliControl = ctrl;
	}
	
	public void setDataServer(CliData data) {
		cliData = data;
	}
	
	public void interpreter(String cmd, PrintWriter output) {
		String[] words = cmd.split(" ");
		String command = words[0].toUpperCase();

		switch (command) {
		case "PORT":
			setActiveMode(Integer.parseInt(words[1]));//Format "PORT PORTNUM"
			break;
		case "PASV":
			setPassiveMode();// wait for passive mode
		case "LIST":
			break;
		case "RETR":
			break;
		case "STOR":
			break;
		case "QUIT":
			//this calls a function in the control server to exit the loop. 
			cliControl.kill();
			System.out.println("Server Control Connection Stopped");
			break;
		default:
			//output.println("Error 500 mamawebo not recognized -cli");
			break;
		

		}
	}
	
	private void setActiveMode (int portNum){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cliData.setupConnection(portNum);
		  
	}
	private void setPassiveMode () {
		cliData.setupConnection(ClientMain.defaultDataPort);//20 is the default data port
		
	}
}
