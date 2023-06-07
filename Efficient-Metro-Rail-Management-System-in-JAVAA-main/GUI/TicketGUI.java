package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.PrintService;
import javax.print.ServiceUI;
import javax.swing.*;
import Entity.*;
import Interface.TicketPrintable;
import List.PassengerList;
import List.TrainList;
import List.TrainScheduleList;

public class TicketGUI extends JFrame implements ActionListener {
    private PassengerList passengerList;
    private TrainScheduleList scheduleList;
    private TrainList trainList;
    private JComboBox<Passenger> passengerComboBox;
    private JComboBox<Train> trainComboBox;
    private JButton calculateButton, confirmButton;
    private JPanel passengerDetailsPanel;
    private JLabel trainNameLabel, trainSourceLabel, trainDestinationLabel, trainDepartureTimeLabel, trainArrivalTimeLabel;
    private JLabel passengerNameLabel, passengerContactLabel, passengerEmailLabel;
    private JLabel ticketTypeLabel, distanceLabel, priceLabel;

    public TicketGUI() {
        // Load passenger data from file
        passengerList = new PassengerList(100, "GUI/Resources/passengers.txt");
        passengerList.loadFromFile();

        // Load train schedules from file
        scheduleList = new TrainScheduleList(100, "GUI/Resources/schedules.txt");
        scheduleList.loadFromFile();

        // Load train data from file
        trainList = new TrainList(100, "GUI/Resources/trainlist.txt");
        trainList.loadFromFile();

        // Set the frame properties
        setTitle("Ticket Booking");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("GUI/Resources/train.jpeg").getImage());

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

      // Create panel for passenger selection with GridLayout
      JPanel passengerPanel = createPanel("Select Passenger:", passengerComboBox = createComboBox(passengerList.getAllPassengers()));
      passengerComboBox.setRenderer(new PassengerComboBoxRenderer());
      passengerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

      // Create panel for train selection with GridLayout
      JPanel trainPanel = createPanel("Select Train:", trainComboBox = createComboBox(trainList.getAllTrains()));
      trainComboBox.setRenderer(new TrainComboBoxRenderer());
      trainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create panel for ticket information with GridLayout
        JPanel ticketPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        ticketPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ticketTypeLabel = new JLabel("Ticket Type:");
        distanceLabel = new JLabel("Distance (km):");
        priceLabel = new JLabel("Price (TK):");
        ticketPanel.add(ticketTypeLabel);
        ticketPanel.add(distanceLabel);
        ticketPanel.add(priceLabel);

        // Create button panel with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        calculateButton = createButton("Calculate");
        confirmButton = createButton("Confirm");

        calculateButton.addActionListener(this);
        confirmButton.addActionListener(this);
        buttonPanel.add(calculateButton);
        buttonPanel.add(confirmButton);

        // Create passenger details panel
        passengerDetailsPanel = new JPanel();
        passengerDetailsPanel.setLayout(new GridLayout(6, 2, 10, 10));
        passengerDetailsPanel.setBorder(BorderFactory.createTitledBorder("Passenger Details"));
            // Create labels for train information
    trainNameLabel = new JLabel("Train Name: ");
    trainSourceLabel = new JLabel("Source: ");
    trainDestinationLabel = new JLabel("Destination: ");
    trainDepartureTimeLabel = new JLabel("Departure Time: ");
    trainArrivalTimeLabel = new JLabel("Arrival Time: ");

    // Add labels to passenger details panel
    passengerDetailsPanel.add(trainNameLabel);
    passengerDetailsPanel.add(new JLabel());
    passengerDetailsPanel.add(trainSourceLabel);
    passengerDetailsPanel.add(new JLabel());
    passengerDetailsPanel.add(trainDestinationLabel);
    passengerDetailsPanel.add(new JLabel());
    passengerDetailsPanel.add(trainDepartureTimeLabel);
    passengerDetailsPanel.add(new JLabel());
    passengerDetailsPanel.add(trainArrivalTimeLabel);
    passengerDetailsPanel.add(new JLabel());

    // Add panels to the main panel
    mainPanel.add(passengerPanel, BorderLayout.NORTH);
    mainPanel.add(trainPanel, BorderLayout.CENTER);
    mainPanel.add(ticketPanel, BorderLayout.EAST);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    mainPanel.add(passengerDetailsPanel, BorderLayout.WEST);

    // Add main panel to the frame
    add(mainPanel);

    // Set frame properties
    setResizable(true);
    setVisible(true);
}

public void actionPerformed(ActionEvent e) {
    if (e.getSource() == calculateButton) {
        calculateTicketPrice();
    } else if (e.getSource() == confirmButton) {
        purchaseTicket();
    }
}

private void calculateTicketPrice() {
    // Get selected train
    Train selectedTrain = (Train) trainComboBox.getSelectedItem();

    // Get ticket type and distance
    String ticketType = ticketTypeLabel.getText();
    int distance = Integer.parseInt(distanceLabel.getText());

    // Calculate ticket price
    double price = selectedTrain.calculatePrice(distance);

    // Display calculated price
    priceLabel.setText(Double.toString(price));
}

private void purchaseTicket() {
    // Get selected passenger and train
    Passenger selectedPassenger = (Passenger) passengerComboBox.getSelectedItem();
    Train selectedTrain = (Train) trainComboBox.getSelectedItem();

    // Get ticket information
    String ticketType = ticketTypeLabel.getText();
    int distance = Integer.parseInt(distanceLabel.getText());
    double price = Double.parseDouble(priceLabel.getText());

    // Generate ticket ID
    int ticketID = generateTicketID();

    // Create ticket object
    Ticket ticket = new Ticket(ticketID, selectedPassenger, selectedTrain, distance, price);

    // Update train information labels
    trainNameLabel.setText("Train Name: " + selectedTrain.getTransportName());
    trainSourceLabel.setText("Source: " + selectedTrain.getSource());
    trainDestinationLabel.setText("Destination: " + selectedTrain.getDestination());
    trainDepartureTimeLabel.setText("Departure Time: " + selectedTrain.getDepartureTime());
    trainArrivalTimeLabel.setText("Arrival Time: " + selectedTrain.getArrivalTime());

    // Display success message
    JOptionPane.showMessageDialog(this, "Ticket purchased successfully!");

    // Reset fields
    ticketTypeLabel.setText("Ticket Type:");
    distanceLabel.setText("Distance (km):");
    priceLabel.setText("Price (TK):");
}

private <T> JComboBox<T> createComboBox(T[] items) {
    JComboBox<T> comboBox = new JComboBox<>(items);
    comboBox.setPreferredSize(new Dimension(200, 30));
    return comboBox;
}

private JButton createButton(String label) {
    JButton button = new JButton(label);
    button.setPreferredSize(new Dimension(100, 30));
    return button;
}

private void saveTicketAsPDF(Ticket ticket) {
    // Generate PDF file name
    String fileName = "GUI/Resources/Tickets/Ticket_" + ticket.getTicketID() + ".pdf";
    try {
        // Create PrinterJob
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Ticket");
        job.setPrintable(new TicketPrintable(ticket));

        // Select a printer for printing
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        PrintService printService = ServiceUI.printDialog(null, 200, 200, printServices, job.getPrintService(), null, null);
        if (printService != null) {
            job.setPrintService(printService);
        } else {
            return; // User canceled the print dialog
        }

        // Print the ticket
        job.print();

        JOptionPane.showMessageDialog(this, "Ticket saved as PDF successfully!");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private class PassengerComboBoxRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Passenger) {
            Passenger passenger = (Passenger) value;
            setText(passenger.getPassengerName());
        }
        return this;
    }
}

private class TrainComboBoxRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Train) {
            Train train = (Train) value;
            setText(train.getTransportName());
        }
        return this;
    }
}

private int generateTicketID() {
    // Generate ticket ID based on current timestamp
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String timestamp = dateFormat.format(new Date());
    return Integer.parseInt(timestamp);
}

private JPanel createPanel(String label, JComponent component) {
    JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
    JLabel titleLabel = new JLabel(label);
    panel.add(titleLabel);
    panel.add(component);
    return panel;
}

public static void main(String[] args) {
    // Set look and feel to Nimbus
    try {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (Exception e) {
        // Use default look and feel
    }

    // Run the GUI
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            new TicketGUI();
        }
    });
}
}

