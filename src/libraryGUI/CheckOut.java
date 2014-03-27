package libraryGUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CheckOut extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField cardNumber;
	private JTextField callNumber;
	private JButton addBtn;

	public CheckOut() {
		this.setPreferredSize(new Dimension(400, 400));
		JLabel cardNumberLabel = new JLabel("Card Number:");
		this.add(cardNumberLabel);
		cardNumber = new JTextField(20);
		this.add(cardNumber);
		JLabel callNumberLabel = new JLabel("Call Number:");
		this.add(callNumberLabel);
		callNumber = new JTextField(20);
		this.add(callNumber);
		addBtn = new JButton("Add");
		this.add(addBtn);
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//checkOutItems();
			}
		});
	}
	
	private void checkOutItems(List<String> callNumbers) {
		
	}
}
	
	

	