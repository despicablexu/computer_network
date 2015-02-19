package mailclient;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MailThread extends JFrame implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		LoginDialog ld = new LoginDialog();
        if (ld.canProcess()) {
            String account = ld.getAccount();
            String pass = ld.getPassword();
            
            new MailClientFrame(account, pass);
        }
	}

}
