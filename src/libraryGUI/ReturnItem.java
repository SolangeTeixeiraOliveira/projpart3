package libraryGUI;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import sqlFunctions.SQLFunctionsClerk;


public class ReturnItem extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField callNumber;
	private JTextField copyNumber;
	private JButton returnBtn;
	private JFrame frame;

	public ReturnItem() {
		this.setPreferredSize(new Dimension(350, 100));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel firstPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		firstPanel.setPreferredSize(new Dimension(350,25));
		JLabel callNumLbl = new JLabel("Call number:");
		firstPanel.add(callNumLbl);
		callNumber = new JTextField(20);
		firstPanel.add(callNumber);
		this.add(firstPanel);
		
		JPanel secondPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		secondPanel.setPreferredSize(new Dimension(350,25));
		JLabel copyNumLbl = new JLabel("Copy number:");
		secondPanel.add(copyNumLbl);
		copyNumber = new JTextField(3);
		secondPanel.add(copyNumber);
		returnBtn = new JButton("Return");
		this.add(secondPanel);
		
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
		
		String emailAddress = SQLFunctionsClerk.returnItem(bookCallNum, bookCopyNum);
		if (emailAddress != null) {
			JOptionPane.showMessageDialog(frame, "Book returned - sent email to " + emailAddress.toString());
			System.out.println("Book returned - sent email to " + emailAddress);
		} else {
			JOptionPane.showMessageDialog(frame, "Book returned");
			System.out.println("Book has been returned");
		}
	}
}