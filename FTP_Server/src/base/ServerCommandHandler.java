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

public class ServerCommandHandler{
	
	// Reference to the control server
	SrvControl srvControl;
	SrvData	srvData;
	
	public void setCtrlServer(SrvControl ctrl) {
		srvControl = ctrl;
	}
	
	public void setDataServer(SrvData data) {
		srvData = data;
	}
	
	public void interpreter(String cmd, PrintWriter output) {
		String[] words = cmd.split(" ");
		String command = words[0].toUpperCase();
		System.out.println("command data received: " + cmd);
		switch (command) {
		case "PORT":
			setActiveMode(Integer.parseInt(words[1]));//Format "PORT PORTNUM"
			break;
		case "PASV":
			setPassiveMode();
		case "LIST":
			break;
		case "RETR":
			break;
		case "STOR":
			break;
		case "QUIT":
			//this calls a function in the control server to exit the loop. 
			srvControl.kill();
			System.out.println("Server Control Connection Stopped");
			break;
		default:
			//output.println("Error 500 mamawebo not recognized -srv");
			break;
		

		}
	}
	
	private void setActiveMode (int portNum){
		System.out.println("Setting up data connection on port "+portNum);
		srvData.setupConnection(portNum);
		  
	}
	private void setPassiveMode () {
		srvData.setupConnection(ServerMain.defaultDataPort);//20 is the default data port
		
	}
}
