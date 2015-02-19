import java.text.SimpleDateFormat;
import java.util.Date;


public class ProtocolMaker {
	
	// make status protocol
	public static String statusP(String status, String entity) {
		String message = "";
		// add request line
		message = "CS1.0 STATUS " + status + "$";
		// add header line, first:Date, sec:Content-Length
		message = message + "Date: " + getDate() + "$";
		message = message + "Content-Length: " + entity.length() + "$$";
		// add entity body
		message = message + entity;
		
		return message;
	}
	
	// make list protocol
	public static String listP(String entity) {
		String message = "";
		// add request line
		message = "CS1.0 LIST " + "$";
		// add header line, first:Date, sec:Content-Length
		message = message + "Date: " + getDate() + "$";
		message = message + "Content-Length: " + entity.length() + "$$";
		// add entity body
		message = message + entity;
		
		return message;
	}
	
	// make getlist protocol
	public static String getListP() {
		String message = "";
		// add request line
		message = "CS1.0 GETLIST " + "$";
		// add header line, first:Date
		message = message + "Date: " + getDate() + "$$";
		
		// entity body is null
		
		return message;
	}
	
	// make update protocol
	public static String updateP(String name, String status) {
		String message = "";
		// add request line
		message = "CS1.0 UPDATE " + status + " " + name + "$";
		// add header line, first:Date
		message = message + "Date: " + getDate() + "$$";
		
		// entity body is null
		
		return message;
	}
	
	// make leave protocol
	public static String leaveP(String name) {
		String message = "";
		// add request line
		message = "CS1.0 LEAVE " + name + "$";
		// add header line, first:Date
		message = message + "Date: " + getDate() + "$$";
		
		// entity body is null
		
		return message;
	}
	
	// make message(minet->miro) protocol
	public static String messageP(String name, String entity) {
		String message = "";
		// add request line
		message = "CS1.0 MESSAGE " + name + "$";
		// add header line, first:Date, sec:Content-Length
		message = message + "Date: " + getDate() + "$$";
		message = message + "Content-Length: " + entity.length() + "$$";
		// add entity body
		message = message + entity;
		
		return message;
	}
	
	// make csmessage(miro->minet) protocol
	public static String csmessageP(String name, String entity) {
		String message = "";
		// add request line
		message = "CS1.0 CSMESSAGE " + name + "$";
		// add header line, first:Date, sec:Content-Length
		message = message + "Date: " + getDate() + "$$";
		message = message + "Content-Length: " + entity.length() + "$$";
		// add entity body
		message = message + entity;
		
		return message;
	}
	
	// make beat protocol
	public static String beatP(String name) {
		String message = "";
		// add request line
		message = "CS1.0 BEAT " + name + "$";
		// add header line, first:Date, sec:Content-Length
		message = message + "Date: " + getDate() + "$$";
		
		// entity body is null
		
		return message;
	}
	
	// make p2pmessage(minet->minet) protocol
	public static String p2pmessageP(String name, String entity) {
		String message = "";
		// add request line
		message = "P2P1.0 P2PMESSAGE " + name + "$";
		// add header line, first:Date, sec:Content-Length
		message = message + "Date: " + getDate() + "$$";
		message = message + "Content-Length: " + entity.length() + "$$";
		// add entity body
		message = message + entity;
		
		return message;
	}

	public static String getDate() {
		Date nowTime = new Date();
		String pattern = "HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String timePattern = sdf.format(nowTime);
		// return current time
		return timePattern;
	}
}
