package libraryGUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GenerateReport extends JPanel {
	
		private static final long serialVersionUID = 1L;

		private JTextField CheckedOutSubject;
	    private JTextField year;
	    private JTextField n;
		private JButton addBtn;

		public GenerateReport() {
			this.setPreferredSize(new Dimension(200, 300));
			JLabel ReportCheckedOutLabel = new JLabel("Report: CheckedOut Books");
			this.add(ReportCheckedOutLabel);
			JLabel subjectLabel = new JLabel("Subject (optional):");
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
			this.setPreferredSize(new Dimension(200, 350));
			JLabel ReportPopularBooksLabel = new JLabel("Report: Popular Books");
			this.add(ReportPopularBooksLabel);
			JLabel yearLabel = new JLabel(" Year:");
			this.add(yearLabel);
			year = new JTextField(15);
			n = new JTextField(2);
			this.add(year);
			JLabel nLabel = new JLabel("Number of top books:");
			this.add(nLabel);
			this.add(n);
			addBtn = new JButton("Confirm");
			this.add(addBtn);
			addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popularBook();		
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
			String yearStr = year.getText();
			String nStr = n.getText();
			int yearInt;
			int nInt;
			
			try {
				yearInt = Integer.parseInt(yearStr);
				nInt = Integer.parseInt(nStr);
			} catch (NumberFormatException e) {
				System.out.println("Invalid year or number");
				return;
			}
			
			JFrame frame = new JFrame (nStr + " Most Popular Books in " + yearStr);
            frame.getContentPane().add (new DisplayMostPopularItem(yearInt, nInt));
            frame.pack();
            frame.setVisible (true);
		}
	}
