package base;

public class SrvData extends ConnectionServer{

	//listen() auto-executes after the setupConnection() is complete.
	public void listen(){ 
		System.out.println("DataServer Listening");
		
		
		//Insert here methods to execute while the data connection is active
//		while (alive)
//		{
//			
//		}
		
		
		super.closeConnection();
	}
}
