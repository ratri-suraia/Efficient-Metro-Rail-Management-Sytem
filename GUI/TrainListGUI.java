package GUI;

import Entity.Train;
import Entity.TrainRoute;
import Entity.TrainSchedule;
import List.TrainList;
import List.TrainRouteList;
import List.TrainScheduleList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrainListGUI extends JFrame implements ActionListener {
    private TrainList trainList;
    private TrainRouteList trainRouteList;
    private TrainScheduleList scheduleList;
    private DefaultTableModel model;
    private JTable table;
    private JTextField idField, nameField, searchField;
    private JComboBox<String> sourceComboBox, destinationComboBox, departureTimeComboBox, arrivalTimeComboBox,
            searchDropdown;
    private JButton searchButton, addButton, removeButton, updateButton, backButton;
    private JPanel inputPanel, contentPane;

    // Font size adjustment
    private Font labelFont = new Font("Arial", Font.PLAIN, 18);
    private Font fieldFont = new Font("Arial", Font.PLAIN, 16);
    private Font buttonFont = new Font("Arial", Font.BOLD, 18);
    private Font tableFont = new Font("Arial", Font.PLAIN, 18);
    private Font comboFont = new Font("Arial", Font.PLAIN, 16);

    public TrainListGUI() {
        // Load train data from file
        trainList = new TrainList(100, "GUI/Resources/trainlist.txt");
        trainList.loadFromFile();

        // Load train schedules from file
        scheduleList = new TrainScheduleList(100, "GUI/Resources/schedules.txt");
        scheduleList.loadFromFile();
        // Load train routes from file
        trainRouteList = new TrainRouteList(100, "GUI/Resources/TrainRouteList.txt");
        trainRouteList.loadFromFile();

        // Set the frame properties
        setTitle("Train List");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("GUI/Resources/train.jpeg").getImage());

        // Create table model and table
        String[] columnNames = { "ID", "Name", "Source", "Destination", "Departure Time", "Arrival Time" };
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setAutoCreateRowSorter(true);

        // Adjust font and row height for table
        table.setFont(tableFont);
        table.setRowHeight(30);

        // Populate table with train data
        for (Train train : trainList.getAllTrains()) {
            Object[] row = { train.getTrainID(), train.getTransportName(), train.getSource(),
                    train.getDestination(), train.getSchedule().getDepartureTime(),
                    train.getSchedule().getArrivalTime() };
            model.addRow(row);
        }

        // Create input panel with text fields and buttons
        idField = new JTextField(8);
        idField.setFont(fieldFont);
        nameField = new JTextField(8);
        nameField.setFont(fieldFont);
        searchField = new JTextField(8);
        searchField.setFont(fieldFont);
        sourceComboBox = new JComboBox<>();
        sourceComboBox.setFont(comboFont);
        destinationComboBox = new JComboBox<>();
        destinationComboBox.setFont(comboFont);
        departureTimeComboBox = new JComboBox<>();
        departureTimeComboBox.setFont(comboFont);
        arrivalTimeComboBox = new JComboBox<>();
        arrivalTimeComboBox.setFont(comboFont);
        searchDropdown = new JComboBox<>(new String[] { "ID", "Name" });
        searchDropdown.setFont(comboFont);

        // Buttons
        searchButton = new JButton("Search");
        searchButton.setFont(buttonFont);
        addButton = new JButton("Add");
        addButton.setFont(buttonFont);
        removeButton = new JButton("Remove");
        removeButton.setFont(buttonFont);
        updateButton = new JButton("Update");
        updateButton.setFont(buttonFont);
        backButton = new JButton("Go back to Dashboard");
        backButton.setFont(buttonFont);

        // Set button colors
        searchButton.setBackground(Color.BLUE);
        searchButton.setForeground(Color.WHITE);
        addButton.setBackground(Color.GREEN);
        addButton.setForeground(Color.WHITE);
        removeButton.setBackground(Color.RED);
        removeButton.setForeground(Color.WHITE);
        updateButton.setBackground(Color.ORANGE);
        updateButton.setForeground(Color.WHITE);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);

        // Populate the source, destination, departure time, and arrival time dropdown
        // menus
       // Populate the source, destination, departure time, and arrival time dropdown menus
// Populate the source drop-down menu
for (TrainRoute trainRoute : trainRouteList.getAllRoutes()) {
    sourceComboBox.addItem(trainRoute.getSource());
}

// Populate the destination drop-down menu
for (TrainRoute trainRoute : trainRouteList.getAllRoutes()) {
    destinationComboBox.addItem(trainRoute.getDestination());
}

// Populate the departure time drop-down menu
for (TrainSchedule trainSchedule : scheduleList.getAllSchedules()) {
    departureTimeComboBox.addItem(trainSchedule.getDepartureTime());
}

// Populate the arrival time drop-down menu
for (TrainSchedule trainSchedule : scheduleList.getAllSchedules()) {
    arrivalTimeComboBox.addItem(trainSchedule.getArrivalTime());
}


        searchButton.addActionListener(this);
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        updateButton.addActionListener(this);
        backButton.addActionListener(this);

        inputPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(Color.WHITE);

        inputPanel.add(createLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(createLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(createLabel("Source:"));
        inputPanel.add(sourceComboBox);
        inputPanel.add(createLabel("Destination:"));
        inputPanel.add(destinationComboBox);
        inputPanel.add(createLabel("Departure Time:"));
        inputPanel.add(departureTimeComboBox);
        inputPanel.add(createLabel("Arrival Time:"));
        inputPanel.add(arrivalTimeComboBox);
        inputPanel.add(createLabel("Search by:"));
        inputPanel.add(searchDropdown);
        inputPanel.add(createLabel("Search text:"));
        inputPanel.add(searchField);
        inputPanel.add(searchButton);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(updateButton);
        inputPanel.add(backButton);

        // Create content pane with background image
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("GUI/Resources/background.jpg");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);
        contentPane.add(inputPanel, BorderLayout.SOUTH);

        // Add content pane to frame
        setContentPane(contentPane);

        // Set frame properties
        setResizable(true);
        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setBorder(new EmptyBorder(0, 0, 5, 5));
        return label;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("Search")) {
                String searchText = searchField.getText();
                if (searchDropdown.getSelectedItem().equals("ID")) {
                    int searchID = Integer.parseInt(searchText);
                    Train train = trainList.getTrainByID(searchID);
                    if (train != null) {
                        model.setRowCount(0);
                        Object[] row = { train.getTrainID(), train.getTransportName(), train.getSource(),
                                train.getDestination(), train.getSchedule().getDepartureTime(),
                                train.getSchedule().getArrivalTime() };
                        model.addRow(row);
                    } else {
                        JOptionPane.showMessageDialog(contentPane, "Train with ID " + searchID + " not found.",
                                "Error Message", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (searchDropdown.getSelectedItem().equals("Name")) {
                    Train[] trains = trainList.getTrainsByName(searchText);
                    if (trains != null && trains.length > 0) {
                        model.setRowCount(0);
                        for (Train train : trains) {
                            Object[] row = { train.getTrainID(), train.getTransportName(), train.getSource(),
                                    train.getDestination(), train.getSchedule().getDepartureTime(),
                                    train.getSchedule().getArrivalTime() };
                            model.addRow(row);
                        }
                    } else {
                        JOptionPane.showMessageDialog(contentPane, "Train with name " + searchText + " not found.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (button.getText().equals("Add")) {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String source = (String) sourceComboBox.getSelectedItem();
                String destination = (String) destinationComboBox.getSelectedItem();
                String departureTime = (String) departureTimeComboBox.getSelectedItem();
                String arrivalTime = (String) arrivalTimeComboBox.getSelectedItem();
                TrainRoute route = new TrainRoute(id, source, destination, id, id);
                TrainSchedule schedule = new TrainSchedule(id, departureTime, arrivalTime);
                Train train = new Train(id, name, schedule, route);

                if (trainList.getTrainByID(id) == null) {
                    trainList.addTrain(train);
                    trainList.saveToFile();
                    Object[] row = { id, name, source, destination, departureTime, arrivalTime };
                    model.addRow(row);
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Train with ID " + id + " already exists.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    clearFields();
                }
            } else if (button.getText().equals("Remove")) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) model.getValueAt(selectedRow, 0);
                    Train train = trainList.getTrainByID(id);
                    trainList.removeTrain(train);
                    trainList.saveToFile();
                    model.removeRow(selectedRow);
                    clearFields();
                }
            } else if (button.getText().equals("Update")) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) model.getValueAt(selectedRow, 0);
                    Train train = trainList.getTrainByID(id);
                    if (train != null) {
                        String name = nameField.getText();
                        String source = (String) sourceComboBox.getSelectedItem();
                        String destination = (String) destinationComboBox.getSelectedItem();
                        String departureTime = (String) departureTimeComboBox.getSelectedItem();
                        String arrivalTime = (String) arrivalTimeComboBox.getSelectedItem();

                        if (!name.equals("")) {
                            train.setTransportName(name);
                            model.setValueAt(name, selectedRow, 1);
                        }
                        if (!source.equals("")) {
                            train.setSource(source);
                            model.setValueAt(source, selectedRow, 2);
                        }
                        if (!destination.equals("")) {
                            train.setDestination(destination);
                            model.setValueAt(destination, selectedRow, 3);
                        }
                        if (!departureTime.equals("")) {
                            train.getSchedule().setDepartureTime(departureTime);
                            model.setValueAt(departureTime, selectedRow, 4);
                        }
                        if (!arrivalTime.equals("")) {
                            train.getSchedule().setArrivalTime(arrivalTime);
                            model.setValueAt(arrivalTime, selectedRow, 5);
                        }
                        trainList.saveToFile(); // Save the updated train information to file
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(contentPane, "Train with ID " + id + " not found.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (button.getText().equals("Go back to Dashboard")) {
                dispose();
                new DashboardGUI();
            }
        }
    }

    public void clearFields() {
        idField.setText("");
        nameField.setText("");
        sourceComboBox.setSelectedIndex(0);
        destinationComboBox.setSelectedIndex(0);
        departureTimeComboBox.setSelectedIndex(0);
        arrivalTimeComboBox.setSelectedIndex(0);
        searchField.setText("");
        searchDropdown.setSelectedIndex(0);
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
        // create an instance of TrainListGUI
        TrainListGUI trainListGUI = new TrainListGUI();

        // call the setVisible method on the instance
        trainListGUI.setVisible(true);

    }
}
