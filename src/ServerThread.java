

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;


public class ServerThread implements Runnable {
	Socket client = null;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	
	// hold current thread name
	String name = null;
	// new a hashtable to store all thread object of clients
	public static Hashtable<String, ServerThread> clientlist = new Hashtable<String, ServerThread>();
	public static Hashtable<String, User> userMess = new Hashtable<String, User>();
	
	public ServerThread() {
	}
	
	public ServerThread(Socket client, String name) {
	try {
		this.client = client;
		this.name = name;
		
		dis = new DataInputStream(client.getInputStream());
		dos = new DataOutputStream(client.getOutputStream());
		
		} catch (IOException e) {
		}
	}
	
	public void run() {
	    try {
		// add current to hash table and show it in GUI
		clientlist.put(name, this);
		
		// send online clients
		getList();
		// send login message to all clients
		sendAllClient(ProtocolMaker.updateP(name, "TRUE"));
		
		while (true) {
			// set timeout
			client.setKeepAlive(true); 
			client.setOOBInline(true);
			client.setSoTimeout(30000);
			
		    // read message from 
		    String mess = dis.readUTF();
		    String command = ProtocolHandler.getType(mess);

		    if (command.equalsIgnoreCase("GETLIST")) {
		    	getList();
		    	Server.serverGUI.messJTA.append
		    			(getDate() + " [" + name + "] request online users.\n");
		    }
		    else if (command.equalsIgnoreCase("BEAT")) {
		    	// client still alive
		    	continue;
		    }
		    else if (command.equalsIgnoreCase("LEAVE")) {
		    	// jump out to close the socket connection
		    	break;
		    } 
		    else if (command.equalsIgnoreCase("MESSAGE")) {
		    	// get content
		    	String text = ProtocolHandler.getEntityText(mess);
		    	Server.serverGUI.messJTA.append
    					(getDate() + " [" + name + "] public message: "+ text + "\n");
		    	// send to all clients
		    	String toSend = getDate() + "\t" + "\n" + name + " : \t" + text + "\n\n";
		    	sendAllClient(ProtocolMaker.csmessageP(name, toSend));
		    }

		}
		client.close();
	    } catch (Exception e) {

	    } finally {
		    // delete client message
		    clientlist.remove(name);
		    // notify every online client
		    sendAllClient(ProtocolMaker.updateP(name, "FALSE"));
		    Server.serverGUI.model.removeElement(name);
		    Server.serverGUI.messJTA.append(getDate() + " [" + name + "] log off.\n");
		}
	}
	
	/** send message to all clients */
	public void sendAllClient(String mess) {
		// get all clients
		Enumeration<ServerThread> allclients = clientlist.elements();

		while (allclients.hasMoreElements()) {
			// has next£¿
			ServerThread st = (ServerThread) allclients.nextElement();
			try {
				// write to users
				st.dos.writeUTF(mess);
				st.dos.flush();
			} catch (IOException e) {
				Thread th = new Thread(st);
				th.interrupt();
			}
		}
	}
	
	/** get online list */
	public void getList() throws IOException {
		// get all clients
		Enumeration<String> checkname = clientlist.keys();
		// get the requesting client thread by name
		ServerThread st = clientlist.get(name);
		// message to write
		String mess = "";
  
		String port = null;
		String IP = null;
		
		while (checkname.hasMoreElements()) {
			// get next client
			String sn = (String) checkname.nextElement();
			// get IP and port
			IP = userMess.get(sn).getIP();
			port = String.valueOf(userMess.get(sn).getPort());
			
			// write in stream, separated by '$'
			mess = mess + sn + " " + IP + " " + port + "$";
			
		}
		// send list protocol
		st.dos.writeUTF(ProtocolMaker.listP(mess));
		st.dos.flush();
	}
	
	public boolean checkp(String str) {
		boolean flag = true;
		// get all client
		Enumeration<String> checkname = clientlist.keys();
		
		// check whether has this name
		while (checkname.hasMoreElements()) {
			String sn = (String) checkname.nextElement();
			
			if (str.equalsIgnoreCase(sn)) {
				flag = false;
			}
		}
		return flag;
	}
	
	/** get time */
	public String getDate() {
		Date nowTime = new Date();
		String pattern = "HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String timePattern = sdf.format(nowTime);
		// return current time
		return timePattern;
	}
}
