import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CheckOutWindow extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField cardNumber;
	private JTextField callNumber;
	private JButton addBtn;

	public CheckOutWindow() {
		this.setPreferredSize(new Dimension(400, 400));
		JLabel cardNumberLabel = new JLabel("Card Number:");
		this.add(cardNumberLabel);
		cardNumber = new JTextField(20);
		this.add(cardNumber);

		this.setPreferredSize(new Dimension(400, 400));
		JLabel callNumberLabel = new JLabel("Card Number:");
		this.add(callNumberLabel);
		callNumber = new JTextField(20);
		this.add(callNumber);

		this.setPreferredSize(new Dimension(300, 200));
		addBtn = new JButton("Add");
		this.add(addBtn);
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// addBook();
			}
		});
	}
}
	
	

	