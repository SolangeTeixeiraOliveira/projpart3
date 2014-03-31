package libraryGUI;

import javax.swing.*;

import sqlFunctions.SQLFunctions;

import java.beans.*; //property change stuff
import java.awt.*;
import java.awt.event.*;

public class LoginWindow extends JPanel {
	
	private JTextField username;
	private JPasswordField password;
	private int loginAttempts;

	/** Creates the reusable dialog. */
	public LoginWindow() {
		
		JFrame frame = new JFrame("Log In");
		frame.setContentPane(this);
		
		JComboBox userType = new JComboBox();
		userType = new JComboBox();
		userType.setPreferredSize(new Dimension(225, 20));
		userType.addItem("Borrower");
		userType.addItem("Clerk");
		userType.addItem("Librarian");
		this.add(userType);

		JLabel userName = new JLabel("Borrower ID: ");
		userName.setPreferredSize(new Dimension(160, 30));
		this.add(userName);
		username = new JTextField(20);
		this.add(username);
		JLabel passWord = new JLabel("Password: ");
		passWord.setPreferredSize(new Dimension(160, 30));
		this.add(passWord);
		password = new JPasswordField(20);
		password.setEchoChar('*');
		this.add(password);


		JButton loginBtn = new JButton("Log In");
		loginBtn.setPreferredSize(new Dimension(160, 30));
		loginBtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		checklogin();
        		LibraryWindow.createAndShowGUI();
        	}
        });
		this.add(loginBtn);
		
		
		//Handle window closing correctly.
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{ 
				System.exit(0); 
			}
		});
		

	}
	
	private void checklogin() {
		
		
		
	}
	
}