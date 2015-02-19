import java.io.DataInputStream;
import java.io.IOException;
import java.util.Hashtable;


public class ClientThread implements Runnable {
	
	DataInputStream dis;

	// store online client getting from server
	public static Hashtable<String, User> userList = new Hashtable<String, User>();
	
	public void setDataInputStream(DataInputStream dis) {
		this.dis = dis;
	}

	public void run() {
		
		while (true) {
			try {
				// read message
				String mess = dis.readUTF();
				String command = ProtocolHandler.getType(mess);

				// print online users
				if (command.equals("LIST")) {
					Client.clientGUI.model.clear();
					
					String[] list = ProtocolHandler.getList(mess).split("\\$");
					// get every client
					for (int i = 0; i < list.length; i++) {
						
						String[] attr = list[i].split(" ");
						// do not add himself to online list
						if (attr[0].equals(Client.clientGUI.hostJTF.getText())) 
							continue;
						
						Client.clientGUI.model.addElement(attr[0]);
						User user = new User(attr[0], attr[1], attr[2]);
						userList.put(attr[0], user);
					}
					
				} 
				else if (command.equals("CSMESSAGE")) {
					String text = ProtocolHandler.getEntityText(mess);
					Client.clientGUI.pubMess.append(text);
				}
				else if (command.equals("UPDATE")) {
					String[] t = ProtocolHandler.getUpdate(mess);
					if (t[1].equals("TRUE"))
						Client.clientGUI.pubMess.append(t[2] + "\t"
								+ "\n[System]: " + t[0] + " joins in chatting room.\n\n");
					else
						Client.clientGUI.pubMess.append(t[2] + "\t"
								+ "\n[System]: " + t[0] + " leaves chatting room.\n\n");
				}
    
			} catch (IOException e) {
				// fail to connect server
				Client.clientGUI.pubMess.append("connection problem!\n");
				break;
			}
		}
	}
	
}
