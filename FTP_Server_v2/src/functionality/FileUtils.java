package functionality;

import static functionality.Constants.*;

import java.io.File;

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
}
