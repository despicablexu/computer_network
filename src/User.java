
public class User {
	private String name;
	private String IP;
	private String port;
	
	public User(String name, String IP, String port) {
		this.name = name;
		this.IP = IP;
		this.port = port;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIP() {
		return IP;
	}
	
	public String getPort() {
		return port;
	}
}
