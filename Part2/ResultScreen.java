import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// class that creates the result screen after a round has ended
public class ResultScreen extends JPanel{
    
    private ArrayList<Result> results;

    private JComboBox<String> metricSelect;
    private JTable table;
    private DefaultTableModel tableModel;

    public ResultScreen(ArrayList<Result> results) {
        this.results = results;

        setBackground(new Color(0xeae4cf));
        setLayout(new BorderLayout());

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(0xeae4cf));

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(new Color(0xeae4cf));
        wrapper.setPreferredSize(new Dimension(800, 500));

        wrapper.add(selectRow());
        wrapper.add(tableRow());

        center.add(wrapper);
        add(center, BorderLayout.CENTER);

        updateTable("WPM");
    }

    // combo box used to change table based on the variablese tracked
    private JPanel selectRow() {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(0xeae4cf));

        JLabel label = new JLabel("Select Metric:");
        label.setFont(new Font("Monospaced", Font.BOLD, 22));
        label.setForeground(new Color(0xada998));

        metricSelect = new JComboBox<>(new String[] {
            "WPM",
            "Accuracy",
            "Burnouts",
            "Accuracy Change",
            "Time Taken"
        });

        metricSelect.setForeground(new Color(0xada998));

        metricSelect.addActionListener(e -> {
            String selected = (String) metricSelect.getSelectedItem();
            updateTable(selected);
        });

        row.add(label, BorderLayout.WEST);
        row.add(metricSelect, BorderLayout.CENTER);

        return row;
    }

    // method that initialises a table in swing
    private JPanel tableRow() {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(0xeae4cf));

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        table.setFont(new Font("Monospaced", Font.PLAIN, 18));
        table.setRowHeight(30);

        JScrollPane scroll = new JScrollPane(table);

        row.add(scroll, BorderLayout.CENTER);

        return row;
    }

    // method that updates the table based off of user option 
    private void updateTable(String metric) {

        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        tableModel.addColumn("Position");
        tableModel.addColumn("Name");
        tableModel.addColumn(metric);

        // Sort results based on selected metric
        ArrayList<Result> sortedResults = new ArrayList<>(results);
        sortedResults.sort((r1, r2) -> {
            if (metric.equals("WPM")) {
                // WPM: highest to lowest (descending)
                return Double.compare(r2.getWPM(), r1.getWPM());
            }
            else if (metric.equals("Accuracy")) {
                // Accuracy: highest to lowest (descending)
                return Double.compare(r2.getAccuracy(), r1.getAccuracy());
            }
            else if (metric.equals("Burnouts")) {
                // Burnouts: lowest to highest (ascending)
                return Integer.compare(r1.getBurnoutCount(), r2.getBurnoutCount());
            }
            else if (metric.equals("Accuracy Change")) {
                // Accuracy Change: lowest to highest (ascending)
                return Double.compare(r1.getAccuracyChange(), r2.getAccuracyChange());
            }
            else if (metric.equals("Time Taken")) {
                // Time Taken: lowest to highest (ascending)
                return Double.compare(r1.getTimeTaken(), r2.getTimeTaken());
            }
            return 0;
        });

        // second pass that assigns the value 
        for (int rank = 0; rank < sortedResults.size(); rank++) {
            Result r = sortedResults.get(rank);
            Object value;

            if (metric.equals("WPM")) {
                value = String.format("%.2f", r.getWPM());
            }
            else if (metric.equals("Accuracy")) {
                value = String.format("%.2f", r.getAccuracy());
            }
            else if (metric.equals("Burnouts")) {
                value = r.getBurnoutCount();
            }
            else if (metric.equals("Accuracy Change")) {
                value = String.format("%.4f", r.getAccuracyChange());
            }
            else if (metric.equals("Time Taken")) {
                value = String.format("%.2f", r.getTimeTaken());
            }
            else {
                value = "";
            }

            tableModel.addRow(new Object[]{
                rank + 1,  // Display sort rank based on selected metric
                r.getName(),
                value});
        }
    }
}