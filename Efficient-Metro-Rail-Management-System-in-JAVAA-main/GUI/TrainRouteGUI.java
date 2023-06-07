package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Entity.TrainRoute;
import List.TrainRouteList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrainRouteGUI extends JFrame implements ActionListener, TableModelListener {

    private TrainRouteList trainRouteList;
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField, sourceField, destinationField, distanceField, durationField, searchField;
    private JComboBox<String> searchDropdown;
    private JPanel contentPane;
    private Font labelFont = new Font("Arial", Font.PLAIN, 18);
    private Font fieldFont = new Font("Arial", Font.PLAIN, 16);
    private Font tableFont = new Font("Arial", Font.PLAIN, 18);
    private Font comboFont = new Font("Arial", Font.PLAIN, 16);

    public TrainRouteGUI() {
        super("Train Route List");
        trainRouteList = new TrainRouteList(100, "GUI/Resources/TrainRouteList.txt");
        trainRouteList.loadFromFile();

        setTitle("Train Route List");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("GUI/Resources/train.jpeg").getImage());

        String[] columnNames = { "ID", "Source", "Destination", "Distance(KM)", "Duration(Hours)" };
        model = new DefaultTableModel(columnNames, 0);
        model.addTableModelListener(this);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setAutoCreateRowSorter(true);

        table.setFont(tableFont);
        table.setRowHeight(30);

        for (TrainRoute route : trainRouteList.getAllRoutes()) {
            Object[] row = { route.getID(), route.getSource(), route.getDestination(), route.getDistance(),
                    route.getDuration() };
            model.addRow(row);
        }

        idField = new JTextField(10);
        idField.setFont(fieldFont);

        sourceField = new JTextField(10);
        sourceField.setFont(fieldFont);

        destinationField = new JTextField(10);
        destinationField.setFont(fieldFont);

        distanceField = new JTextField(10);
        distanceField.setFont(fieldFont);

        durationField = new JTextField(10);
        durationField.setFont(fieldFont);

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
        searchDropdown = new JComboBox<>(new String[] { "ID" });
        searchDropdown.setFont(comboFont);
        searchField = new JTextField(10);
        searchField.setFont(fieldFont);
        searchButton.addActionListener(this);

        JPanel inputPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.add(createLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(createLabel("Source:"));
        inputPanel.add(sourceField);
        inputPanel.add(createLabel("Destination:"));
        inputPanel.add(destinationField);
        inputPanel.add(createLabel("Distance:"));
        inputPanel.add(distanceField);
        inputPanel.add(createLabel("Duration:"));
        inputPanel.add(durationField);
        inputPanel.add(createLabel("Search by:"));
        inputPanel.add(searchDropdown);
        inputPanel.add(createLabel("Search text:"));
        inputPanel.add(searchField);
        inputPanel.add(searchButton);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(updateButton);
        inputPanel.add(backButton);

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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("Search")) {
                String searchText = searchField.getText();
                if (searchDropdown.getSelectedItem().equals("ID")) {
                    int searchID = Integer.parseInt(searchText);
                    TrainRoute route = trainRouteList.getRouteByID(searchID);
                    if (route != null) {
                        model.setRowCount(0);
                        Object[] row = { route.getID(), route.getSource(), route.getDestination(), route.getDistance(),
                                route.getDuration() };
                        model.addRow(row);
                    } else {
                        JOptionPane.showMessageDialog(contentPane, "Route with ID " + searchID + " not found.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                }
            } else if (button.getText().equals("Add")) {
                int id = Integer.parseInt(idField.getText());
                String source = sourceField.getText();
                String destination = destinationField.getText();
                int distance = Integer.parseInt(distanceField.getText());
                int duration = Integer.parseInt(durationField.getText());
                TrainRoute route = new TrainRoute(id, source, destination, distance, duration);
                if (trainRouteList.getRouteByID(id) == null) {
                    trainRouteList.addRoute(route);
                    trainRouteList.saveToFile();
                    Object[] row = { id, source, destination, distance, duration };
                    model.addRow(row);
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Route with ID " + id + " already exists.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    clearFields();
                }
            } else if (button.getText().equals("Remove")) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) model.getValueAt(selectedRow, 0);
                    TrainRoute route = trainRouteList.getRouteByID(id);
                    trainRouteList.removeRoute(route);
                    model.removeRow(selectedRow);
                    clearFields();
                }
            } else if (button.getText().equals("Update")) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) model.getValueAt(selectedRow, 0);
                    TrainRoute route = trainRouteList.getRouteByID(id);
                    if (route != null) {
                        String source = sourceField.getText();
                        String destination = destinationField.getText();
                        int distance = Integer.parseInt(distanceField.getText());
                        int duration = Integer.parseInt(durationField.getText());

                        if (!source.equals("")) {
                            route.setSource(source);
                            model.setValueAt(source, selectedRow, 1);
                        }
                        if (!destination.equals("")) {
                            route.setDestination(destination);
                            model.setValueAt(destination, selectedRow, 2);
                        }
                        if (!distanceField.getText().equals("")) {
                            route.setDistance(distance);
                            model.setValueAt(distance, selectedRow, 3);
                        }
                        if (!durationField.getText().equals("")) {
                            route.setDuration(duration);
                            model.setValueAt(duration, selectedRow, 4);
                        }

                        trainRouteList.updateRoute(route);
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(contentPane, "Route with ID " + id + " not found.", "Error",
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
        sourceField.setText("");
        destinationField.setText("");
        distanceField.setText("");
        durationField.setText("");
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
            int row = e.getFirstRow();
            int column = e.getColumn();

            int id = (int) model.getValueAt(row, 0);
            String value = (String) model.getValueAt(row, column);
            TrainRoute route = trainRouteList.getRouteByID(id);

            if (route != null) {
                switch (column) {
                    case 1:
                        route.setSource(value);
                        break;
                    case 2:
                        route.setDestination(value);
                        break;
                    case 3:
                        int distance = Integer.parseInt(value);
                        route.setDistance(distance);
                        break;
                    case 4:
                        int duration = Integer.parseInt(value);
                        route.setDuration(duration);
                        break;
                }
                trainRouteList.updateRoute(route);
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
        TrainRouteGUI trainRouteGUI = new TrainRouteGUI();
        trainRouteGUI.setVisible(true);
    }
}
