import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

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

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.DARK_GRAY);
        panel1.setPreferredSize(new Dimension(200, 500));
        
        JButton button1 = new JButton("Button 1");
        panel1.add(button1, BorderLayout.CENTER);
        JButton button2 = new JButton("Button 2");
        panel1.add(button2, BorderLayout.CENTER);
        
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.GRAY);
        panel2.setPreferredSize(new Dimension(200, 500));
        
        JButton borrower = new JButton("Borrower");
        panel2.add(borrower, BorderLayout.CENTER);
        
        JButton borrowerbutton1 = new JButton("Search Library");
        panel2.add (borrowerbutton1, BorderLayout.CENTER);
        JButton borrowerbutton2 = new JButton("Check Account");
        panel2.add (borrowerbutton2, BorderLayout.CENTER);
        JButton borrowerbutton3 = new JButton("Hold Request");
        panel2.add (borrowerbutton3, BorderLayout.CENTER);
        JButton borrowerbutton4 = new JButton("Pay Fine");
        panel2.add (borrowerbutton4, BorderLayout.CENTER);
        
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.LIGHT_GRAY);
        panel3.setPreferredSize(new Dimension(200, 500));
        frame.add(panel1, BorderLayout.LINE_START);
        frame.add(panel2, BorderLayout.CENTER);
        frame.add(panel3, BorderLayout.LINE_END);

        //Display the window.
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