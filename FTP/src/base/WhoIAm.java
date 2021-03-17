package base;

// InetAddress Example
// WhoIAm.java
 
import java.net.*;

public class WhoIAm {

	public static void main(String args[]) {
		InetAddress host;
		try {
			if(args.length < 1)
				host = InetAddress.getLocalHost();
			else
				host = InetAddress.getByName(args[0]);
			
			System.out.println("Host name: " + host.getHostName());
			System.out.println("IP Address: " + host.getHostAddress());
		} catch(UnknownHostException e) {
			System.out.println("Unknow host: "+e);
		}
	} // main
} // class WhoIAm

