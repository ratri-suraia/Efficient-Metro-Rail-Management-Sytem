package GUI;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardGUI extends JFrame implements ActionListener {
    private JButton trainsButton;
    private JButton routesButton;
    private JButton schedulesButton;
    private JButton ticketsButton;
  //  private JButton paymentsButton;
    private JButton logoutButton;
    private JButton passengersButton;
    private JButton aboutUsButton;

    public DashboardGUI() {
        // Set up the frame
        setTitle("Metro Rail Management System - Dashboard");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("GUI/Resources/train.jpeg").getImage());
        setLocationRelativeTo(null);

        // Create the buttons
        passengersButton = new JButton("Passengers");
        trainsButton = new JButton("Trains");
        routesButton = new JButton("Routes");
        schedulesButton = new JButton("Schedules");
        ticketsButton = new JButton("Tickets");
        logoutButton = new JButton("Logout");
        aboutUsButton = new JButton("About Us");

        // Add action listeners to the buttons
        passengersButton.addActionListener(this);
        trainsButton.addActionListener(this);
        routesButton.addActionListener(this);
        schedulesButton.addActionListener(this);
        ticketsButton.addActionListener(this);
        logoutButton.addActionListener(this);
        aboutUsButton.addActionListener(this);

        // Create the panel and add the components to it
        JPanel panel = new JPanel(new GridLayout(7, 1));
        panel.add(passengersButton);
        panel.add(trainsButton);
        panel.add(routesButton);
        panel.add(schedulesButton);
        panel.add(ticketsButton);
        panel.add(aboutUsButton);
        panel.add(logoutButton);

        // Add the panel to the frame
        add(panel);

        // Display the frame
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == passengersButton) {
            dispose();
            new PassengerListGUI();
        } else if (e.getSource() == trainsButton) {
            dispose();
            new TrainListGUI();
        }
        else if (e.getSource() == routesButton) {
            dispose();
            new TrainRouteGUI();
        }
        
        else if (e.getSource() == schedulesButton) {
            dispose();
            new TrainScheduleGUI();
        }
         else if (e.getSource() == ticketsButton) {
            dispose();
            new TicketGUI();
        }  
        else if (e.getSource() == aboutUsButton) {
            String message = "<html><b>Created By MD.Istiak Ahamed</b></html><br><br>";
            JOptionPane.showMessageDialog(this, message, "About Us", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == logoutButton) {
            dispose();
            new LoginGUI();
              }
    }
}
