package libraryGUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import sqlFunctions.SQLFunctionsLibrarian;

public class AddBook extends JPanel {
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private JTextField bookNumber;
	private JTextField bookIsbn;
	private JTextField bookTitle;
	private JTextField bookMainAuthor;
	private JTextField bookPublisher;
	private JTextField bookPublicationYear;
	private JTextField subject;
	private JTextField otherAuthor;
	private JList subjects;
	private JList otherAuthors;
	private JButton addAuthorBtn;
	private JButton addSubjectBtn;
	private JButton addBtn;
	
	private Vector<String> subjectList;
	private Vector<String> authorList;

	public AddBook() {
		this.setPreferredSize(new Dimension(400, 450));
		
		subjectList = new Vector<String>();
		authorList = new Vector<String>();
		
		JLabel numberLabel = new JLabel("Call number:");
		numberLabel.setPreferredSize(new Dimension(80,20));
		this.add(numberLabel);
		bookNumber = new JTextField(20);
		this.add(bookNumber);
		
		JLabel isbnLabel = new JLabel("ISBN:");
		isbnLabel.setPreferredSize(new Dimension(80,20));
		this.add(isbnLabel);
		bookIsbn = new JTextField(20);
		this.add(bookIsbn);		
		
		JLabel titleLabel = new JLabel("Title:");
		titleLabel.setPreferredSize(new Dimension(80,20));
		this.add(titleLabel);
		bookTitle = new JTextField(20);
		this.add(bookTitle);
		
		JLabel publisherLabel = new JLabel("Publisher:");
		publisherLabel.setPreferredSize(new Dimension(80,20));
		this.add(publisherLabel);
		bookPublisher = new JTextField(20);
		this.add(bookPublisher);
		
		JLabel PublicationYearLabel = new JLabel("Year:");
		PublicationYearLabel.setPreferredSize(new Dimension(80,20));
		this.add(PublicationYearLabel);
		bookPublicationYear = new JTextField(20);
		this.add(bookPublicationYear);
		
		JLabel MainAuthorLabel = new JLabel("Main author:");
		MainAuthorLabel.setPreferredSize(new Dimension(80,20));
		this.add(MainAuthorLabel);
		bookMainAuthor = new JTextField(20);
		this.add(bookMainAuthor);
		
		JSeparator sep1 = new JSeparator();
		this.add(sep1);
		
		JLabel authorsLabel = new JLabel("Other authors:");
		authorsLabel.setPreferredSize(new Dimension(100,20));
		this.add(authorsLabel);
		otherAuthor = new JTextField(13);
		this.add(otherAuthor);
		addAuthorBtn = new JButton("Add");
		this.add(addAuthorBtn);
		otherAuthors = new JList();
		JScrollPane authorSP = new JScrollPane(otherAuthors);
		authorSP.setPreferredSize(new Dimension(300,70));
		this.add(authorSP);
		
		JLabel subjectsLabel = new JLabel("Subjects:");
		subjectsLabel.setPreferredSize(new Dimension(100,20));
		this.add(subjectsLabel);
		subject = new JTextField(13);
		this.add(subject);
		addSubjectBtn = new JButton("Add");
		this.add(addSubjectBtn);
		subjects = new JList();
		JScrollPane subjectSP = new JScrollPane(subjects);
		subjectSP.setPreferredSize(new Dimension(300,70));
		this.add(subjectSP);
		
		addBtn = new JButton("Add book");
		this.add(addBtn);
		
		addAuthorBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addAuthor();
			}
		});
		
		addSubjectBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addSubject();
			}
		});
		
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addBook();
			}

		});
	}
	
	private void addSubject() {
		String newsubj = subject.getText().trim();
		if (newsubj.length() > 0 && subjectList.size() <= 10) {
			subjectList.add(newsubj);
			subjects.setListData(subjectList);
		}
	}
	
	private void addAuthor() {
		String newauth = otherAuthor.getText().trim();
		if (newauth.length() > 0 && authorList.size() <= 10 && !authorList.contains(newauth)) {
			authorList.add(newauth);
			System.out.println("Adding author " + newauth + " to authorList");
			otherAuthors.setListData(authorList);
		}
	}

	private void addBook() {

		String callNumber = bookNumber.getText();
		int isbn;
		Integer publicationYear = null;
		String title = bookTitle.getText();
		String mainAuthor = bookMainAuthor.getText();
		String publisher = bookPublisher.getText();
		
		// Check for valid inputs
		if (callNumber.length() == 0) {
			JOptionPane.showMessageDialog(frame, "Invalid call number");
			return;
		}
		
		try {
			isbn = Integer.parseInt(bookIsbn.getText());
		} catch (NumberFormatException e) {			
			JOptionPane.showMessageDialog(frame, "Invalid ISBN");
			return;
		}
		
		if (title.length() == 0) {
			title = null;
		}
		if (mainAuthor.length() == 0) {
			mainAuthor = null;
		}
		if (publisher.length() == 0) {
			publisher = null;
		}

		try {
			publicationYear = Integer.parseInt(bookPublicationYear.getText());
			if (publicationYear < 1600 || publicationYear > 3000) {
				publicationYear = null;
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid publication year - leaving it blank");
		}
		// Add the book
		int copynum = SQLFunctionsLibrarian.addBook(callNumber, isbn, title, mainAuthor,
				publisher, publicationYear, authorList, subjectList);
		if (copynum == 0) {
			JOptionPane.showMessageDialog(frame, "Failed to add book - please check input values", "Error adding book", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(frame, "Adding copy " + copynum + " of book " + callNumber);
			JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
			topFrame.dispose();
		}
	}

}
