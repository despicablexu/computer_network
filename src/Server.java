

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	 private static ServerSocket server = null;
	 private static Socket client = null;
	 private static String name;
	 private static boolean flag = true;
	 private static DataInputStream dis = null;
	 private static DataOutputStream dos = null;
	 
	 static ServerGUI serverGUI = new ServerGUI();
	 
	 public static void main(String[] argvs) {
		 ServerThread st = new ServerThread();
		 try {
			// new a serversocket with port 8888
			server = new ServerSocket(8888);
			
			// show message in GUI
			serverGUI.ipJTF.setText(InetAddress.getLocalHost().getHostAddress());
			serverGUI.portJTF.setText(String.valueOf(server.getLocalPort()));
			
			} catch (IOException e) {
				serverGUI.messJTA.append("port occupied, initialize failure£¡\n");
			}
		 while (true) {
			
			try {
				serverGUI.messJTA.append("Wait for connecting...\n");
				// wait for client's connecting
				client = server.accept();
			 
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());
				
				// get client's name
				while (flag) {
					// client closes abnormally
					if (client.isClosed())
						break;
					
				    name = dis.readUTF();
				    if (st.checkp(name)) {
				        serverGUI.messJTA.append("Client IP£º" + client.getInetAddress()
				        		+ "\n[" + name + "] : enter chatting room.\n");
				        serverGUI.model.addElement(name);
				        
				        // indicate connection success
				        dos.writeUTF("TRUE");
				    	dos.flush();
				    	
				        flag = false;
				    } else {
				    	dos.writeUTF("FALSE");
				    	dos.flush();
				    }
				}
			} catch (IOException e) {
				serverGUI.messJTA.append("Wait for client's connecting...\n");
				e.printStackTrace();
			}
			
			// check whether client connects successfully
			if (client.isConnected()) {
				// get the client's IP and add its mess to GUI
				try {
					String clientPort = dis.readUTF();
					String IP = client.getInetAddress().getHostAddress();
					User user = new User(name, IP, clientPort);
					ServerThread.userMess.put(name, user);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			    // new a thread for every client
			    ServerThread sth = new ServerThread(client, name);
			    Thread th = new Thread(sth);
			    th.start();
			    
			    // jump out to wait for other connecting
			    flag = true;
			}
		}
	 }

}
