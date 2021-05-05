package base;

public class ClientMain {

	//Connections
	public static ClientCommandHandler cmdHandler;
	public static CliControl cliControl;
	public static CliData cliData;
	
	public static int defaultControlPort = 21;
	public static int defaultDataPort = 20;
	
	public static void main(String[] args) {
		cmdHandler = new ClientCommandHandler();
		cliControl = new CliControl();
		cliData = new CliData();
		
		//The command handler needs access to these connection instances in order to perform operations on them.
		cmdHandler.setCtrlServer(cliControl);
		cmdHandler.setDataServer(cliData);
		
		
		cliControl = new CliControl();
		cliControl.setupConnection(defaultControlPort);
		

	}

}
