import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.swing.JOptionPane;


public class P2PThread implements Runnable {
	private Socket guest = null;
	private String name = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private static Socket activeSendSocket; 
	
	public static Hashtable<String, P2PThread> connectedGuestList = new Hashtable<String, P2PThread>();
	
	public P2PThread(Socket guess, String name) {
	try {
		this.guest = guess;
		this.name = name;
		dis = new DataInputStream(guess.getInputStream());
		dos = new DataOutputStream(guess.getOutputStream());
	} catch (IOException e) {
	}
	}
	
	public void run() {
		try {
			// add current to hash table and show it in GUI
			connectedGuestList.put(name, this);
			
			while (true) {

			    // read message
			    String mess = dis.readUTF();
			    // get command type
			    String command = ProtocolHandler.getType(mess);
			    
			    if (command.equals("P2PMESSAGE")) {
			    	String text = ProtocolHandler.getEntityText(mess);
			    	Client.clientGUI.pvtMess.append(text);
			    }
			    if (command.equals("LEAVE"))
			    	break;

			}
			guest.close();
		    } catch (Exception e) {

		    } finally {
			    // delete the client that disconnected
			    connectedGuestList.remove(name);
			    Client.clientGUI.guessJL.setText("");
			}
	}
	
	/** send message to guest */
	public static void sendGuest(String sender, String receiver, String mess) {
		P2PThread st = connectedGuestList.get(receiver);
		if (st == null) {
			connectGuest(receiver);
		}
		else {
			try {
				String text = getDate() + "\n" + sender + " : \t" + mess + "\n\n";
				st.dos.writeUTF(ProtocolMaker.p2pmessageP(sender, text));
				st.dos.flush();
	
			} catch (IOException e) {
				// if something wrong, interrupt it
				Thread th = new Thread(st);
				th.interrupt();
			}
		}
	}
	
	/** connect to a guess */
	public static void connectGuest(String receiver) {
		
		String IP = ClientThread.userList.get(receiver).getIP();
		int port = Integer.parseInt(ClientThread.userList.get(receiver).getPort());
		
		try {
			// connect to specified online user
			activeSendSocket = new Socket(IP, port);
			
			// connect friend success?
			if (activeSendSocket.isConnected()) {
				// success then new a thread
				P2PThread p2pTh = new P2PThread(activeSendSocket, receiver);
				connectedGuestList.put(receiver, p2pTh);
				Thread p = new Thread(p2pTh);
				p.start();
				
				// send its name to receiver
				p2pTh.dos.writeUTF(Client.clientGUI.hostJTF.getText());
				p2pTh.dos.flush();
				
				// connecting success, send message
				P2PThread.sendGuest(Client.clientGUI.hostJTF.getText(), 
						receiver, Client.clientGUI.mess);
				
			} else {
				// show connection failure message
				JOptionPane.showMessageDialog(null, 
						"Fail to connect with "+ receiver, 
						"Attention", 
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
		}
	
	}
	
	
	/** get time */
	public static String getDate() {
		Date nowTime = new Date();
		String pattern = "HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String timePattern = sdf.format(nowTime);
		return timePattern;
	}
}
