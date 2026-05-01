import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.text.*;

// class that creates the game screen where it is run
public class RaceScreen extends JPanel {

    private ArrayList<Typist> typists;
    private TypingRace race;
    private GameSettings settings;
    private Runnable onFinish;
    private String passage;
    private ArrayList<Result> results = new ArrayList<>();

    private JTextPane[] textPanes;
    private StyledDocument[] docs;

    private JLabel[] nameLabels;
    private JLabel[] accuracyLabels;
    private JLabel[] progressLabels;
    private JLabel[] statusLabels;

    private Style[] originalStyle;
    private Style[] completeStyle;
    
    // counts total amount of turns
    private int totalTurns = 0;

    private HashMap<String, Integer> finishTurns = new HashMap<>();

    
    // constructor method that displays all the pages 
    public RaceScreen(GameSettings settings,ArrayList<Typist> typists, Runnable onFinish) {

        this.settings = settings;
        this.passage = settings.getPassage();
        this.onFinish = onFinish;
        this.typists = typists;
        

        // creates a new race and retrieves amount of typists
        race = new TypingRace(passage.length(), settings, typists);
        int numTypists = typists.size();


        // adds information label to the left side of the row
        setBackground(new Color(0xeae4cf));
        setLayout(new BorderLayout());

        nameLabels = new JLabel[numTypists];
        accuracyLabels = new JLabel[numTypists];
        progressLabels = new JLabel[numTypists];
        statusLabels = new JLabel[numTypists];

        // adds text label to right side of the row
        textPanes = new JTextPane[numTypists];
        docs = new StyledDocument[numTypists];
        originalStyle = new Style[numTypists];
        completeStyle = new Style[numTypists];
        
        // wraps labels into a grid
        JPanel wrapper = new JPanel(new GridLayout(numTypists, 1));
        wrapper.setBackground(new Color(0xeae4cf));
        wrapper.setBorder(BorderFactory.createEmptyBorder(100, 150, 0, 0));
        
        for (int i = 0; i < numTypists; i++) {
            wrapper.add(createRow(typists.get(i), i));
        }

        add(wrapper, BorderLayout.CENTER);

        // initialises the game
        updateText();
        updateCursor();
        updateInfo();
        startRace();
    }

    // method that returns the game text row for typists 
    private JPanel createRow(Typist t, int index) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(0xeae4cf));

        JPanel infoWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        infoWrapper.setBackground(new Color(0xeae4cf));

        JPanel info = infoPanel(t, index);
        infoWrapper.add(info);

        row.add(infoWrapper, BorderLayout.WEST);

        // creates a text pane to store the text
        textPanes[index] = new JTextPane();
        textPanes[index].setEditable(false);
        textPanes[index].setFont(new Font("Monospaced", Font.BOLD, 22));
        textPanes[index].setBackground(new Color(0xeae4cf));
        textPanes[index].setCaretColor(t.getColour());
        docs[index] = textPanes[index].getStyledDocument();
        
        // text style to differentiate between incomplete and complete text
        originalStyle[index] = textPanes[index].addStyle("Original", null);
        StyleConstants.setForeground(originalStyle[index], new Color(0xada998));
        
        completeStyle[index] = textPanes[index].addStyle("Complete", null);
        StyleConstants.setForeground(completeStyle[index], t.getColour());

        row.add(textPanes[index], BorderLayout.CENTER);

        return row;
    }

    // logic which starts the race and updates text and cursor every 0.2 seconds for the typist
    private void startRace() {
        Timer timer = new Timer(200, e -> {

            totalTurns++;
            race.incrementTurn();

            for (Typist t: typists) {
                if (t.getProgress() < passage.length()) {
                    race.advanceTypist(t);
                }
            }

            for (Typist t: typists) {
                if (t.getProgress() >= passage.length() && !finishTurns.containsKey(t.getName())) {
                    finishTurns.put(t.getName(), totalTurns);
                }
            }

            updateText();
            updateCursor();
            updateInfo();

            // stops race and runs back to setting screen
            if (allFinished()) {
                ((Timer) e.getSource()).stop();

                calculateResults();

                String winner = results.get(0).getName();

                // popup which indicates the winner
                JOptionPane.showMessageDialog(
                    this,
                    "Winner: " + winner,
                    "Race Finished",
                    JOptionPane.INFORMATION_MESSAGE
                );

                onFinish.run();
            }
        });

        timer.start();
    }

    // method which creates the info panel section on the left side of the row
    private JPanel infoPanel(Typist t, int index) {
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(new Color(0xeae4cf));
        info.setPreferredSize(new Dimension(250, 120));
        info.setMaximumSize(new Dimension(250, 120));
        info.setMinimumSize(new Dimension(250, 120));

        // displays information
        nameLabels[index] = new JLabel(t.getName() + ": " + t.getSymbol());
        accuracyLabels[index] = new JLabel("Accuracy: " + String.format("%.2f",t.getAccuracy()));
        progressLabels[index] = new JLabel("Progress: " + t.getProgress() + "/" + passage.length());

        String status;

        // logic which changes state of user in the round
        if (t.getProgress() >= passage.length()) {
            status = "Finished";
        }
        else if (t.isBurntOut()) {
            status = "Burned: " + t.getBurnoutTurnsRemaining() + " turns remaining";
        }
        else {
            status = "Typing";
        }

        statusLabels[index] = new JLabel(status);

        Font font = new Font("Monospaced", Font.BOLD, 22);

        nameLabels[index].setFont(font);
        accuracyLabels[index].setFont(font);
        progressLabels[index].setFont(font);
        statusLabels[index].setFont(font);

        nameLabels[index].setForeground(new Color(0xada998));
        accuracyLabels[index].setForeground(new Color(0xada998));
        progressLabels[index].setForeground(new Color(0xada998));
        statusLabels[index].setForeground(new Color(0xada998));

        info.add(nameLabels[index]);
        info.add(accuracyLabels[index]);
        info.add(progressLabels[index]);
        info.add(statusLabels[index]);

        return info;
    }

    // updates progress and users state 
    private void updateInfo() {
        for (int i = 0; i < typists.size(); i++) {
            Typist t = typists.get(i);
        
            progressLabels[i].setText("Progress: " + t.getProgress() + "/" + passage.length());

            String status;

            if (t.getProgress() >= passage.length()) {
                status = "Finished";
            }
            else if (t.isBurntOut()) {
                status = "Burned: " + t.getBurnoutTurnsRemaining() + " turns remaining";
            }
            else {
                status = "Typing";
            }

            statusLabels[i].setText(status);
        }
    }

    // logic used to update the text of the round
    private void updateText() {
        for (int i = 0; i < typists.size(); i++) {
            Typist t = typists.get(i);
            try {
                docs[i].remove(0, docs[i].getLength());

                int progress = Math.min(t.getProgress(), passage.length());

                docs[i].insertString(0,
                    passage.substring(0, progress),
                    completeStyle[i]);

                // ADD: only show remaining text if not finished
                if (progress < passage.length()) {
                    docs[i].insertString(docs[i].getLength(),
                        passage.substring(progress),
                        originalStyle[i]);
                }

            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    // updates the cursor depending on the current position of the typist
    private void updateCursor() {
        for (int i = 0; i < typists.size(); i++) {
            Typist t = typists.get(i);

            if (t.getProgress() >= passage.length()) {
                textPanes[i].getCaret().setVisible(false);
                continue;
            }
            // THIS is the key line
            textPanes[i].getCaret().setVisible(true);
            textPanes[i].setCaretPosition(t.getProgress());
        }
    }
    
    // method that calculates final accuracy 
    private void calculateResults() {
        
        double timeSeconds = totalTurns * 0.2;
        double minutes = timeSeconds / 60.0;

        results.clear();

        // Create results for each typist
        for (Typist t : typists) {
            double wpm = (t.getProgress() / 5.0) / minutes;
            t.updateBestWPM(wpm);

            // create result object
            Result r = new Result(
                t.getName(),
                0,
                wpm,
                t.getAccuracy(),
                t.getAccuracy() - t.getStartingAccuracy(),
                t.getBurnoutCount(),
                timeSeconds
            );

            // link result with typist
            r.setTypist(t);
            results.add(r);
            t.getHistory().add(r);
        }

        // Sort by progress
        results.sort((a, b) -> 
            b.getTypist().getProgress() - a.getTypist().getProgress()
        );

        // Assign positions + adjust accuracy
        for (int i = 0; i < results.size(); i++) {
            Result r = results.get(i);
            Typist t = r.getTypist();

            r.setPosition(i + 1);

            double adjustment = 0;

            if (i == 0) {
                adjustment += 0.05;
            }
            else if (i == 1) {
                adjustment += 0.02;
            }
            else if (i == 2) {
                adjustment += 0.01;
            }

            adjustment -= t.getBurnoutCount() * 0.02;

            double newAccuracy = t.getAccuracy() + adjustment;
            newAccuracy = Math.max(0.0, Math.min(1.0, newAccuracy));

            t.setAccuracy(newAccuracy);
            t.setFinalAccuracy(newAccuracy);

            r.setAccuracyChange(newAccuracy - r.getStartingAccuracy());
        }
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    private boolean allFinished() {
        for (Typist t: typists) {
            if (t.getProgress() < passage.length()) {
                return false;
            }
        }
        return true;
    }

}