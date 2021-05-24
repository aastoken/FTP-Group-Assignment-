package functionality;

import static functionality.Constants.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class FileUtils {
	
	private static FileUtils instance;

    public static FileUtils getInstance() {
        if (instance == null) {
            instance = new FileUtils();
        }
        return instance;
    }
    
    //Methods
    
    public String getRootFolder()
    {
    	return ROOT_DATA;
    }
    public File[] listFilesFromDir(String folder)
    {
    	String rootFolder = getRootFolder();
    	String dirFolder = rootFolder + folder;
    	File dirFiles = new File(dirFolder);
    	//System.out.println(dirFolder + dirFiles);
    	return dirFiles.listFiles();
    }
    
    public static void writeLogs(Logger logs)
    {
    	try (PrintStream pStream = new PrintStream(Constants.LOG_FILE)) {
    		pStream.println(logs);
    		pStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
