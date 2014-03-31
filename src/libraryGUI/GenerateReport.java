package libraryGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class GenerateReport extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField CheckedOutSubject;
	private JTextField year;
	private JTextField n;
	private JButton checkedOutBtn;
	private JButton popularBtn;
	private JFrame frame;

	public GenerateReport() {
		this.setPreferredSize(new Dimension(400, 320));
		
		// Panel for generating report of checked out books
		JPanel panel1 = new JPanel();
		panel1.setPreferredSize(new Dimension(390, 150));
		panel1.setBorder(new LineBorder(Color.DARK_GRAY));
		
		JLabel ReportCheckedOutLabel = new JLabel("Report: Checked Out Books");
		ReportCheckedOutLabel.setPreferredSize(new Dimension(375, 20));
		panel1.add(ReportCheckedOutLabel);
		JLabel subjectLabel = new JLabel("Subject (optional):");
		panel1.add(subjectLabel);
		CheckedOutSubject = new JTextField(22);
		panel1.add(CheckedOutSubject);
		checkedOutBtn = new JButton("Generate Report");
		panel1.add(checkedOutBtn);
		checkedOutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkedOutBooks();
			}
		});
		this.add(panel1);

		// Panel for generating report of popular books
		JPanel panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(390, 150));
		panel2.setBorder(new LineBorder(Color.DARK_GRAY));
		
		JLabel ReportPopularBooksLabel = new JLabel("Report: Popular Books");
		ReportPopularBooksLabel.setPreferredSize(new Dimension(375, 20));
		panel2.add(ReportPopularBooksLabel);
		JLabel yearLabel = new JLabel(" Year:");
		yearLabel.setPreferredSize(new Dimension(70, 30));
		panel2.add(yearLabel);
		year = new JTextField(4);
		panel2.add(year);
		
		n = new JTextField(2);
		JLabel nLabel = new JLabel("Number of top books:");
		nLabel.setPreferredSize(new Dimension(130, 30));
		panel2.add(nLabel);
		panel2.add(n);
		popularBtn = new JButton("Generate Report");
		panel2.add(popularBtn);
		popularBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popularBook();
			}
		});
		this.add(panel2);
	}

	private void checkedOutBooks() {
		String subject = CheckedOutSubject.getText().trim();

		JFrame frame = new JFrame("Checked Out Books");
		frame.getContentPane().add(new DisplayCheckedOutBooks(subject));
		frame.pack();
		frame.setVisible(true);
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
			JOptionPane.showMessageDialog(frame, "Invalid year or number");
			return;
		}

		JFrame frame = new JFrame(nStr + " Most Popular Books in " + yearStr);
		frame.getContentPane().add(new DisplayMostPopularItem(yearInt, nInt));
		frame.pack();
		frame.setVisible(true);
	}
}
