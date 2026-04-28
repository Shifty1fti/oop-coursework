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

    public RaceScreen() {
        System.out.println("RaceScreen loaded");
        setBackground(new Color(0xeae4cf));
        setLayout(new BorderLayout());

        race = new TypingRace(passage.length());

        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(new Font("Monospaced", Font.BOLD, 22));
        textPane.setBackground(new Color(0xeae4cf));

        doc = textPane.getStyledDocument();

        typingStyle = textPane.addStyle("Typing", null);
        StyleConstants.setForeground(typingStyle, new Color(0xada998));

        finishedStyle = textPane.addStyle("Finished", null);
        StyleConstants.setForeground(finishedStyle, Color.WHITE);

        add(textPane, BorderLayout.CENTER);

        startRace();
    }

    private void startRace() {
        Timer timer = new Timer(200, e -> {
            race.advance();
            updateText();

            if (race.isFinished()) {
                ((Timer) e.getSource()).stop();
            }

        });
        timer.start();
    }

    private void updateText() {
    try {
        doc.remove(0, doc.getLength());

        Typist t = race.getTypists().get(0);
        int progress = Math.min(t.getProgress(), passage.length());

        // typed text (coloured)
        doc.insertString(0,
                passage.substring(0, progress),
                typingStyle);

        // remaining text (normal / uncoloured look)
        doc.insertString(doc.getLength(),
                passage.substring(progress),
                null);

        // finished override
        if (t.getProgress() >= passage.length()) {
            doc.remove(0, doc.getLength());
            doc.insertString(0, passage, finishedStyle);
        }
    } 

    catch (BadLocationException e) {
        e.printStackTrace();
        }
    }
}