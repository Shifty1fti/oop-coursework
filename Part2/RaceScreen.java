import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

public class RaceScreen extends JPanel {

    private TypingRace race;

    private String passage = "the quick brown fox jumped over the lazy fence";

    private JTextPane textPane;
    private StyledDocument doc;

    private Style typingStyle;
    private Style finishedStyle;

    private JLabel name;
    private JLabel accuracy;
    private JLabel progress;
    private JLabel statusLabel;

    public RaceScreen() {
        System.out.println("RaceScreen loaded");

        setBackground(new Color(0xeae4cf));
        setLayout(new BorderLayout());

        race = new TypingRace(passage.length());

        // TEXT AREA
        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(new Font("Monospaced", Font.BOLD, 22));
        textPane.setBackground(new Color(0xeae4cf));

        // CARET which is used for cursor system
        textPane.setCaretColor(new Color(0xada998));
        textPane.setCaretPosition(0);

        add(infoPanel(race.getTypists().get(0)), BorderLayout.NORTH);

        doc = textPane.getStyledDocument();

        typingStyle = textPane.addStyle("Typing", null);
        StyleConstants.setForeground(typingStyle, new Color(0xada998));

        finishedStyle = textPane.addStyle("Finished", null);
        StyleConstants.setForeground(finishedStyle, Color.WHITE);

        add(textPane, BorderLayout.CENTER);

        startRace();
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

    private JPanel infoPanel(Typist t) {
        JPanel info = new JPanel();
        info.setLayout(new GridLayout(0, 1));
        info.setBackground(new Color(0xeae4cf));

        name = new JLabel(t.getName());
        accuracy = new JLabel("Accuracy: " + t.getAccuracy());
        progress = new JLabel("Progress: " + t.getProgress() + "/" + passage.length());

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

        statusLabel = new JLabel(status);

        Font font = new Font("Monospaced", Font.BOLD, 16);

        name.setFont(font);
        accuracy.setFont(font);
        progress.setFont(font);
        statusLabel.setFont(font);

        info.add(name);
        info.add(accuracy);
        info.add(progress);
        info.add(statusLabel);

        return info;
    }

    private void updateInfo() {
        Typist t = race.getTypists().get(0);
        
        progress.setText("Progress: " + t.getProgress() + "/" + passage.length());

        String statusInfo;

        if (t.getProgress() >= passage.length()) {
            statusInfo = "Finished";
        }
        else if (t.isBurntOut()) {
            statusInfo = "Burned: " + t.getBurnoutTurnsRemaining() + " turns remaining";
        }
        else {
            statusInfo = "Typing";
        }

        statusLabel.setText(statusInfo);
    }

    // logic used to update the text
    private void updateText() {
        try {
            doc.remove(0, doc.getLength());

            Typist t = race.getTypists().get(0);
            int progress = Math.min(t.getProgress(), passage.length());

            // typed text
            doc.insertString(0,
                    passage.substring(0, progress),
                    typingStyle);

            // remaining text
            doc.insertString(doc.getLength(),
                    passage.substring(progress),
                    null);

            // finished state
            if (t.getProgress() >= passage.length()) {
                doc.remove(0, doc.getLength());
                doc.insertString(0, passage, finishedStyle);
            }

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    // updates the cursor depending on the current position of the typist
    private void updateCursor() {
        Typist t = race.getTypists().get(0);

        // THIS is the key line
        textPane.getCaret().setVisible(true);
        textPane.setCaretPosition(t.getProgress());
        
        if (t.getProgress() >= passage.length()) {
            textPane.getCaret().setVisible(false);
        }
    }
}