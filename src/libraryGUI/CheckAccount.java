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


public class CheckAccount extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public static JTextField borID;
	private JButton checkAcctBtn;
	private JFrame frame;
	
	public CheckAccount() {
		
		// Making the label of the pop up
        this.setPreferredSize(new Dimension(400, 150));
		
		JLabel userIDLabel = new JLabel("User ID:");
		userIDLabel.setPreferredSize(new Dimension(110, 20));
		this.add(userIDLabel);
		borID = new JTextField(20);
		this.add(borID);
		
		checkAcctBtn = new JButton("Confirm");
		this.add(checkAcctBtn);
		checkAcctBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Object obj = arg0.getSource();
				//If Search Button Pressed.
				if (obj == checkAcctBtn) {		
					//If search fields are not provided
					if (borID.getText().equals ("")) {
						JOptionPane.showMessageDialog (frame, "Search Fields not Provided.");
						borID.requestFocus ();
					}
					else 
					{
						JFrame frame = new JFrame ("Account Information");
		        		frame.setResizable(false);
		                frame.getContentPane().add (new CheckAcctResults());
		                frame.pack();
		                frame.setVisible (true);
					}
		
				}
			}
		
		});	
	}
	
}
