package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Entity.TrainSchedule;
import List.TrainScheduleList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrainScheduleGUI extends JFrame implements ActionListener, TableModelListener {

    private TrainScheduleList scheduleList;
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField, departureField, arrivalField, searchField;
    private JComboBox<String> searchDropdown;
    private JPanel contentPane;
    // Font size adjustment
    private Font labelFont = new Font("Arial", Font.PLAIN, 18);
    private Font fieldFont = new Font("Arial", Font.PLAIN, 16);
    private Font tableFont = new Font("Arial", Font.PLAIN, 18);
    private Font comboFont = new Font("Arial", Font.PLAIN, 16);

    public TrainScheduleGUI() {
        super("Train Schedule List");
        scheduleList = new TrainScheduleList(100, "GUI/Resources/schedules.txt");
        scheduleList.loadFromFile();

        // set the frame properties
        setTitle("Train Schedule List");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("GUI/Resources/train.jpeg").getImage());
        // Create table model and table
        String[] columnNames = { "ID", "Departure Time", "Arrival Time" };
        model = new DefaultTableModel(columnNames, 0);
        model.addTableModelListener(this);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setAutoCreateRowSorter(true);
        // Adjust font and row height for table
        table.setFont(tableFont);
        table.setRowHeight(30);

        // Populate table with schedules
        for (TrainSchedule schedule : scheduleList.getAllSchedules()) {
            Object[] row = { schedule.getScheduleID(), schedule.getDepartureTime(), schedule.getArrivalTime() };
            model.addRow(row);
        }

        // Create input fields
        idField = new JTextField(10);
        idField.setFont(fieldFont);

        departureField = new JTextField(10);
        departureField.setFont(fieldFont);

        arrivalField = new JTextField(10);
        arrivalField.setFont(fieldFont);

        // Create buttons
        JButton addButton = new JButton("Add");
        addButton.addActionListener(this);
        addButton.setBackground(new Color(0, 153, 0));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(this);
        removeButton.setBackground(new Color(204, 0, 0));
        removeButton.setForeground(Color.WHITE);
        removeButton.setFont(new Font("Arial", Font.BOLD, 14));
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        updateButton.setBackground(new Color(0, 102, 204));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        searchButton.setBackground(new Color(255, 153, 0));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        JButton backButton = new JButton("Go back to Dashboard");
        backButton.addActionListener(this);
        backButton.setBackground(new Color(255, 153, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Create dropdowns and search button
        searchDropdown = new JComboBox<>(new String[] { "ID", "Departure Time", "Arrival Time" });
        searchDropdown.setFont(comboFont);
        searchField = new JTextField(10);
        searchField.setFont(fieldFont);
        searchButton.addActionListener(this);

        // create panels for input fields and buttons
        JPanel inputPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.add(createLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(createLabel("Departure Time:"));
        inputPanel.add(departureField);
        inputPanel.add(createLabel("Arrival Time:"));
        inputPanel.add(arrivalField);
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
        setResizable(true);

        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setBorder(new EmptyBorder(0, 0, 5, 5));
        return label;
    }

    private boolean isValidTime(String time) {
        try {
            String[] parts = time.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            return hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60;
        } catch (Exception e) {
            return false;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("Search")) {
                String searchText = searchField.getText();
                if (searchDropdown.getSelectedItem().equals("ID")) {
                    int searchID = Integer.parseInt(searchText);
                    TrainSchedule schedule = scheduleList.getScheduleByID(searchID);
                    if (schedule != null) {
                        model.setRowCount(0);
                        Object[] row = { schedule.getScheduleID(), schedule.getDepartureTime(),
                                schedule.getArrivalTime() };
                        model.addRow(row);
                    } else {
                        JOptionPane.showMessageDialog(contentPane, "Schedule with ID " + searchID + " not found.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (searchDropdown.getSelectedItem().equals("Departure Time")) {
                    // Handle search by departure time
                } else if (searchDropdown.getSelectedItem().equals("Arrival Time")) {
                    // Handle search by arrival time
                }
            } else if (button.getText().equals("Add")) {
                String idText = idField.getText();
                String departureTime = departureField.getText();
                String arrivalTime = arrivalField.getText();

                if (idText.isEmpty() || departureTime.isEmpty() || arrivalTime.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Please fill in all the fields.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int id;
                try {
                    id = Integer.parseInt(idText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(contentPane, "Invalid ID. Please enter a valid integer value.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isValidTime(departureTime)) {
                    JOptionPane.showMessageDialog(contentPane,
                            "Invalid departure time. Please enter a valid time between 00:00 and 23:59.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isValidTime(arrivalTime)) {
                    JOptionPane.showMessageDialog(contentPane,
                            "Invalid arrival time. Please enter a valid time between 00:00 and 23:59.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                TrainSchedule schedule = new TrainSchedule(id, departureTime, arrivalTime);
                if (scheduleList.getScheduleByID(id) == null) {
                    scheduleList.addSchedule(schedule);
                    Object[] row = { id, departureTime, arrivalTime };
                    model.addRow(row);
                    scheduleList.saveToFile();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Schedule with ID " + id + " already exists.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    clearFields();
                }
            } else if (button.getText().equals("Remove")) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) model.getValueAt(selectedRow, 0);
                    TrainSchedule schedule = scheduleList.getScheduleByID(id);
                    scheduleList.removeSchedule(schedule);
                    model.removeRow(selectedRow);
                    scheduleList.saveToFile();

                    clearFields();
                }
            } else if (button.getText().equals("Update")) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) model.getValueAt(selectedRow, 0);
                    TrainSchedule schedule = scheduleList.getScheduleByID(id);
                    if (schedule != null) {
                        String departureTime = departureField.getText();
                        String arrivalTime = arrivalField.getText();
                        if (!departureTime.equals("")) {
                            schedule.setDepartureTime(departureTime);
                            model.setValueAt(departureTime, selectedRow, 1);
                        }
                        if (!arrivalTime.equals("")) {
                            schedule.setArrivalTime(arrivalTime);
                            model.setValueAt(arrivalTime, selectedRow, 2);
                        }
                        scheduleList.saveToFile();

                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(contentPane, "Schedule with ID " + id + " not found.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (button.getText().equals("Go back to Dashboard")) {
                dispose();
                DashboardGUI dashboardGUI = new DashboardGUI();
                dashboardGUI.setVisible(true);
            }
        }
    }

    private void clearFields() {
        idField.setText("");
        departureField.setText("");
        arrivalField.setText("");
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
            int row = e.getFirstRow();
            int column = e.getColumn();

            int id = (int) model.getValueAt(row, 0);
            String value = (String) model.getValueAt(row, column);
            TrainSchedule schedule = scheduleList.getScheduleByID(id);

            if (schedule != null) {
                switch (column) {
                    case 1:
                        schedule.setDepartureTime(value);
                        break;
                    case 2:
                        schedule.setArrivalTime(value);
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
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
        TrainScheduleGUI scheduleGUI = new TrainScheduleGUI();
        scheduleGUI.setVisible(true);
    }
}
