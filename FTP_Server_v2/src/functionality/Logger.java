package functionality;

import java.util.List;
import java.util.ArrayList;

public class Logger {
	private final static List<String> logLines = new ArrayList<>();
	
	public static void log(String log) {
        System.out.println(log);
        logLines.add(log);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String line : logLines) {
            sb.append(line + "\n");
        }

        return sb.toString();
    }
}
