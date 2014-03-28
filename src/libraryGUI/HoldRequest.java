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
import sqlFunctions.SQLFunctionsBorrower;


public class HoldRequest extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField borID;
	private JTextField callNumber;
	private JButton holdBtn;
	private JFrame frame;
	
	public HoldRequest() {
		this.setPreferredSize(new Dimension(450, 150));
		
		JLabel userLabel = new JLabel("User ID:");
		userLabel.setPreferredSize(new Dimension(150, 20));
		this.add(userLabel);
		borID = new JTextField(20);
		this.add(borID);
		
		JLabel callNumLabel = new JLabel("Book Call Number:");
		callNumLabel.setPreferredSize(new Dimension(150, 20));
		this.add(callNumLabel);
		callNumber = new JTextField(20);
		this.add(callNumber);
		
		holdBtn = new JButton("Hold");
		this.add(holdBtn);
		holdBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				holdrequest();
			}
		
		});
	}
	
	private void holdrequest() {

		int bid;
		
		try {
			bid = Integer.parseInt(borID.getText());
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(frame, "Borrower ID should be an Integer." );
			return;
		}
		//TODO: make sure there's a callNumber in the database
		int hid = SQLFunctionsBorrower.holdRequest(bid, callNumber.getText());
		
		System.out.println(hid);
		
		JOptionPane.showMessageDialog(frame, "New Hold Request Made." );
		
	}
	
}
