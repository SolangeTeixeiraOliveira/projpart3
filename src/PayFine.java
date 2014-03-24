import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class PayFine extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField borrowingID;
	private JTextField amount;
	private JButton payBtn;
	
	public PayFine() {
		this.setPreferredSize(new Dimension(400, 150));
		
		JLabel borrowingIDLabel = new JLabel("Borrowing ID:");
		borrowingIDLabel.setPreferredSize(new Dimension(110, 20));
		this.add(borrowingIDLabel);
		borrowingID = new JTextField(20);
		this.add(borrowingID);
		
		JLabel amtLabel = new JLabel("Amount:");
		amtLabel.setPreferredSize(new Dimension(110, 20));
		this.add(amtLabel);
		amount = new JTextField(20);
		this.add(amount);
		
		payBtn = new JButton("Hold");
		this.add(payBtn);
		payBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//payfine();
			}
		
		});
		
	}
	
	
}
