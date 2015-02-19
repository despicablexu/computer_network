import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;


public class Client {
	
	private static Socket client = null;
	private static DataInputStream clientDIS = null;
	private static DataOutputStream clientDOS = null;
	
	private boolean flag = true;
	private static String name = null;
	
	protected static ClientGUI clientGUI = new ClientGUI();
	protected static LoginGUI loginGUI = new LoginGUI();
	
	private boolean notifyWindowShow = false;

	public static void main(String argv[]) {
		
		// check if client click the login button
		while (true) {
			if (loginGUI.loginIsClick)
				break;
			// set pause
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		// start to connect server
		Client cc = new Client();
		cc.connect();
	}
	
	public void connect() {
		
		String IP = loginGUI.ipInJTF.getText();
		int port = 8888;
		
		try {
			
			try {
				// connect to server
				client = new Socket(IP, port);
				
				if (client.isConnected()) {
					run();
				} else {
					connect();
				}
			} catch (SocketException e) {
				// connect repeatedly
				connect();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, 
					"Connection error!", 
					"Attention", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void run() throws IOException {
		
		ClientThread readThread = new ClientThread();
		ClientBeat beatThread = new ClientBeat();
		
		// new a thread to read data from server
		Thread readData = new Thread(readThread);

		clientDIS = new DataInputStream(client.getInputStream());
		clientDOS = new DataOutputStream(client.getOutputStream());
		
		while (flag) {
			// get name
			name = loginGUI.getInput();
			
			// do nothing when no input
			if (name.length() == 0) {
				continue;
			}
			
			// sent name to server
			clientDOS.writeUTF(name);
			clientDOS.flush();
			
			// check this name whether can be used
			if (clientDIS.readUTF().equals("FALSE")) {
				
				// make sure the notify window don't show infinitely
				if (!notifyWindowShow) {
					JOptionPane.showMessageDialog(null, 
							"Name has already been occupied, try another!", 
							"Attention", 
							JOptionPane.ERROR_MESSAGE);
					notifyWindowShow = true;
				}
				continue;
			} else {
				notifyWindowShow = false;
				loginGUI.setVisible(false);
				clientGUI.setVisible(true);
			}
			
			// show hostname and ip message in GUI
			clientGUI.hostJTF.setText(name);
			clientGUI.ipJTF.setText(client.getInetAddress().getHostAddress());
			
			// start thread to read message from server
			readThread.setDataInputStream(clientDIS);
			readData.start();
			
			// start beat thread
			beatThread.setDataOutputStream(clientDOS);
			Thread sendBeat = new Thread(beatThread);
			sendBeat.start();
			
			MonitorThread mt = new MonitorThread(clientDOS);
			Thread mtt = new Thread(mt);
			mtt.start();

			flag = false;
		}
	}
	
	// send message to server
	public static void select(String mess) {
		try {
			clientDOS.writeUTF(mess);
			clientDOS.flush();
		} catch (IOException e) {
			
		}
	}

}
