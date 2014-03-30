package libraryGUI;

import java.awt.Dimension;
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
		private JTextField ReportCheckedOut;
	    private JTextField ReportPopularBooks;
		private JButton addBtn;

		public GenerateReport() {
			this.setPreferredSize(new Dimension(300, 300));
			JLabel ReportCheckedOutLabel = new JLabel("Report: CheckedOut Books");
			this.add(ReportCheckedOutLabel);
			ReportCheckedOut = new JTextField(30);
			addBtn = new JButton("confirm");
			this.add(addBtn);
			addBtn.addActionListener(new ActionListener() {
				
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		JFrame frame = new JFrame ("Books checked Out");
	                frame.getContentPane().add(new GenerateReport());
	                frame.pack();
	                frame.setVisible (true);
	                	               
					bookcheckeOut();
					
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
				JFrame frame = new JFrame("Popular Books");
				frame.getContentPane().add(new GenerateReport());
				frame.pack();
				frame.setVisible(true);

				popularBook();
			}
			});
		}
		
		private void bookcheckeOut() {
			ResultSet report = SQLFunctions.getdisplayCheckOutAllBook();
			System.out.println(report);
		}
		
		private void popularBook() {
			int callNumber = Integer.parseInt(bookNumber.getText());
			int report = SQLFunctions.popuparBook(callNumber, outDate.getText(), inDate.getText(),
					bookTitle.getText());
			System.out.println(report);
		}
	}
	
