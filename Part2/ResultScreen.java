import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// class that creates the result screen after a round has ended
public class ResultScreen extends JPanel{
    
    private Runnable onPlay;
    private ArrayList<Result> results;
    private RaceHistory raceHistory;

    private JComboBox<String> metricSelect;
    private JTable table;
    private DefaultTableModel tableModel;

    public ResultScreen(ArrayList<Result> results,RaceHistory raceHistory, Runnable  onPlay) {
        this.results = results;
        this.onPlay = onPlay;
        this.raceHistory = raceHistory;

        setBackground(new Color(0xeae4cf));
        setLayout(new BorderLayout());

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(0xeae4cf));

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(new Color(0xeae4cf));
        wrapper.setPreferredSize(new Dimension(800, 500));

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Monospaced", Font.BOLD, 16));

        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(new Color(0xeae4cf));
        resultsPanel.add(selectRow());
        resultsPanel.add(tableRow());

        tabs.add("Race Results", resultsPanel);
        tabs.add("Race History", buildHistoryPanel());
        tabs.add("Leaderboard", buildLeaderboardPanel());

        wrapper.add(tabs);
        wrapper.add(buttonRow());

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

    private JPanel buttonRow() {
        JPanel row = new JPanel();
        row.setBackground(new Color(0xeae4cf));

        JButton backButton = new JButton("Return to Menu");

        backButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        backButton.setBackground(new Color(0xada998));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);

        backButton.addActionListener(e -> {
            onPlay.run();
        });

        row.add(backButton);

        return row;
    }

    private JPanel buildHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xeae4cf));

        String[] names = results.stream().map(Result::getName).toArray(String[]::new);
        JComboBox<String> typistSelect = new JComboBox<>(names);
        typistSelect.setFont(new Font("Monospaced", Font.PLAIN, 16));

        JLabel label = new JLabel("View history for: ");
        label.setFont(new Font("Monospaced", Font.BOLD, 18));
        label.setForeground(new Color(0xada998));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(new Color(0xeae4cf));
        top.add(label);
        top.add(typistSelect);

        DefaultTableModel histModel = new DefaultTableModel(
            new String[]{"Race #", "Position", "WPM", "Accuracy", "Burnouts"}, 0
        );
        JTable histTable = new JTable(histModel);
        histTable.setFont(new Font("Monospaced", Font.PLAIN, 16));
        histTable.setRowHeight(28);

        Runnable refresh = () -> {
            histModel.setRowCount(0);
            String name = (String) typistSelect.getSelectedItem();
            ArrayList<Result> typistHistory = raceHistory.getHistory(name);
            for (int i = 0; i < typistHistory.size(); i++) {
                Result r = typistHistory.get(i);
                histModel.addRow(new Object[]{
                    i + 1,
                    r.getPosition(),
                    String.format("%.2f", r.getWPM()),
                    String.format("%.1f%%", r.getAccuracy() * 100),
                    r.getBurnoutCount()
                });
            }
        };

        typistSelect.addActionListener(e -> refresh.run());
        refresh.run();

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(histTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildLeaderboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xeae4cf));

        DefaultTableModel lbModel = new DefaultTableModel(
            new String[]{"Rank", "Name", "Points", "Title"}, 0
        );
        JTable lbTable = new JTable(lbModel);
        lbTable.setFont(new Font("Monospaced", Font.PLAIN, 16));
        lbTable.setRowHeight(28);

        Leaderboard lb = raceHistory.getLeaderboard();
        ArrayList<String> ranked = lb.getRanking();
        for (int i = 0; i < ranked.size(); i++) {
            String name = ranked.get(i);
            lbModel.addRow(new Object[]{
                i + 1,
                name,
                lb.getPoints(name),
                lb.getTitle(name)
            });
        }

        panel.add(new JScrollPane(lbTable), BorderLayout.CENTER);
        return panel;
    }

    // method that updates the table based off of user option 
    private void updateTable(String metric) {

        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        tableModel.addColumn("Position");
        tableModel.addColumn("Name");
        tableModel.addColumn("Best WPM");
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
                value = String.format("%.0f", r.getWPM());
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

            double personalBest = raceHistory.getPersonalBestWPM(r.getName());
            tableModel.addRow(new Object[]{
                rank + 1,  // Display sort rank based on selected metric
                r.getName(),
                personalBest,
                value});
        }
    }
}