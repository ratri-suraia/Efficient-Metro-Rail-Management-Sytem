package GUI;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;


public class LoginGUI extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JCheckBox rememberMeCheckbox;

    public LoginGUI() {
        // set the frame properties
        setTitle("Metro Rail Management System");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("GUI/Resources/train.jpeg").getImage());
        
        

        // create the components
        JLabel titleLabel = new JLabel("Metro Rail Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        rememberMeCheckbox = new JCheckBox("Remember me");
        rememberMeCheckbox.setForeground(Color.WHITE);
        rememberMeCheckbox.setOpaque(false);

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
        panel.setBounds(0, 0, 500, 400);

        titleLabel.setBounds(50, 30, 400, 30);
        panel.add(titleLabel);

        usernameLabel.setBounds(50, 100, 80, 25);
        usernameLabel.setForeground(Color.WHITE);
        panel.add(usernameLabel);

        usernameField.setBounds(140, 100, 200, 25);
        panel.add(usernameField);

        JLabel usernameIcon = new JLabel(new ImageIcon("GUI/Resources/username.png"));
        usernameIcon.setBounds(350, 100, 25, 25);
        panel.add(usernameIcon);

        passwordLabel.setBounds(50, 150, 80, 25);
        passwordLabel.setForeground(Color.WHITE);
        panel.add(passwordLabel);

        passwordField.setBounds(140, 150, 200, 25);
        panel.add(passwordField);

        JLabel passwordIcon = new JLabel(new ImageIcon("GUI/Resources/password.png"));
        passwordIcon.setBounds(350, 150, 25, 25);
        panel.add(passwordIcon);

        rememberMeCheckbox.setBounds(140, 200, 120, 25);
        panel.add(rememberMeCheckbox);

        loginButton.setBounds(140, 250, 80, 25);
        panel.add(loginButton);

        registerButton.setBounds(230, 250, 100, 25);
        panel.add(registerButton);

        // add the panel to the frame
        add(panel);

        // add action listeners to the buttons
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        // add an item listener to the checkbox
        rememberMeCheckbox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    loadSavedCredentials();
                }
            }
        });

        // load the saved username and password if the checkbox is selected
        if (rememberMeCheckbox.isSelected()) {
            loadSavedCredentials();
        }
        setResizable(false);
        // display the frame
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // get the username and password
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // check if the username and password are valid
            if (isValidUser(username, password)) {
                // save the username and password if the checkbox is selected
                if (rememberMeCheckbox.isSelected()) {
                    saveCredentials(username, password);
                }

                // close the LoginGUI and open the DashboardGUI
                dispose();
                new DashboardGUI();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == registerButton) {
            dispose();
            new RegisterGUI();
        }
    }

    private boolean isValidUser(String username, String password) {
        // check if the username and password are valid
        try {
            BufferedReader reader = new BufferedReader(new FileReader("GUI/Resources/users.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue; // skip empty lines
                }
                String[] parts = line.split(",");
                if (parts.length < 2) {
                    continue; //skip lines that don't have both username and password
                }
                if (parts[0].equals(username) && parts[1].equals(password)) {
                reader.close();
                return true;
                }
                }
                reader.close();
                } catch (IOException e) {
                e.printStackTrace();
                }
                return false;
                }
                private void saveCredentials(String username, String password) {
                    // save the username and password to a file
                    try {
                        FileWriter writer = new FileWriter("GUI/Resources/credentials.txt");
                        writer.write(username + "," + password);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                
                private void loadSavedCredentials() {
                    // load the saved username and password from a file
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("GUI/Resources/credentials.txt"));
                        String line = reader.readLine();
                        if (line != null) {
                            String[] parts = line.split(",");
                            if (parts.length >= 2) {
                                usernameField.setText(parts[0]);
                                passwordField.setText(parts[1]);
                            }
                        }
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                
                public static void main(String[] args) {
                    new LoginGUI();
                }
            }