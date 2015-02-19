/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailclient;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;


@SuppressWarnings("serial")
public class LoginDialog extends JDialog {

    private JButton login, cancel;
    private JTextField editAccount;
    private JPasswordField editPassword;
    private JLabel accountLabel, passLabel;
    private boolean toProcessed = false;

    LoginDialog() {
        setUI(); // 瑕佸湪鍒濆鍖栦箣鍓嶈缃甎I鎵嶆湁鏁�
        initComponent();
        pack();
        setModal(true);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();//Get the screen size
        setBounds((d.width - this.getWidth()) / 2, (d.height - this.getHeight()) / 2, this.getWidth(), this.getHeight());
        setResizable(false);
        setVisible(true);
    }

    private void initComponent() {
        login = new JButton("OK");
        cancel = new JButton("Cancel");
        editAccount = new JTextField();
        editPassword = new JPasswordField();
        accountLabel = new JLabel("Email addr", JLabel.LEFT);
        passLabel = new JLabel("Password", JLabel.LEFT);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(null);
        this.setPreferredSize(new Dimension(300, 200));
        setTitle("Input personal message");

        accountLabel.setBounds(20, 20, 80, 30);
        passLabel.setBounds(20, 60, 80, 30);
        editAccount.setBounds(100, 20, 170, 30);
        editPassword.setBounds(100, 60, 170, 30);
        login.setBounds(75, 120, 70, 30);
        cancel.setBounds(155, 120, 70, 30);

        add(accountLabel);
        add(passLabel);
        add(editAccount);
        add(editPassword);
        add(login);
        add(cancel);

        accountLabel.setFont(new Font("", 1, 15));
        passLabel.setFont(new Font("", 1, 15));

        login.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                loginActionPerformed();
            }
        });

        cancel.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cancelActionPerformed();
            }
        });
    }

    // 纭鎸夐挳浜嬩欢
    private void loginActionPerformed() {
        if (!getAccount().equals("") && !getPassword().equals("")) {
            if (getAccount().contains("@")) {
                toProcessed = true;
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Mail format illegal.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Input account and password.");
        }
    }

    // 鍙栨秷鎸夐挳浜嬩欢
    private void cancelActionPerformed() {
        toProcessed = false;
        this.dispose();
    }

    public boolean canProcess() {
        return toProcessed;
    }

    public String getAccount() {
        return editAccount.getText();
    }

    public String getPassword() {
        return new String(editPassword.getPassword());
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
