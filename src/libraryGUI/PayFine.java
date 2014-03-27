package libraryGUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sqlFunctions.SQLFunctions;


public class PayFine extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField bID;
	private JButton payBtn;
	private JFrame frame;
	
	public PayFine() {
		this.setPreferredSize(new Dimension(400, 150));
		
		JLabel borrowingIDLabel = new JLabel("Borrower ID:");
		borrowingIDLabel.setPreferredSize(new Dimension(110, 20));
		this.add(borrowingIDLabel);
		bID = new JTextField(20);
		this.add(bID);
		
		payBtn = new JButton("Pay Fine");
		this.add(payBtn);
		payBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				payfine();
			}
		
		});
		
	}
	
	private void payfine() {

		int bid;
		
		try {
			bid = Integer.parseInt(bID.getText());
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(frame, "Borrower ID should be an Integer." );
			return;
		}
		
		int fid = SQLFunctions.payFine(bid);
		
		System.out.println(fid);
		
		JOptionPane.showMessageDialog(frame, "Paid Fine." );
		
	}
	
	
}
