import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MonitorThread implements Runnable {
	private ServerSocket monitorSocket = null;
	private Socket guest = null;
	private DataOutputStream clientDOS = null;
	private DataInputStream monitorDIS = null;
	private int monitorPort = 8888;
	public MonitorThread(DataOutputStream clientDOS) {
		this.clientDOS = clientDOS;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			monitorSocket = new ServerSocket(monitorPort);
		} catch (IOException e) {
			// try another port
			monitorPort++;
			run();
		}
		
		Client.clientGUI.portJTF.setText(String.valueOf(monitorPort));
		try {
			/*
			 *  sent port to server
			 *  no need sent IP to server
			 */
			clientDOS.writeUTF(String.valueOf(monitorPort));
			clientDOS.flush();
			
		} catch (IOException e) {
			
		}
		
		while (true) {
			try {
				// wait for connecting
				guest = monitorSocket.accept();
			 
				monitorDIS = new DataInputStream(guest.getInputStream());
				
				// connect success
				if (guest.isConnected()) {
					
					// get the guest name
					String guessName = monitorDIS.readUTF();
					
					P2PThread p2pTh = new P2PThread(guest, guessName);
				    Thread th = new Thread(p2pTh);
				    
				    th.start();
				}
			} catch (Exception e) {
				
			}
		}
	}
	
}
