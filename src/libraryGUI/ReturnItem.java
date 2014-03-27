package libraryGUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import sqlFunctions.SQLFunctions;


public class ReturnItem extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField callNumber;
	private JTextField copyNumber;
	private JButton returnBtn;
	private JFrame frame;

	public ReturnItem() {
		this.setPreferredSize(new Dimension(400, 400));
		callNumber = new JTextField(20);
		this.add(callNumber);
		copyNumber = new JTextField(3);
		this.add(copyNumber);
		returnBtn = new JButton("Return");
		this.add(returnBtn);
		returnBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				processReturn();
			}
		});
	}
	
	private void processReturn() {
		String bookCallNum = callNumber.getText();
		int bookCopyNum;
		
		// Check for empty inputs
		if (callNumber.getText().length() == 0 || copyNumber.getText().length() == 0) {
			JOptionPane.showMessageDialog(frame, "Please enter a call number and copy number");
			return;
		}	
		try {
			bookCopyNum = Integer.parseInt(copyNumber.getText());
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Invalid copy number");
			return;
		}
		
		String emailAddress = SQLFunctions.returnItem(bookCallNum, bookCopyNum);
		if (emailAddress != null) {
			System.out.println("Book returned - sent email to " + emailAddress);
		} else {
			System.out.println("Book has been returned");
		}
	}
}