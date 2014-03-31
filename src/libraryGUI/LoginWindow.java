package libraryGUI;

import javax.swing.*;

import sqlFunctions.SQLFunctions;

import java.awt.*;
import java.awt.event.*;

public class LoginWindow {
	
	private static JFrame frame;
	private static JTextField borrowerID;
	private static JPasswordField password;
	private static JComboBox userType;
	static int loginAttempts = 0;

	/** Creates the reusable dialog. */
	public static void CreateLoginWindow() {
		
		frame = new JFrame("Log In");
		frame.setPreferredSize(new Dimension(300, 180));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.setContentPane(panel);
		
		userType = new JComboBox();
		userType.setPreferredSize(new Dimension(225, 20));
		userType.addItem("Borrower");
		userType.addItem("Clerk");
		userType.addItem("Librarian");
		panel.add(userType);

		JLabel borID = new JLabel("Borrower ID: ");
		borID.setPreferredSize(new Dimension(100, 30));
		panel.add(borID);
		borrowerID = new JTextField(10);
		panel.add(borrowerID);
		JLabel passWord = new JLabel("Password: ");
		passWord.setPreferredSize(new Dimension(100, 30));
		panel.add(passWord);
		password = new JPasswordField(10);
		password.setEchoChar('*');
		panel.add(password);


		JButton loginBtn = new JButton("Log In");
		loginBtn.setPreferredSize(new Dimension(160, 30));
		loginBtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		if (userType.getSelectedItem() == "Borrower"){
        			if (checklogin(borrowerID.getText(), String.valueOf(password.getPassword()))) {
        				frame.dispose();
        				LibraryWindow.createAndShowGUI();
        			} else {
        				loginAttempts++;
            			if (loginAttempts >= 3)
            			{
            				frame.dispose();
            				System.exit(-1);
            			}else{
            				password.setText("");
            			}
        			}
        		} else {
        				frame.dispose();
        				LibraryWindow.createAndShowGUI();
        		}
        	}
        });
		panel.add(loginBtn);
		
		
		//Handle window closing correctly.
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
	
	private static boolean checklogin(String borid, String pwd) {
		
		int bid;
		
		try {
			bid = Integer.parseInt(borid);
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(frame, "Borrower ID should be an Integer." );
			return false;
		}
		
		boolean validUser = SQLFunctions.isValidBorrower(bid, pwd);
		if (!validUser) {
			JOptionPane.showMessageDialog(frame, "Borrower does not exist");
			return false;
		}
		return true;
	}
	
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CreateLoginWindow();
            }
        });
    }
}