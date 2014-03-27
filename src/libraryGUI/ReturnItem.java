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
		int bookCopyNum = Integer.parseInt(copyNumber.getText());
		boolean success = SQLFunctions.returnItem(bookCallNum, bookCopyNum);
		if (success) {
			System.out.println("Successfully returned book " + bookCallNum + " C" + bookCopyNum);
		} else {
			System.out.println("Failed to return book " + bookCallNum + " C" + bookCopyNum);
		}
	}
}