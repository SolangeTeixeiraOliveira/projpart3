import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LibraryWindow {
	
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Welcome to the Library");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Clerk panel
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.LIGHT_GRAY);
        panel1.setPreferredSize(new Dimension(200, 300));
        
        JLabel clerklabel = new JLabel("Clerk", SwingConstants.CENTER);
        clerklabel.setPreferredSize(new Dimension(130, 30));
        panel1.add(clerklabel);
        JButton addBorrowerBtn = new JButton("Add Borrower");
        addBorrowerBtn.setPreferredSize(new Dimension(130, 30));
        addBorrowerBtn.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new JFrame ("MyPanel");
                frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add (new AddBorrowerWindow());
                frame.pack();
                frame.setVisible (true);
        	}
        });
       
        panel1.add(addBorrowerBtn);
        JButton checkOutBtn = new JButton("Check Out Items");
        checkOutBtn.setPreferredSize(new Dimension(130, 30));
        panel1.add(checkOutBtn);
        JButton returnItemsBtn = new JButton("Return Items");
        returnItemsBtn.setPreferredSize(new Dimension(130, 30));
        panel1.add(returnItemsBtn);
        
        // Borrower panel
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.GRAY);
        panel2.setPreferredSize(new Dimension(200, 300));
        
        JLabel borrower = new JLabel("Borrower", SwingConstants.CENTER);
        borrower.setPreferredSize(new Dimension(130, 30));
        panel2.add(borrower);
        JButton borrowerbutton1 = new JButton("Search Library");
        borrowerbutton1.setPreferredSize(new Dimension(130, 30));
        panel2.add (borrowerbutton1);
        JButton borrowerbutton2 = new JButton("Check Account");
        borrowerbutton2.setPreferredSize(new Dimension(130, 30));
        panel2.add (borrowerbutton2);
        JButton borrowerbutton3 = new JButton("Hold Request");
        borrowerbutton3.setPreferredSize(new Dimension(130, 30));
        panel2.add (borrowerbutton3);
        JButton borrowerbutton4 = new JButton("Pay Fine");
        borrowerbutton4.setPreferredSize(new Dimension(130, 30));
        panel2.add (borrowerbutton4);
        
        // Librarian panel
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.LIGHT_GRAY);
        panel3.setPreferredSize(new Dimension(200,300));
        
        JLabel librarian = new JLabel("Librarian", SwingConstants.CENTER);
        librarian.setPreferredSize(new Dimension(130, 30));
        panel3.add(librarian);
        JButton librarianbutton1 = new JButton("Create Book");
        librarianbutton1.setPreferredSize(new Dimension(130, 30));
        panel3.add (librarianbutton1);
        JButton librarianbutton2 = new JButton("Add Book");
        librarianbutton2.setPreferredSize(new Dimension(130, 30));
        panel3.add (librarianbutton2);
        JButton librarianbutton3 = new JButton("Generate Report");
        librarianbutton3.setPreferredSize(new Dimension(130, 30));
        panel3.add (librarianbutton3);

        //Display the window.
        
        frame.add(panel1, BorderLayout.LINE_START);
        frame.add(panel2, BorderLayout.CENTER);
        frame.add(panel3, BorderLayout.LINE_END);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}