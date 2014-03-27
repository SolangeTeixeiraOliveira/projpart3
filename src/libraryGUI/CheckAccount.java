package libraryGUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CheckAccount extends JPanel{
	
	private static final long serialVersionUID = 1L;

	private JTextField borID;
	private JTextField borPassword;
	private JButton checkAcctBtn;
	
	public CheckAccount() {
		
		// Making the label of the pop up
        this.setPreferredSize(new Dimension(400, 150));
		
		JLabel userIDLabel = new JLabel("User ID:");
		userIDLabel.setPreferredSize(new Dimension(110, 20));
		this.add(userIDLabel);
		borID = new JTextField(20);
		this.add(borID);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setPreferredSize(new Dimension(110, 20));
		this.add(passwordLabel);
		borPassword = new JTextField(20);
		this.add(borPassword);
		
		checkAcctBtn = new JButton("Confirm");
		this.add(checkAcctBtn);
		checkAcctBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//checkaccount();
			}
		
		});	
	}
	
//	private void checkaccount() {
//
//		String book = SQLFunctions.searchbook(bookTitle.getText(), bookAuthor
//				.getText(), bookSubject.getText());
//		System.out.println("Title: " + bookTitle + " Author: " + bookAuthor + " Subject: " + bookSubject);
//	}
//
//	
}
