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

public class CommandHandler{
	
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
			//this should call a function in the control server to exit the loop. 
			srvControl.kill();
			break;
		default:
			output.println("Error 500 mamawebo not recognized");
			break;
		

		}
	}
	
	private void setActiveMode (int portNum){
		  srvData.setupConnection(portNum);
		  
	}
	private void setPassiveMode () {
		srvData.setupConnection(ServerMain.defaultDataPort);//20 is the default data port
		
	}
}
