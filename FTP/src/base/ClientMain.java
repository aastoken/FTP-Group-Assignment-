package base;

public class ClientMain {

	//Connections
	public static CliControl cliControl;
	public static CliData cliData;
	
	public static int defaultControlPort = 21;
	public static int defaultDataPort = 20;
	
	public static void main(String[] args) {
		
		cliControl = new CliControl();
		cliControl.setupConnection(defaultControlPort);
		cliControl.listen();

	}

}
