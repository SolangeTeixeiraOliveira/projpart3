import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// Pop up for Searching a Book from the Library
public class SearchLibrary extends JPanel{
	
	private static final long serialVersionUID = 1L;

	private JTextField bookTitle;
	private JTextField bookAuthor;
	private JTextField bookSubject;
	private JButton searchBtn;
	
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
				searchbook();
			}
		
		});	
	}
	
	private void searchbook() {
		
		System.out.println("Going into SQL Query");
		String book = SQLFunctions.searchbook(bookTitle.getText(), bookAuthor
				.getText(), bookSubject.getText());
		System.out.println(book);
	}

	
}
