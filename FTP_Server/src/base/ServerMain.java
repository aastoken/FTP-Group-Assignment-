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
import java.net.SocketException;

public class ServerMain {
	
	//Connections
	public static SrvControl srvControl;
	public static SrvData srvData;
	public static ServerCommandHandler cmdHandler;
	
	public static int defaultControlPort = 21;
	public static int defaultDataPort = 20;

	public static void main(String[] args) {		
		
		cmdHandler = new ServerCommandHandler();
		srvControl = new SrvControl();
		srvData = new SrvData();
		
		//The command handler needs access to these connection instances in order to perform operations on them.
		cmdHandler.setCtrlServer(srvControl);
		cmdHandler.setDataServer(srvData);
		
		
		//Start of the control connection
		srvControl.setupConnection(defaultControlPort);
		
		
	}

	
	
}
