package libraryGUI;

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
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Welcome to the Library");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Clerk panel
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.LIGHT_GRAY);
        panel1.setPreferredSize(new Dimension(200, 300));
        
        JLabel clerklabel = new JLabel("Clerk", SwingConstants.CENTER);
        clerklabel.setPreferredSize(new Dimension(160, 30));
        panel1.add(clerklabel);
        JButton addBorrowerBtn = new JButton("Add Borrower");
        addBorrowerBtn.setPreferredSize(new Dimension(160, 30));
        addBorrowerBtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new JFrame ("Add Borrower");
        		frame.setResizable(false);
                frame.getContentPane().add (new AddBorrower());
                frame.pack();
                frame.setVisible (true);
        	}
        });
       
		panel1.add(addBorrowerBtn);
		JButton checkOutBtn = new JButton("Check Out Items");
		checkOutBtn.setPreferredSize(new Dimension(160, 30));
		checkOutBtn.addActionListener(new ActionListener() { 	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new JFrame ("Check Out Items");
        		frame.setResizable(false);
                frame.getContentPane().add (new CheckOut());
                frame.pack();
                frame.setVisible (true);
        	}
        });
        panel1.add(checkOutBtn);
        JButton returnItemBtn = new JButton("Return Item");
        returnItemBtn.setPreferredSize(new Dimension(160, 30));
        returnItemBtn.addActionListener(new ActionListener() { 	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new JFrame ("Return Item");
        		frame.setResizable(false);
                frame.getContentPane().add (new ReturnItem());
                frame.pack();
                frame.setVisible (true);
        	}
        });
        panel1.add(returnItemBtn);
        JButton overdueItemsBtn = new JButton("Check Overdue Items");
        overdueItemsBtn.setPreferredSize(new Dimension(160, 30));
        overdueItemsBtn.addActionListener(new ActionListener() { 	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new JFrame ("Overdue Items");
        		frame.setResizable(false);
                frame.getContentPane().add (new CheckOverdue());
                frame.pack();
                frame.setVisible (true);
        	}
        });
        panel1.add(overdueItemsBtn);
        
        // Borrower panel
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.GRAY);
        panel2.setPreferredSize(new Dimension(200, 300));
        
        JLabel borrower = new JLabel("Borrower", SwingConstants.CENTER);
        borrower.setPreferredSize(new Dimension(160, 30));
        panel2.add(borrower);
        
        // Search Library button
        JButton searchLibBtn = new JButton("Search Library");
        searchLibBtn.setPreferredSize(new Dimension(160, 30));
        // Search Library pop up Frame to start the search
        searchLibBtn.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new JFrame ("Search Library");
        		frame.setResizable(false);
                frame.getContentPane().add (new SearchLibrary());
                frame.pack();
                frame.setVisible (true);
        	}
        });
        panel2.add (searchLibBtn);
        
        // Check User Account Button
        JButton checkAcctBtn = new JButton("Check Account");
        checkAcctBtn.setPreferredSize(new Dimension(160, 30));
        // Check User Account pop up Frame to get user's information
        checkAcctBtn.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new JFrame ("Check Your Account");
        		frame.setResizable(false);
                frame.getContentPane().add (new CheckAccount());
                frame.pack();
                frame.setVisible (true);
        	}
        });
        panel2.add (checkAcctBtn);
        
        // Hold Request Button
        JButton holdRequestBtn = new JButton("Hold Request");
        holdRequestBtn.setPreferredSize(new Dimension(160, 30));
        // Hold Request pop up Frame to place the hold request for a book that is out
        holdRequestBtn.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new JFrame ("Make a Hold Request");
        		frame.setResizable(false);
                frame.getContentPane().add (new HoldRequest());
                frame.pack();
                frame.setVisible (true);
        	}
        });
        panel2.add (holdRequestBtn);
        
        // Pay Fine Button
        JButton payFineBtn = new JButton("Pay Fine");
        payFineBtn.setPreferredSize(new Dimension(160, 30));
        // Pay Fine pop up Frame to pay the fine
        payFineBtn.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new JFrame ("Payment");
        		frame.setResizable(false);
                frame.getContentPane().add (new PayFine());
                frame.pack();
                frame.setVisible (true);
        	}
        });
        panel2.add (payFineBtn);
        
     // Librarian panel
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.LIGHT_GRAY);
        panel3.setPreferredSize(new Dimension(200,300));
               
        JLabel librarian = new JLabel("Librarian", SwingConstants.CENTER);
        librarian.setPreferredSize(new Dimension(160, 30));
        panel3.add(librarian);
        JButton librarianbutton1 = new JButton("Add Book");
        librarianbutton1.setPreferredSize(new Dimension(160, 30));
        librarianbutton1.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new JFrame ("Add Book");
        		frame.setResizable(false);
                frame.getContentPane().add(new AddBook());
                frame.pack();
                frame.setVisible (true);
        	}
        });
        panel3.add (librarianbutton1);
        
        JButton librarianbutton2 = new JButton("Generate Report");
        librarianbutton2.setPreferredSize(new Dimension(160, 30));
        librarianbutton2.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new JFrame ("Books checked Out");
                frame.getContentPane().add(new GenerateReport());
                frame.pack();
                frame.setVisible (true);
        	}
        });
        panel3.add (librarianbutton2);

        //Display the window.
        
        frame.add(panel1, BorderLayout.LINE_START);
        frame.add(panel2, BorderLayout.CENTER);
        frame.add(panel3, BorderLayout.LINE_END);
        frame.pack();
        frame.setVisible(true);
    }
    
    private void showLoginWindow() {
//    	JPanel loginPanel = new JPanel();
//    	
//    	JLabel userTypeLbl = new JLabel("Log in as:");
//    	JComboBox userType = new JComboBox();
//    	userType = new JComboBox();
//		userType.setPreferredSize(new Dimension(225, 20));
//		userType.addItem("Borrower");
//		userType.addItem("Clerk");
//		userType.addItem("Librarian");
//    	
//    	JLabel unameLbl = new JLabel("Username:");
//    	JLabel passwordLbl = new JLabel("Password:");
//    	JTextField username = new JTextField(20);
//    	JTextField password = new JTextField(20);
//    	JButton login = new JButton("Log in");
//    	
//    	loginPanel.add(userTypeLbl);
//    	loginPanel.add(userType);
//    	loginPanel.add(unameLbl);
//    	loginPanel.add(username);
//    	loginPanel.add(passwordLbl);
//    	loginPanel.add(password);
//    	loginPanel.add(login);
//    	
//    	login.addActionListener(new ActionListener() {
//        	@Override
//        	public void actionPerformed(ActionEvent e) {
//        		boolean success = verifyLogin();
//        		if (success) {
//        			createAndShowGUI();
//        		}
//        	}
//        });
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	/*final JFrame frame = new JFrame("Log In");
            	frame.setPreferredSize(new Dimension(300, 100));
            	LoginWindow loginWindow = new LoginWindow(frame);
                loginWindow.setVisible(true);*/
                createAndShowGUI();
            }
        });
    }
}