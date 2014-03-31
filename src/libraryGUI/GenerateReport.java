package libraryGUI;

import java.awt.Dimension;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sqlFunctions.SQLFunctions;


public class GenerateReport extends JPanel {
	
		private static final long serialVersionUID = 1L;

		private JTextField bookNumber;
		private JTextField outDate;
		private JTextField inDate;
		private JTextField bookTitle;
		private JTextField CheckedOutSubject;
	    private JTextField ReportPopularBooks;
		private JButton addBtn;

		public GenerateReport() {
			this.setPreferredSize(new Dimension(200, 300));
			JLabel ReportCheckedOutLabel = new JLabel("Report: CheckedOut Books");
			this.add(ReportCheckedOutLabel);
			JLabel subjectLabel = new JLabel("Subject:");
			this.add(subjectLabel);
			CheckedOutSubject = new JTextField(15);
			this.add(CheckedOutSubject);
			addBtn = new JButton("confirm");
			this.add(addBtn);
			addBtn.addActionListener(new ActionListener() {
				
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
					checkedOutBooks();					
	        	}
	        });
			
			JLabel ReportPopularBooksLabel = new JLabel("Report: Popular Books");
			this.add(ReportPopularBooksLabel);
			ReportPopularBooks = new JTextField(30);
			//this.add(ReportPopularBooks);
			addBtn = new JButton("Confirm");
			this.add(addBtn);
			addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			//	popularBook();		
	        		JFrame frame = new JFrame ("Isert Year");
	        		frame.setResizable(false);
	                frame.getContentPane().add (new PopupMenu());
	                frame.pack();
	                frame.setVisible (true);
	        	}
			});
		}
		
		private void checkedOutBooks() {
			String subject = CheckedOutSubject.getText().trim();
			
			JFrame frame = new JFrame ("Checked Out Books");
            frame.getContentPane().add (new DisplayCheckedOutBooks(subject));
            frame.pack();
            frame.setVisible (true);
		}
		
		private void popularBook() {
			JFrame frame = new JFrame ("Display The Most Popular Books");
            frame.getContentPane().add (new DisplayMostPopularItem());
            frame.pack();
            frame.setVisible (true);
            
		}
	}
