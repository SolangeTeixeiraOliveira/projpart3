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
		
		try {
			Integer.parseInt(bID.getText());
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
		
		int fid = SQLFunctionsBorrower.payFine(fineID);
		
		System.out.println(fid);
		
		JOptionPane.showMessageDialog(frame, "Paid Fine." );
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		topFrame.dispose();
	}
	
	
}
