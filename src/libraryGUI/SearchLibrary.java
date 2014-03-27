package libraryGUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import sqlFunctions.SQLFunctions;

// Pop up for Searching a Book from the Library
public class SearchLibrary extends JPanel{
	
	private static final long serialVersionUID = 1L;

	private JTextField bookTitle;
	private JTextField bookAuthor;
	private JTextField bookSubject;
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
				

				if (obj == searchBtn) {		//If Search Button Pressed.
					//if search field is not provided
					if (bookTitle.getText().equals ("") && bookAuthor.getText().equals("") && bookSubject.getText().equals("")) {
						JOptionPane.showMessageDialog (frame, "Search Fields not Provided.");
						bookTitle.requestFocus ();
					}
					else 
					{
						searchbook();
					}
		
				}
			}
		});	
	}
	
	private void searchbook() {
		
		System.out.println("Going into SQL Query");
		ResultSet rs = SQLFunctions.searchbook(bookTitle.getText(), bookAuthor.getText(), bookSubject.getText());
		
//		String[] columnNames = {"CallNumber", "Title", "Author", "Subject", "Available Copies", "Unavailable Copies"};
//		Object[][] data;
//		String callnumber;
//		String book_title;
//		String book_author;
//		String book_subject;
//				//{new String callnum, new String bkTitle, new String bkAuthor, new String bkSubject, new int in_copies, new int out_copies};
//		data = {
//				while (rs.next()) {
//					callnumber = rs.getString(1);
//					book_title = rs.getString(2);
//					book_author = rs.getString(3);
//					book_subject = rs.getString(4);
//					} 
//				}
//		};
//		
//		JTable searchTable = new JTable(data, columnNames);
		
		
		
	}

	
}
