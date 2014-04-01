package libraryGUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import sqlFunctions.SQLFunctionsBorrower;


public class PayFine extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField bID;
	private JTextField fID;
	private JButton payBtn;
	private JFrame frame;
	
	public PayFine() {
		this.setPreferredSize(new Dimension(400, 150));
		
		JLabel borrowingIDLabel = new JLabel("Borrower ID:");
		borrowingIDLabel.setPreferredSize(new Dimension(110, 20));
		this.add(borrowingIDLabel);
		bID = new JTextField(20);
		this.add(bID);
		
		JLabel fineIDLabel = new JLabel("Fine ID:");
		fineIDLabel.setPreferredSize(new Dimension(110, 20));
		this.add(fineIDLabel);
		fID = new JTextField(20);
		this.add(fID);
		
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
		
		int fineID;
		
		try {
			fineID = Integer.parseInt(fID.getText());
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(frame, "Fine ID should be an Integer." );
			return;
		}
		
		ResultSet rs = SQLFunctionsBorrower.payFineHelper(bid);
		
		try {
			if (rs.next()) {
				SQLFunctionsBorrower.payFine(fineID, bid);
				JOptionPane.showMessageDialog(frame, "Fine Paid.");
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
				topFrame.dispose();
			} else {
				JOptionPane.showMessageDialog(frame, "No Fine ID found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
