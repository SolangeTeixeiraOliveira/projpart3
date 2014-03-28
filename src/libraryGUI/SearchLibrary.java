package libraryGUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


// Pop up for Searching a Book from the Library
public class SearchLibrary extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public static JTextField bookTitle;
	public static JTextField bookAuthor;
	public static JTextField bookSubject;
	private JButton searchBtn;
	private JFrame frame;
	
	public SearchLibrary() {
		
		// Making the label of the pop up
		this.setPreferredSize(new Dimension(400, 150));
		
		JLabel titleLabel = new JLabel("Title:");
		titleLabel.setPreferredSize(new Dimension(110, 20));
		this.add(titleLabel);
		bookTitle = new JTextField(20);
		this.add(bookTitle);
		
		JLabel authorLabel = new JLabel("Author:");
		authorLabel.setPreferredSize(new Dimension(110, 20));
		this.add(authorLabel);
		bookAuthor = new JTextField(20);
		this.add(bookAuthor);
		
		JLabel subjectLabel = new JLabel("Subject:");
		subjectLabel.setPreferredSize(new Dimension(110, 20));
		this.add(subjectLabel);
		bookSubject = new JTextField(20);
		this.add(bookSubject);
		
		searchBtn = new JButton("Search");
		this.add(searchBtn);
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Object obj = arg0.getSource();
				//If Search Button Pressed.
				if (obj == searchBtn) {		
					//If search fields are not provided
					if (bookTitle.getText().equals ("") && bookAuthor.getText().equals("") && bookSubject.getText().equals("")) {
						JOptionPane.showMessageDialog (frame, "Search Fields not Provided.");
						bookTitle.requestFocus ();
					}
					else 
					{
						JFrame frame = new JFrame ("Search Results");
		        		frame.setResizable(false);
		                frame.getContentPane().add (new SearchResults());
		                frame.pack();
		                frame.setVisible (true);
					}
		
				}
			}
		});	
	}
					
}
