import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;


public class ClientBeat implements Runnable {
	protected Timer heartBeatTimer;
	protected TimerTask heartBeatTask;
	protected DataOutputStream dos;
	
	public ClientBeat() {
	}
	
	public void setDataOutputStream(DataOutputStream dos) {
		this.dos = dos;
	}
	
	@Override
	public void run() {
		heartBeatTimer = new Timer();  
        heartBeatTask = new TimerTask() {  
  
            @Override  
            public void run() {  
                // TODO Auto-generated method stub  
                sendOrder(ProtocolMaker.beatP(""));  
            }  
        };  
        heartBeatTimer.schedule(heartBeatTask, 15000, 15000); 
	}
	
	private void sendOrder(String order){  
	    try {  
	        dos.writeUTF(order);  
	        dos.flush();  
	    } catch (UnsupportedEncodingException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  
	      
	} 

}
