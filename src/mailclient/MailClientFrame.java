/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailclient;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class MailClientFrame extends JFrame {

    private final static String CRLF = "\r\n";

    private JLabel sender, receiver, theme, text;
    private JTextField senderText, editReceiver, editTheme;
    private JTextArea editText;
    private JScrollPane jsp;
    private JButton send, clear, quit;
    private String senderPass;

    MailClientFrame(String senderAddress, String pwd) {
        senderPass = pwd;
        setUI();
        initComponent(senderAddress);
        pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();//Get the screen size
        setBounds((d.width - this.getWidth()) / 2, (d.height - this.getHeight()) / 2, this.getWidth(), this.getHeight());
        setResizable(false);
        setVisible(true);
    }

    private void initComponent(String senderAddress) {
        sender = new JLabel("Sender");
        receiver = new JLabel("Receiver");
        theme = new JLabel("Theme");
        text = new JLabel("Text");
        senderText = new JTextField(senderAddress);
        editReceiver = new JTextField();
        editTheme = new JTextField();
        editText = new JTextArea();
        jsp = new JScrollPane(editText);
        send = new JButton("Send");
        clear = new JButton("Clear");
        quit = new JButton("Exit");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(null);
        this.setPreferredSize(new Dimension(450, 450));
        setTitle("New mail");
        editText.setLineWrap(true);
        senderText.setEditable(false);

        sender.setBounds(20, 20, 80, 30);
        senderText.setBounds(100, 20, 300, 30);
        receiver.setBounds(20, 60, 80, 30);
        editReceiver.setBounds(100, 60, 300, 30);
        theme.setBounds(20, 100, 80, 30);
        editTheme.setBounds(100, 100, 300, 30);
        text.setBounds(20, 140, 80, 30);
        jsp.setBounds(100, 140, 300, 200);
        editText.setBounds(100, 140, 300, 200);
        send.setBounds(100, 350, 70, 30);
        clear.setBounds(215, 350, 70, 30);
        quit.setBounds(330, 350, 70, 30);

        add(sender);
        add(receiver);
        add(theme);
        add(text);
        add(senderText);
        add(editReceiver);
        add(editTheme);
        add(jsp);
        add(send);
        add(clear);
        add(quit);

        send.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null,
                        "Confirm to send?", "Mail", JOptionPane.YES_NO_OPTION)) {
                    sendMail();
                }
            }
        });

        clear.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                editReceiver.setText("");
                editTheme.setText("");
                editText.setText("");
            }
        });

        quit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
    }

    private void sendMail() {
        try {
            String fromAddr = senderText.getText();
            String toAddr = editReceiver.getText();
            String subject = editTheme.getText();
            String message = editText.getText();

            if (!toAddr.contains("@")) {
                JOptionPane.showMessageDialog(null, "Please input legal mail address!");
            } else {

                int port = 25;

                // get domain name
                int index = fromAddr.indexOf("@");
                String fromServerDomain = fromAddr.substring(index + 1);

                // connect to smtp server, like smtp.gmail.com
                Socket socket = new Socket("smtp." + fromServerDomain, port);
                // socket input buffer
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                // socket output buffer
                PrintStream out = new PrintStream(socket.getOutputStream());

                if (in == null || out == null) {
                    System.out.println("Fail to open the stream");
                    socket.close();
                    return;
                }

                // server returns 220, get message from server
                String from_server = in.readLine();
                System.out.println(from_server);

                System.out.println("HELO " + fromAddr);
                // send HELO to server
                out.print("HELO " + fromAddr + CRLF);

                // server returns 250
                from_server = in.readLine();
                System.out.println(from_server);

                // clear buffer
                out.print("RSET" + CRLF);

                // return 250 confirm reset
                from_server = in.readLine();
                System.out.println(from_server);

                // request login
                out.print("AUTH LOGIN" + CRLF);

                // return 334
                from_server = in.readLine();
                System.out.println("Login " + from_server);

                // use base64 encoder then send to server
                out.print(new String(Base64Encoder.encode(fromAddr.getBytes())) + CRLF);

                // return 334
                from_server = in.readLine();
                System.out.println("USER " + from_server);

                // send password
                out.print(new String(Base64Encoder.encode(senderPass.getBytes())) + CRLF);

                // return 250
                from_server = in.readLine();
                System.out.println("PASS " + from_server);

                System.out.println("MAIL FROM: <" + fromAddr + ">");
                // send the sender address
                out.print("MAIL FROM: <" + fromAddr + ">" + CRLF);

                // return 250
                from_server = in.readLine();
                System.out.println(from_server);

                System.out.println("RCPT TO: <" + toAddr + ">");
                // send receiver address
                out.print("RCPT TO: <" + toAddr + ">" + CRLF);

                // return 250
                from_server = in.readLine();
                System.out.println(from_server);

                System.out.println("DATA ");
                // notify server that begin to send text
                out.print("DATA" + CRLF);

                // return 354
                from_server = in.readLine();
                System.out.println(from_server);

                // send the sender name
                out.print("FROM:" + fromAddr + CRLF);
                // send the receiver name
                out.print("TO:" + toAddr + CRLF);
                // send theme
                out.print("SUBJECT:" + subject + CRLF);
                // obligate a new line
                out.print(CRLF);
                // send text
                out.print(message + CRLF);
                // send '.', indicate the end of text
                out.print("." + CRLF);

                // server return 250
                from_server = in.readLine();
                System.out.println(from_server);

                System.out.println("QUIT");
                // request quit
                out.print("QUIT" + CRLF);

                // server return 221
                from_server = in.readLine();
                System.out.println(from_server);

                if (from_server.contains("221 Bye")) {
                    JOptionPane.showMessageDialog(null, "Mail sending success!");
                }
                
                // close the socket
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(MailClientFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void setUI() {
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
