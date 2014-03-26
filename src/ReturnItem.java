import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class ReturnItem extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField callNumber;
	private JButton returnBtn;

	public ReturnItem() {
		this.setPreferredSize(new Dimension(400, 400));
		callNumber = new JTextField(20);
		this.add(callNumber);
		returnBtn = new JButton("Return");
		this.add(returnBtn);
		returnBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				processReturn(callNumber.getText());
			}
		});
	}
	
	private void processReturn(String callNumber) {
		
	}
}