import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;


public class RaceScreen extends JPanel {

    private TypingRace race;

    private String passage = "the quick brown fox jumped over the lazy fence";

    private JTextPane[] textPanes;
    private StyledDocument[] docs;

    private JLabel[] nameLabels;
    private JLabel[] accuracyLabels;
    private JLabel[] progressLabels;
    private JLabel[] statusLabels;

    private Style[] originalStyle;
    private Style[] completeStyle;

    public RaceScreen() {
        System.out.println("RaceScreen loaded");

        race = new TypingRace(passage.length());
        int numTypists = race.getTypists().size();


        setBackground(new Color(0xeae4cf));
        setLayout(new BorderLayout());

        nameLabels = new JLabel[numTypists];
        accuracyLabels = new JLabel[numTypists];
        progressLabels = new JLabel[numTypists];
        statusLabels = new JLabel[numTypists];

        textPanes = new JTextPane[numTypists];
        docs = new StyledDocument[numTypists];
        originalStyle = new Style[numTypists];
        completeStyle = new Style[numTypists];
        
        JPanel wrapper = new JPanel(new GridLayout(numTypists, 1));
        wrapper.setBackground(new Color(0xeae4cf));
        wrapper.setBorder(BorderFactory.createEmptyBorder(100, 150, 0, 0));
        
        for (int i = 0; i < numTypists; i++) {
            wrapper.add(createRow(race.getTypists().get(i), i));
        }

        add(wrapper, BorderLayout.CENTER);

        updateText();
        updateCursor();
        updateInfo();
        startRace();
    }

    private JPanel createRow(Typist t, int index) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(0xeae4cf));

        JPanel infoWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        infoWrapper.setBackground(new Color(0xeae4cf));

        JPanel info = infoPanel(t, index);
        infoWrapper.add(info);

        row.add(infoWrapper, BorderLayout.WEST);

        textPanes[index] = new JTextPane();
        textPanes[index].setEditable(false);
        textPanes[index].setFont(new Font("Monospaced", Font.BOLD, 30));
        textPanes[index].setBackground(new Color(0xeae4cf));
        textPanes[index].setCaretColor(new Color(0xada998));
        docs[index] = textPanes[index].getStyledDocument();
        
        originalStyle[index] = textPanes[index].addStyle("Original", null);
        StyleConstants.setForeground(originalStyle[index], new Color(0xada998));
        
        completeStyle[index] = textPanes[index].addStyle("Complete", null);
        StyleConstants.setForeground(completeStyle[index], new Color(0x7d7c7a));

        row.add(textPanes[index], BorderLayout.CENTER);

        return row;
    }

    // logic which starts the race and updates text and cursor every 0.2 seconds for the typist
    private void startRace() {
        Timer timer = new Timer(200, e -> {
            race.advance();
            updateText();
            updateCursor();
            updateInfo();

            if (race.isFinished()) {
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }

    private JPanel infoPanel(Typist t, int index) {
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(new Color(0xeae4cf));
        info.setPreferredSize(new Dimension(250, 120));
        info.setMaximumSize(new Dimension(250, 120));
        info.setMinimumSize(new Dimension(250, 120));

        nameLabels[index] = new JLabel(t.getName() + ": " + t.getSymbol());
        accuracyLabels[index] = new JLabel("Accuracy: " + t.getAccuracy());
        progressLabels[index] = new JLabel("Progress: " + t.getProgress() + "/" + passage.length());

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

    private void updateInfo() {
        for (int i = 0; i < race.getTypists().size(); i++) {
            Typist t = race.getTypists().get(i);
        
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

    // logic used to update the text
    private void updateText() {
        for (int i = 0; i < race.getTypists().size(); i++) {
            Typist t = race.getTypists().get(i);
            try {
                docs[i].remove(0, docs[i].getLength());


                int progress = Math.min(t.getProgress(), passage.length());

                // typed text
                docs[i].insertString(0,
                    passage.substring(0, progress),
                    completeStyle[i]);

                // remaining text
                docs[i].insertString(docs[i].getLength(),
                        passage.substring(progress),
                        originalStyle[i]);

            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    // updates the cursor depending on the current position of the typist
    private void updateCursor() {
        for (int i = 0; i < race.getTypists().size(); i++) {
            Typist t = race.getTypists().get(i);

            // THIS is the key line
            textPanes[i].getCaret().setVisible(true);
            textPanes[i].setCaretPosition(t.getProgress());
            
            if (t.getProgress() >= passage.length()) {
                textPanes[i].getCaret().setVisible(false);
            }
        }
    }
}