package GUI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class tempCodeRunnerFile extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JCheckBox rememberMeCheckbox;

    public tempCodeRunnerFile() {
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

            private void loadSavedCredentials() {
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

    private void loadSavedCredentials() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}