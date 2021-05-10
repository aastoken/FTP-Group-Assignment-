package functionality;

import java.io.*;

//Add functionality to read file from socket and copy to server filesystem
class CopiaBinaria
{
	public static void main(String arg[])
	{
		BufferedReader keyboardInput;
		String originalFileName;
		String copyFileName;
		
		try
		{
			keyboardInput = new BufferedReader(new InputStreamReader(System.in));
			if(arg.length<1)
			{
				System.out.println("Write the name of the original file");
				
				originalFileName = keyboardInput.readLine();
			}
			else
			{
				originalFileName = arg[0];
				System.out.println("Input file: " + originalFileName);
			}
			
			if(arg.length<2)
			{
				System.out.println("Write the name of the file copy");
				copyFileName = keyboardInput.readLine();
			}
			else
			{
				copyFileName = arg[1];
				System.out.println("Output file: " + copyFileName);
			}
			
			FileInputStream original = new FileInputStream(originalFileName);
			BufferedInputStream originalBuffer = new BufferedInputStream(original);
			
			FileOutputStream  copy = new FileOutputStream (copyFileName);
			BufferedOutputStream copyBuffer = new BufferedOutputStream(copy);
			
			// Loop to read a file and write in another
			byte [] array = new byte[1000];
			int n_bytes = originalBuffer.read(array);
			while (n_bytes > 0)
			{
				copyBuffer.write(array,0,n_bytes);
				n_bytes=originalBuffer.read(array);
			}

			// Close the files
			originalBuffer.close();
			copyBuffer.close();
		}
		catch (Exception e)
		{
			System.out.println("Error to send text :" + e);
		}
	}
}