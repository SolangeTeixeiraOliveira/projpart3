package libraryGUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import sqlFunctions.SQLFunctions;


public class CheckOut extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField cardNumber;
	private JTextField callNumber;
	private JTextField copyNumber;
	private JButton addBtn;
	private JButton checkOutBtn;
	private JList booklist;
	private Vector<String> callNumList;
	private Vector<Integer> copyNumList;
	private Vector<String> fullCallNums;
	private JScrollPane listScrollPane;
	private JFrame frame;

	public CheckOut() {
		this.setPreferredSize(new Dimension(400, 300));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel cardNumberLabel = new JLabel("Card Number:");
		this.add(cardNumberLabel);
		cardNumber = new JTextField(20);
		this.add(cardNumber);
		JLabel callNumberLabel = new JLabel("Call Number:");
		this.add(callNumberLabel);
		callNumber = new JTextField(20);
		this.add(callNumber);
		JLabel copyNumberLabel = new JLabel("Copy:");
		this.add(copyNumberLabel);
		copyNumber = new JTextField(3);
		this.add(copyNumber);
		addBtn = new JButton("Add");
		this.add(addBtn);
		
		// The list of call numbers and the JList to display them in
		callNumList = new Vector<String>();
		copyNumList = new Vector<Integer>();
		fullCallNums = new Vector<String>();
		booklist = new JList();
		listScrollPane = new JScrollPane(booklist);
		this.add(listScrollPane);

		checkOutBtn = new JButton("Check out");
		this.add(checkOutBtn);
		
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addToBookList();
			}
		});
		
		checkOutBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkOutItems();
			}
		});
	}
	
	private void addToBookList() {
		if (callNumList.size() == 20) {
			JOptionPane.showMessageDialog(frame, "Cannot check out more than 20 books at once");
			return;
		}
		
		String book = callNumber.getText().trim();
		Integer copy;

		// Check for valid inputs
		if (book.length() == 0) {
			JOptionPane.showMessageDialog(frame, "Please enter a call number");
			return;
		}
		
		try {
			copy = Integer.parseInt(copyNumber.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Invalid copy number");
			return;
		}
		
		// Concatenate the book call number with the copy number
		String fullCallNum = book + " C" + copy;
		if (!fullCallNums.contains(fullCallNum)) {
			callNumList.add(book);
			copyNumList.add(copy);
			fullCallNums.add(fullCallNum);
			booklist.setListData(fullCallNums);
		}
	}
	
	private void checkOutItems() {
		
		// Get borrower id
		int bid;
		try {
			bid = Integer.parseInt(cardNumber.getText());
			boolean validAccount = SQLFunctions.isValidAccount(bid);
			if (!validAccount) {
				JOptionPane.showMessageDialog(frame, "Account does not exist");
				return;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Not a valid card number");
			return;
		}
		
		// Try to check out each item
		Vector<String> failedCheckouts = new Vector<String>();
		//Vector<String> successfulCheckouts = new Vector<String>();
		for (int i=0; i<callNumList.size(); i++) {
			boolean success = SQLFunctions.checkOutItem(callNumList.get(i),
					copyNumList.get(i), bid);
			if (success) {
				System.out.println("Successfully checked out " + fullCallNums.get(i));
				//successfulCheckouts.add(fullCallNums.get(i));
			} else {
				failedCheckouts.add(fullCallNums.get(i));
			} 
		}
		
		// DEBUGGING
		failedCheckouts.add("testtest123 C1");
		failedCheckouts.add("abcdefg C3");
		failedCheckouts.add("blabla C2");

		if (failedCheckouts.size() > 0) {
			// Show error message with failed checkouts
			JPanel errorPanel = new JPanel();
			JLabel errorLabel = new JLabel("Failed to check out the following items:");
			JList failedList = new JList();
			JScrollPane errorScP = new JScrollPane(failedList);
			errorPanel.setPreferredSize(new Dimension(300, 170));
			errorScP.setPreferredSize(new Dimension(300, 150));
			errorPanel.add(errorLabel);
			errorPanel.add(errorScP);
			
			failedList.setListData(failedCheckouts);
			JOptionPane.showMessageDialog(frame, errorPanel, "Failed checkouts", JOptionPane.WARNING_MESSAGE);
		}
	}
}
	
	

	