

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mailclient.LoginDialog;
import mailclient.MailThread;

@SuppressWarnings("serial")
public class ClientGUI extends JFrame implements ActionListener, ListSelectionListener {
	
	protected String mess = null;
	
	protected JMenuBar menuJMB;
	protected JMenu optionMenu;
	protected JMenuItem emailItem;
	protected JMenu clearMenu;
	protected JMenuItem clrPubItem;
	protected JMenuItem clrPvtItem;
	
	protected JLabel hostnameJL;
	protected JTextField hostJTF;
	protected JLabel portJL;
	protected JTextField portJTF;
	protected JLabel ipJL;
	protected JTextField ipJTF;
	
	protected JLabel pvtMessJL;
	protected JScrollPane pvtMessJSP;
	protected JTextArea pvtMess;	// private message area
	protected JScrollPane pvtMessInJSP;
	protected JTextArea pvtMessIn;
	protected JButton pvtMessSendJB;
	protected JLabel guessJL;

	protected JLabel pubMessJL;
	protected JScrollPane pubMessJSP;
	protected JTextArea pubMess;	// private message area
	protected JScrollPane pubMessInJSP;
	protected JTextArea pubMessIn;
	protected JButton pubMessSendJB;
	
	protected JLabel onlineJL;
	protected JList<?> onlineList;
	protected JScrollPane listJSP;
	protected DefaultListModel<Object> model = new DefaultListModel<Object>();
	protected JButton getListJB;
	
	protected JButton exitJB;
	
	public ClientGUI() {
		setUI();
		getContentPane().setLayout(null);
		
		menuJMB = new JMenuBar();
        setJMenuBar(menuJMB);
        
        optionMenu = new JMenu("Option");
        menuJMB.add(optionMenu);
        emailItem = new JMenuItem("Email");
        optionMenu.add(emailItem);
        
        clearMenu = new JMenu("Clear");
        menuJMB.add(clearMenu);
        clrPubItem = new JMenuItem("public Messages");
        clearMenu.add(clrPubItem);
        clrPvtItem = new JMenuItem("private Messages");
        clearMenu.add(clrPvtItem);
		
		hostnameJL = new JLabel("Hostname", JLabel.CENTER);
		hostnameJL.setBounds(10, 10, 70, 25);
		hostnameJL.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		hostJTF = new JTextField();
		hostJTF.setBounds(90, 10, 90, 25);
		hostJTF.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		hostJTF.setEditable(false);
		hostJTF.setHorizontalAlignment(JTextField.CENTER);
		
		portJL = new JLabel("Port", JLabel.CENTER);
		portJL.setBounds(390, 10, 35, 25);
		portJL.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		portJTF = new JTextField();
		portJTF.setBounds(435, 10, 60, 25);
		portJTF.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		portJTF.setEditable(false);
		portJTF.setHorizontalAlignment(JTextField.CENTER);
		
		ipJL = new JLabel("IP", JLabel.CENTER);
		ipJL.setBounds(201, 10, 35, 25);
		ipJL.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		ipJTF = new JTextField();
		ipJTF.setBounds(246, 10, 122, 25);
		ipJTF.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		ipJTF.setEditable(false);
		ipJTF.setHorizontalAlignment(JTextField.CENTER);
		
		// set private message text area
		pvtMessJL = new JLabel("Private Messages", JLabel.CENTER);
		pvtMessJL.setBounds(270, 45, 225, 25);
		pvtMessJL.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		pvtMess = new JTextArea();
		pvtMess.setLineWrap(true);
		pvtMess.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		pvtMess.setEditable(false);
		
		pvtMessJSP = new JScrollPane(pvtMess);
		pvtMessJSP.setBounds(270,80, 225, 235);
		
		// set private message input area
		pvtMessIn = new JTextArea();
		pvtMessIn.setLineWrap(true);
		pvtMessIn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		pvtMessInJSP = new JScrollPane(pvtMessIn);
		pvtMessInJSP.setBounds(270,360, 225, 56);
		
		pvtMessSendJB = new JButton("Send");
		pvtMessSendJB.setBounds(425, 426, 70, 25);
		
		guessJL = new JLabel("", JLabel.CENTER);
		guessJL.setBounds(270, 325, 225, 25);
		guessJL.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		// set public message text area
		pubMessJL = new JLabel("Public Messages", JLabel.CENTER);
		pubMessJL.setBounds(10, 45, 225, 25);
		pubMessJL.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		pubMess = new JTextArea();
		pubMess.setLineWrap(true);
		pubMess.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		pubMess.setEditable(false);
		
		pubMessJSP = new JScrollPane(pubMess);
		pubMessJSP.setBounds(10,80, 225, 235);
		
		// set private message input area
		pubMessIn = new JTextArea();
		pubMessIn.setLineWrap(true);
		pubMessIn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		pubMessInJSP = new JScrollPane(pubMessIn);
		pubMessInJSP.setBounds(10,360, 225, 56);
		
		pubMessSendJB = new JButton("Send");
		pubMessSendJB.setBounds(165, 426, 70, 25);
		
		// set online users list window
		onlineJL = new JLabel("Online List", JLabel.CENTER);
		onlineJL.setBounds(519, 9, 105, 26);
		onlineJL.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		onlineList = new JList<Object>(model);
		
		listJSP = new JScrollPane(onlineList);
		listJSP.setBounds(519, 48, 105, 282);
		
		getListJB = new JButton("Get users");
		getListJB.setBounds(519, 340, 105, 25);
		
		exitJB = new JButton("Exit");
		exitJB.setBounds(519, 426, 105, 25);
		
		getContentPane().add(hostnameJL);
		getContentPane().add(hostJTF);
		getContentPane().add(portJL);
		getContentPane().add(portJTF);
		getContentPane().add(ipJL);
		getContentPane().add(ipJTF);
		getContentPane().add(pvtMessJL);
		getContentPane().add(pvtMessJSP);
		getContentPane().add(pvtMessInJSP);
		getContentPane().add(pvtMessSendJB);
		getContentPane().add(guessJL);
		getContentPane().add(pubMessJL);
		getContentPane().add(pubMessJSP);
		getContentPane().add(pubMessInJSP);
		getContentPane().add(pubMessSendJB);
		getContentPane().add(onlineJL);
		getContentPane().add(listJSP);
		getContentPane().add(getListJB);
		getContentPane().add(exitJB);
		
		pubMessSendJB.addActionListener(this);
		pvtMessSendJB.addActionListener(this);
		getListJB.addActionListener(this);
		exitJB.addActionListener(this);
		onlineList.addListSelectionListener(this);
		emailItem.addActionListener(this);
		clrPubItem.addActionListener(this);
		clrPvtItem.addActionListener(this);
		
		this.setSize(650, 520);
		this.setTitle("MINET");
		this.setIconImage(new ImageIcon(getClass().getResource("/logo.png")).getImage());
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pubMessSendJB) {
			String mess = pubMessIn.getText();
			Client.select(ProtocolMaker.messageP(hostJTF.getText(), mess));
			pubMessIn.setText("");
		} 
		else if (e.getSource() == pvtMessSendJB) {
			this.mess = pvtMessIn.getText();
			if (!guessJL.getText().equals("")) {
				pvtMess.append(P2PThread.getDate() + "\n" + hostJTF.getText() + " : \t" + mess + "\n\n");
				P2PThread.sendGuest(hostJTF.getText(), guessJL.getText(), mess);
				pvtMessIn.setText("");
			} else {
				JOptionPane.showMessageDialog(null, 
						"Please select an online user!", 
						"Attention", 
						JOptionPane.ERROR_MESSAGE);
			}
		}
		// get online client
		else if (e.getSource() == getListJB) {
			Client.select(ProtocolMaker.getListP());
		}
		else if (e.getSource() == exitJB) {
			String mess = "LEAVE";
			Client.select(mess);
			System.exit(0);
		}
		else if (e.getSource() == emailItem) {
			MailThread mt = new MailThread();
			Thread th = new Thread(mt);
			th.start();
		}
		// clear public message area
		else if (e.getSource() == clrPubItem) {
			pubMess.setText("");
		}
		// clear private message area
		else if (e.getSource() == clrPvtItem) {
			pvtMess.setText("");
		}
	}

	// get selected user in onlist
	@Override
	public void valueChanged(ListSelectionEvent e) {
		guessJL.setText((String) onlineList.getSelectedValue());
	}
	
	protected static void setUI() {
        try {
            javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            //javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(LoginDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(LoginDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(LoginDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
