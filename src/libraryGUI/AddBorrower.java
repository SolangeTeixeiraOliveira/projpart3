package libraryGUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import sqlFunctions.SQLFunctionsClerk;

public class AddBorrower extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField borName;
	private JTextField borPassword;
	private JTextField borAddress;
	private JTextField borPhone;
	private JTextField borEmail;
	private JTextField borSinOrStNo;
	private JComboBox borType;
	private JButton addBtn;
	private JFrame frame;

	public AddBorrower() {
		this.setPreferredSize(new Dimension(360, 250));
		
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setPreferredSize(new Dimension(110, 20));
		this.add(nameLabel);
		borName = new JTextField(20);
		this.add(borName);
		
		JLabel psswdLabel = new JLabel("Password:");
		psswdLabel.setPreferredSize(new Dimension(110, 20));
		this.add(psswdLabel);
		borPassword = new JTextField(20);
		this.add(borPassword);
		
		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setPreferredSize(new Dimension(110, 20));
		this.add(addressLabel);
		borAddress = new JTextField(20);
		this.add(borAddress);
		
		JLabel phoneLabel = new JLabel("Phone:");
		phoneLabel.setPreferredSize(new Dimension(110, 20));
		this.add(phoneLabel);
		borPhone = new JTextField(20);
		this.add(borPhone);
		
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setPreferredSize(new Dimension(110, 20));
		this.add(emailLabel);
		borEmail = new JTextField(20);
		this.add(borEmail);
		
		JLabel sinLabel = new JLabel("SIN or Student No:");
		sinLabel.setPreferredSize(new Dimension(110, 20));
		this.add(sinLabel);
		borSinOrStNo = new JTextField(20);
		this.add(borSinOrStNo);
		
		JLabel typeLabel = new JLabel("Type:");
		typeLabel.setPreferredSize(new Dimension(110, 20));
		this.add(typeLabel);
		borType = new JComboBox();
		borType.setPreferredSize(new Dimension(225, 20));
		borType.addItem("Student");
		borType.addItem("Faculty");
		borType.addItem("Staff");
		this.add(borType);
		
		addBtn = new JButton("Add");
		this.add(addBtn);
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addBorrower();
			}
		
		});
	}
	
	private void addBorrower() {

		int phone;
		int sinOrStNo;
		
		if (borName.getText().length() == 0 || borPassword.getText().length() == 0) {
			JOptionPane.showMessageDialog(frame, "Must provide borrower name and password");
			return;
		}
		
		try {
			phone = Integer.parseInt(borPhone.getText());
			sinOrStNo = Integer.parseInt(borSinOrStNo.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Phone number and SIN/Student number must be integers");
			return;
		}
		int bid = SQLFunctionsClerk.addBorrower(borName.getText(), borPassword
				.getText(), borAddress.getText(), phone, borEmail.getText(),
				sinOrStNo, borType.getSelectedItem().toString());
		System.out.println(bid);
		JOptionPane.showMessageDialog(frame, "New borrower id: " + bid);
	}

}
