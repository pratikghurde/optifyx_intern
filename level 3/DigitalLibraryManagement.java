import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DigitalLibraryManagement extends JFrame implements ActionListener {
    // Declare the components
    JTextField bookIdField, userIdField, periodField, issueDateField;
    JButton submitButton;

    // Constructor to set up GUI components and event handling
    DigitalLibraryManagement() {
        // Labels
        JLabel bookIdLabel = new JLabel("Book ID(BID):");
        JLabel userIdLabel = new JLabel("User ID(UID):");
        JLabel periodLabel = new JLabel("Period(days):");
        JLabel issueDateLabel = new JLabel("Issued Date(DD-MM-YYYY):");

        // Input fields
        bookIdField = new JTextField();
        userIdField = new JTextField();
        periodField = new JTextField();
        issueDateField = new JTextField();

        // Button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        // Set up the layout
        setLayout(null);
        bookIdLabel.setBounds(50, 50, 150, 20);
        bookIdField.setBounds(200, 50, 150, 20);
        userIdLabel.setBounds(50, 100, 150, 20);
        userIdField.setBounds(200, 100, 150, 20);
        periodLabel.setBounds(50, 150, 150, 20);
        periodField.setBounds(200, 150, 150, 20);
        issueDateLabel.setBounds(50, 200, 150, 20);
        issueDateField.setBounds(200, 200, 150, 20);
        submitButton.setBounds(150, 250, 100, 30);

        // Add components to frame
        add(bookIdLabel);
        add(bookIdField);
        add(userIdLabel);
        add(userIdField);
        add(periodLabel);
        add(periodField);
        add(issueDateLabel);
        add(issueDateField);
        add(submitButton);

        // Frame settings
        setTitle("Digital Library Management");
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Action event for the submit button
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String bookId = bookIdField.getText();
            String userId = userIdField.getText();
            String period = periodField.getText();
            String issueDate = issueDateField.getText();

            // Validate and process the data
            try {
                int bookIdInt = Integer.parseInt(bookId);
                int userIdInt = Integer.parseInt(userId);
                int periodInt = Integer.parseInt(period);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date date = sdf.parse(issueDate);

                // Success message
                JOptionPane.showMessageDialog(this, "Book Issued Successfully\nBook ID: " + bookIdInt + 
                                                  "\nUser ID: " + userIdInt + 
                                                  "\nPeriod: " + periodInt + " days" +
                                                  "\nIssued Date: " + sdf.format(date));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter valid details.");
            }
        }
    }

    public static void main(String[] args) {
        new DigitalLibraryManagement();
    }
}
