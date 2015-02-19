

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
 
@SuppressWarnings("serial")
public class LoginGUI extends JFrame implements ActionListener {
	
	protected JLabel backgroundJL;
	protected JLabel logoJL;
	protected JTextField ipInJTF;
	protected JLabel ipJL;
	protected JTextField nameInJTF;
	protected JLabel nameJL;
	protected JButton loginJB;
 
    private String current_input = null;
    protected boolean loginIsClick = false;
	
    public LoginGUI() {
    	// initialize the window
    	init();
        this.setTitle("MINET");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        
        this.setIconImage(new ImageIcon(getClass().getResource("/logo.png")).getImage());
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
 
    /*
     * 初始化方法
     */
    public void init() {
    	this.setSize(330, 235);
        backgroundJL = new JLabel();
        // set background image
        ImageIcon image1 = new ImageIcon(getClass().getResource("/tara.jpg"));
        image1.setImage(image1.getImage().getScaledInstance(this.getWidth(), this.getHeight(),Image.SCALE_SMOOTH)); 
        backgroundJL.setIcon(image1);
        backgroundJL.setBounds(0, 0, 330, 235);
         
        // 图标设定
        logoJL = new JLabel();
        Image image2 = new ImageIcon(getClass().getResource("/logo.png")).getImage();
        logoJL.setIcon(new ImageIcon(image2));
        logoJL.setBounds(20, 61, 90, 74);
        
        nameInJTF = new JTextField();
        nameInJTF.setBounds(194, 100, 120, 25);
        nameInJTF.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        nameJL = new JLabel("Name", JLabel.CENTER);
        nameJL.setBounds(118, 100, 60, 25);
        nameJL.setOpaque(true);
        nameJL.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        ipInJTF = new JTextField("172.18.32.56");
        ipInJTF.setHorizontalAlignment(JTextField.CENTER);
        ipInJTF.setBounds(194, 70, 120, 25);
        ipInJTF.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        ipJL = new JLabel("IP", JLabel.CENTER);
        ipJL.setBounds(118, 70, 60, 25);
        ipJL.setOpaque(true);
        ipJL.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        loginJB = new JButton("Login");
        loginJB.setBounds(244, 167, 70, 25);
        
        loginJB.addActionListener(this);
        
        backgroundJL.add(nameInJTF);
        backgroundJL.add(nameJL);
        backgroundJL.add(ipInJTF);
        backgroundJL.add(ipJL);
        backgroundJL.add(logoJL);
        backgroundJL.add(loginJB);
        
        getContentPane().add(backgroundJL);
    }
 

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginJB) {
			if (!nameInJTF.getText().equals(null)) {
				current_input = nameInJTF.getText();
			} else {
				current_input = "";
			}
			loginIsClick = true;
		}
	}
	
	public String getInput() {
		return current_input;
	}
 
}
