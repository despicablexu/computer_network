

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ServerGUI extends JFrame {
	public JLabel portJL;
	public JLabel listJL;
	public JLabel ipJL;
	public JTextField ipJTF;
	public JTextField portJTF;
	public JTextArea messJTA;
	public JScrollPane messJSP;
	public JList<?> onlineList;
	public JScrollPane listJSP;
	public DefaultListModel<Object> model = new DefaultListModel<Object>();
	
	public ServerGUI() {
		getContentPane().setLayout(null);
		ClientGUI.setUI();
		
		ipJL = new JLabel("IP", JLabel.CENTER);
		ipJL.setBounds(10, 10, 36, 26);
		ipJL.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		ipJTF = new JTextField();
		ipJTF.setBounds(56, 11, 103, 26);
		ipJTF.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		ipJTF.setEditable(false);
		ipJTF.setHorizontalAlignment(JTextField.CENTER);
		
		portJL = new JLabel("Port", JLabel.CENTER);
		portJL.setBounds(169, 10, 36, 26);
		portJL.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		portJTF = new JTextField();
		portJTF.setBounds(215, 11, 59, 26);
		portJTF.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		portJTF.setEditable(false);
		portJTF.setHorizontalAlignment(JTextField.CENTER);
		
		messJTA = new JTextArea();
		messJTA.setLineWrap(true);
		messJTA.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		messJTA.setEditable(false);
		
		messJSP = new JScrollPane(messJTA);
		messJSP.setBounds(10,50, 215, 190);
		
		listJL = new JLabel("Online List", JLabel.CENTER);
		listJL.setBounds(284, 10, 90, 26);
		listJL.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		onlineList = new JList<Object>(model);
		
		listJSP = new JScrollPane(onlineList);
		listJSP.setBounds(269, 52, 105, 187);
		
		getContentPane().add(ipJL);
		getContentPane().add(ipJTF);
		getContentPane().add(portJL);
		getContentPane().add(portJTF);
		getContentPane().add(messJSP);
		getContentPane().add(listJSP);
		getContentPane().add(listJL);
		
		this.setTitle("MIRO");
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(getClass().getResource("/logo.png")).getImage());
		this.setSize(400, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
