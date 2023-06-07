package GUI;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
public class RegisterGUI extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField cityField;
    private JButton registerButton;
    private JButton cancelButton;
    public RegisterGUI() {
        // set the frame properties
        setTitle("Metro Rail Management System - Register");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("GUI/Resources/train.jpeg").getImage());
    
        // create the components
        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.WHITE);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmPasswordLabel.setForeground(Color.WHITE);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setForeground(Color.WHITE);
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        phoneLabel.setForeground(Color.WHITE);
        JLabel cityLabel = new JLabel("City:");
        cityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        cityLabel.setForeground(Color.WHITE);
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        emailField = new JTextField();
        phoneField = new JTextField();
        cityField = new JTextField();
        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");
    
        // create the panel and add the components to it
        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new ImageIcon("GUI/Resources/train.jpeg").getImage(), 0, 0, getWidth(), getHeight(), null);
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBounds(0, 0, 700, 700);
    
        titleLabel.setBounds(250, 30, 200, 50);
        panel.add(titleLabel);
    
        usernameLabel.setBounds(150, 120, 80, 25);
        panel.add(usernameLabel);
        usernameField.setBounds(240, 120, 200, 25);
        panel.add(usernameField);
        JLabel usernameIcon = new JLabel(new ImageIcon("GUI/Resources/username.png"));
        usernameIcon.setBounds(450, 120, 25, 25);
        panel.add(usernameIcon);
    
        passwordLabel.setBounds(150, 170, 80, 25);
        panel.add(passwordLabel);
        passwordField.setBounds(240, 170, 200, 25);
        panel.add(passwordField);
        JLabel passwordIcon = new JLabel(new ImageIcon("GUI/Resources/password.png"));
        passwordIcon.setBounds(450, 170, 25, 25);
        panel.add(passwordIcon);
    
        confirmPasswordLabel.setBounds(150, 220, 120, 25);
        panel.add(confirmPasswordLabel);
        confirmPasswordField.setBounds(270, 220, 170, 25);
        panel.add(confirmPasswordField);
        JLabel confirmPasswordIcon = new JLabel(new ImageIcon("GUI/Resources/password.png"));
        confirmPasswordIcon.setBounds(450, 220, 25, 25);
        panel.add(confirmPasswordIcon);
    
        emailLabel.setBounds(150, 270, 80, 25);
        panel.add(emailLabel);
        emailField.setBounds(240, 270, 200, 25);
        panel.add(emailField);
        JLabel emailIcon = new JLabel(new ImageIcon("GUI/Resources/email.png"));
        emailIcon.setBounds(450, 270, 25, 25);
        panel.add(emailIcon);
    
        phoneLabel.setBounds(150, 320, 120, 25);
        panel.add(phoneLabel);
        phoneField.setBounds(270, 320, 170, 25);
        panel.add(phoneField);
        JLabel phoneIcon = new JLabel(new ImageIcon("GUI/Resources/phone.png"));
        phoneIcon.setBounds(450, 320, 25, 25);
        panel.add(phoneIcon);
    
        cityLabel.setBounds(150, 370, 80, 25);
        panel.add(cityLabel);
        cityField.setBounds(240, 370, 200, 25);
        panel.add(cityField);
        JLabel cityIcon = new JLabel(new ImageIcon("GUI/Resources/city.png"));
        cityIcon.setBounds(450, 370, 25, 25);
        panel.add(cityIcon);
    
        registerButton.setBounds(240, 450, 100, 30);
        panel.add(registerButton);
        cancelButton.setBounds(360, 450, 100, 30);
        panel.add(cancelButton);
    
        // add action listeners to the buttons
        registerButton.addActionListener(this);
    cancelButton.addActionListener(this);

    // add the panel to the frame
    setContentPane(new JLabel(new ImageIcon("GUI/Resources/train.jpeg")));
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(panel, BorderLayout.CENTER);

    // display the frame
    setVisible(true);
}

public void actionPerformed(ActionEvent e) {
    if (e.getSource() == registerButton) {
        // get the user details
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String email = emailField.getText();
        String phone = phoneField.getText();
        String city = cityField.getText();

        // check if the passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // register the user
        boolean success = registerUser(username, password, email, phone, city);
        if (success) {
            JOptionPane.showMessageDialog(this, "User registered successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginGUI();
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else if (e.getSource() == cancelButton) {
        // launch the LoginGUI
        dispose();
        new LoginGUI();
    }
}

private boolean registerUser(String username, String password, String email, String phone, String city) {
    // check if the username already exists in the file
    try {
        BufferedReader reader = new BufferedReader(new FileReader("GUI/Resources/users.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                continue; // skip empty lines
            }
            String[] parts = line.split(",");
            if (parts.length < 5) {
                continue; // skip lines that don't have all the required fields
            }
            if (parts[0].equals(username)) {
                // username already exists
                reader.close();
                return false;
            }
        }
        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }

    // add the new user to the file
    try {
        BufferedWriter writer = new BufferedWriter(new FileWriter("GUI/Resources/users.txt", true));
        writer.write(username + "," + password + "," + email + "," + phone + "," + city);
        writer.newLine();
        writer.close();
        return true;
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}}