
public class ProtocolHandler {
	
	// get command type
	public static String getType(String s) {
		// separate every line
		String[] line = s.split("\\$");
		// get command
		String[] firstLine = line[0].split(" ");
		
		return firstLine[1];
	}
	
	// STATUS: return status
	public static String getStatus(String message) {
		// separate every line
		String[] line = message.split("\\$");
		// the index of status is 2
		String[] firstLine = line[0].split(" ");
		
		return firstLine[1];
	}
	
	// LIST: return entity
	public static String getList(String message) {
		// separate every line
		String[] line = message.split("\\$");

		String list = "";
		for (int i = 4; i < line.length; i++) {
			list = list + line[i] + "$";
		}
		
		return list;
	}
	
	// UPDATE: return name, status and date.
	public static String[] getUpdate(String message) {
		// separate every line
		String[] line = message.split("\\$");
		
		String[] firstLine = line[0].split(" ");
		String[] headerLine = line[1].split(" ");
		String[] dst = new String[3];
		dst[0] = firstLine[3];	// name
		dst[1] = firstLine[2];	// status
		dst[2] = headerLine[1];	// date
		return dst;
	}
	
	// MESSAGE && CSMESSAGE && P2PMESSAGE: return entity
	// STATUS: return status
	public static String getEntityText(String message) {
		// separate every line
		String[] line = message.split("\\$");
		
		return line[line.length-1];
	}
}
