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
	
	public static int defaultControlPort = 21;
	public static int defaultDataPort = 20;

	public static void main(String[] args) {		
		
		srvControl = new SrvControl();
		
		srvControl.setupConnection(defaultControlPort);
		srvControl.listen();
		
	}

	
	
}
