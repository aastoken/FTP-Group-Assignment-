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
	
	public static CharacterServer charServer;
	
	//Create a CharacterServer
	public static void startCharacterServer() {
		charServer.openConnection();
	}
	
	public static void main(String[] args) {		
		charServer = CharacterServer.getInstance();
		startCharacterServer();
		
		
	}


}
