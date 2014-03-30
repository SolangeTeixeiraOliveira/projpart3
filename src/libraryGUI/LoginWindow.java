package libraryGUI;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.beans.*; //property change stuff
import java.awt.*;
import java.awt.event.*;
 
public class LoginWindow extends JDialog
                   implements ActionListener,
                              PropertyChangeListener {
    private String typedText = null;
    private JTextField username;
    private JTextField password;
 
    private JOptionPane optionPane;
 
    private String btnString1 = "Enter";
 
    /**
     * Returns null if the typed string was invalid;
     * otherwise, returns the string as the user entered it.
     */
    public String getValidatedText() {
        return typedText;
    }
 
    /** Creates the reusable dialog. */
    public LoginWindow(Frame aFrame) {
        super(aFrame, true);
        setTitle("Log In");
 
        //Create an array of the text and components to be displayed.
        String userTypeLabel = "Log in as:";
    	JComboBox userType = new JComboBox();
    	userType = new JComboBox();
		userType.setPreferredSize(new Dimension(225, 20));
		userType.addItem("Borrower");
		userType.addItem("Clerk");
		userType.addItem("Librarian");
		
    	username = new JTextField(20);
    	password = new JTextField(20);
        
        Object[] array = {userTypeLabel, userType, username, password};
 
        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {"Log In"};
 
        //Create the JOptionPane.
        optionPane = new JOptionPane(array,
                                    JOptionPane.QUESTION_MESSAGE,
                                    JOptionPane.YES_NO_OPTION,
                                    null,
                                    options,
                                    options[0]);
 
        //Make this dialog display it.
        setContentPane(optionPane);
 
        //Handle window closing correctly.
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
 
        //Ensure the text field always gets the first focus.
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                username.requestFocusInWindow();
            }
        });
 
        //Register an event handler that puts the text into the option pane.
        username.addActionListener(this);
 
        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);
    }
 
    /** This method handles events for the text field. */
    public void actionPerformed(ActionEvent e) {
        optionPane.setValue(btnString1);
    }
 
    /** This method reacts to state changes in the option pane. */
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();
 
        if (isVisible()
         && (e.getSource() == optionPane)
         && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
             JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = optionPane.getValue();
 
            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                //ignore reset
                return;
            }
 
            //Reset the JOptionPane's value.
            //If you don't do this, then if the user
            //presses the same button next time, no
            //property change event will be fired.
            optionPane.setValue(
                    JOptionPane.UNINITIALIZED_VALUE);
 
        }
    }
 
    /** This method clears the dialog and hides it. */
    public void clearAndHide() {
        username.setText(null);
        password.setText(null);
        setVisible(false);
    }
}