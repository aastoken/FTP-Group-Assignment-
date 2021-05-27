package functionality;

import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Logger {
	
	private final static List<String> logLines = new ArrayList<>();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void log(String log) {
        //System.out.println(log);
        logLines.add( "["+getTimeStamp()+"] "+log);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String line : logLines) {
            sb.append(line + "\n");
        }

        return sb.toString();
    }
    
    public static String getTimeStamp()
    {
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    	return sdf.format(timestamp);
    }
}
