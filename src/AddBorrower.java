import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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

	public AddBorrower() {
		this.setPreferredSize(new Dimension(300, 200));
		JLabel nameLabel = new JLabel("Name:");
		this.add(nameLabel);
		
		borName = new JTextField(20);
		this.add(borName);
		borPassword = new JTextField(20);
		this.add(borPassword);
		borAddress = new JTextField(20);
		this.add(borAddress);
		borPhone = new JTextField(20);
		this.add(borPhone);
		borEmail = new JTextField(20);
		this.add(borEmail);
		borSinOrStNo = new JTextField(20);
		this.add(borSinOrStNo);
		borType = new JComboBox();
		borType.addItem("Student");
		borType.addItem("Faculty");
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

		int phone = Integer.parseInt(borPhone.getText());
		int sinOrStNo = Integer.parseInt(borSinOrStNo.getText());
		int bid = SQLFunctions.addBorrower(borName.getText(), borPassword
				.getText(), borAddress.getText(), phone, borEmail.getText(),
				sinOrStNo, borType.getSelectedItem().toString());
		System.out.println(bid);
	}

}
