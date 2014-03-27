package libraryGUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sqlFunctions.SQLFunctions;


public class GenerateReport extends JPanel {
	
		private static final long serialVersionUID = 1L;

		private JTextField bookNumber;
		private JTextField checkedOutDate;
		private JTextField dueDate;
		private JTextField bookTitle;
		private JTextField ReportCheckedOut;
		private JTextField ReportPopularBooks;
		private JButton addBtn;

		public GenerateReport() {
			this.setPreferredSize(new Dimension(400, 400));
			JLabel ReportCheckedOutLabel = new JLabel("ReportCheckedOut");
			this.add(ReportCheckedOutLabel);
			ReportCheckedOut = new JTextField(20);
			this.add(ReportCheckedOut);
			addBtn.addActionListener(new ActionListener() {
	        	
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		JFrame frame = new JFrame ("Books checked Out");
	                frame.getContentPane().add(new GenerateReport());
	                frame.pack();
	                frame.setVisible (true);
	        	}
	        });
			
			JLabel ReportPopularBooksLabel = new JLabel("ReportPopularBooks");
			this.add(ReportPopularBooksLabel);
			ReportPopularBooks = new JTextField(20);
			this.add(ReportPopularBooks);
			
			addBtn = new JButton("Add");
			this.add(addBtn);
			addBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					BookcheckeOut();
				}
			});
		}
		
		private void BookcheckeOut() {
			int callNumber = Integer.parseInt(bookNumber.getText());
			int report = SQLFunctions.BookcheckeOut(callNumber, checkedOutDate.getText(), dueDate.getText(),
					bookTitle.getText());
			System.out.println(report);
		}
	}
	
