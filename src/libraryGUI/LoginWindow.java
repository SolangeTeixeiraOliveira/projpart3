package libraryGUI;

import javax.swing.*;

import sqlFunctions.SQLFunctions;

import java.awt.*;
import java.awt.event.*;

public class LoginWindow extends JPanel {
	
	private JTextField borrowerID;
	private JPasswordField password;
	private int loginAttempts;
	private JFrame frame;

	/** Creates the reusable dialog. */
	public LoginWindow() {
		
		JFrame frame = new JFrame("Log In");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(this);
		
		JComboBox userType = new JComboBox();
		userType = new JComboBox();
		userType.setPreferredSize(new Dimension(225, 20));
		userType.addItem("Borrower");
		userType.addItem("Clerk");
		userType.addItem("Librarian");
		this.add(userType);

		JLabel borID = new JLabel("Borrower ID: ");
		borID.setPreferredSize(new Dimension(160, 30));
		this.add(borID);
		borrowerID = new JTextField(20);
		this.add(borrowerID);
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
		
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		borrowerID.requestFocus();

	}
	
	private void checklogin() {
		
		int bid;
		try {
			bid = Integer.parseInt(borrowerID.getText());
			boolean validAccount = SQLFunctions.isValidAccount(bid);
			if (!validAccount) {
				JOptionPane.showMessageDialog(frame, "Account does not exist");
				return;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Not a valid card number");
			return;
		}
		
	}
	
}