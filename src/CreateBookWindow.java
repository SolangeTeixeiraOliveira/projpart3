import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateBookWindow extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField bookNumber;
	private JTextField bookIsbn;
	private JTextField bookTitle;
	private JTextField bookMainAuthor;
	private JTextField bookPublisher;
	private JTextField bookPublicationYear;
	private JButton addBtn;

	public CreateBookWindow() {
		this.setPreferredSize(new Dimension(400, 400));
		JLabel numberLabel = new JLabel("Number:");
		this.add(numberLabel);
		bookNumber = new JTextField(20);
		this.add(bookNumber);
		
		this.setPreferredSize(new Dimension(400, 400));
		JLabel isbnLabel = new JLabel("ISBN:");
		this.add(isbnLabel);
		bookIsbn = new JTextField(20);
		this.add(bookIsbn);		
		
		this.setPreferredSize(new Dimension(400, 400));
		JLabel titleLabel = new JLabel("  Title:");
		this.add(titleLabel);
		bookTitle = new JTextField(20);
		this.add(bookTitle);
		
		this.setPreferredSize(new Dimension(300, 200));
		JLabel MainAuthorLabel = new JLabel(" MainAuthor:");
		this.add(MainAuthorLabel);
		bookMainAuthor = new JTextField(18);
		this.add(bookMainAuthor);
		
		this.setPreferredSize(new Dimension(300, 200));
		JLabel publisherLabel = new JLabel(" Publisher:");
		this.add(publisherLabel);
		bookPublisher = new JTextField(20);
		this.add(bookPublisher);
		
		this.setPreferredSize(new Dimension(300, 200));
		JLabel PublicationYearLabel = new JLabel("PublicationYear:");
		this.add(PublicationYearLabel);
		bookPublicationYear = new JTextField(15);
		this.add(bookPublicationYear);
		
		this.setPreferredSize(new Dimension(300, 200));
		addBtn = new JButton("Add");
		this.add(addBtn);
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addBook();
			}

		});
	}

	private void addBook() {

		int callNumber = Integer.parseInt(bookNumber.getText());
		int isbn = Integer.parseInt(bookIsbn.getText());
		int publicationYear = Integer.parseInt(bookPublicationYear.getText());
		
		int book = SQLFunctions.addBook(callNumber, isbn, bookTitle.getText(),
				bookMainAuthor.getText(), bookPublisher.getText(),
				publicationYear);
		System.out.println(book);
	}

}
