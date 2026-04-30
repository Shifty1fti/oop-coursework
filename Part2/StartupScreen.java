import java.awt.*;
import javax.swing.*;

public class StartupScreen extends JPanel {

    public StartupScreen(Runnable onPlay, Runnable onLeaderboard, Runnable onQuit) {
        
        setBackground(new Color(0xeae4cf));
        setLayout(new BorderLayout());

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(0xeae4cf));

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(new Color(0xeae4cf));

        wrapper.setPreferredSize(new Dimension(800, 500));
        wrapper.setMaximumSize(new Dimension(800, Integer.MAX_VALUE));

        wrapper.add(title());
        wrapper.add(playButton(onPlay));
        wrapper.add(leaderboardButton(onLeaderboard));
        wrapper.add(quitButton(onQuit));

        center.add(wrapper);
        add(center, BorderLayout.CENTER);
    }

    private JPanel title() {
        JPanel row = new JPanel();
        row.setBackground(new Color(0xeae4cf));

        JLabel label = new JLabel("Typeracer");
        label.setFont(new Font("Monospaced", Font.BOLD, 48));
        label.setForeground(new Color(0xada998));

        row.add(label);
        return row;
    }

    private JPanel playButton(Runnable onPlay) {
        JPanel row = new JPanel();
        row.setBackground(new Color(0xeae4cf));

        JButton button = new JButton("Play Race");
        styleButton(button);

        button.addActionListener(e -> onPlay.run());

        row.add(button);
        return row;
    }

    private JPanel leaderboardButton(Runnable onLeaderboard) {
        JPanel row = new JPanel();
        row.setBackground(new Color(0xeae4cf));

        JButton button = new JButton("Leaderboard");
        styleButton(button);

        button.addActionListener(e -> onLeaderboard.run());

        row.add(button);
        return row;
    }

    private JPanel quitButton(Runnable onQuit) {
        JPanel row = new JPanel();
        row.setBackground(new Color(0xeae4cf));

        JButton button = new JButton("Quit");
        styleButton(button);

        button.addActionListener(e -> onQuit.run());

        row.add(button);
        return row;
    }



    private void styleButton(JButton button) {
        button.setFont(new Font("Monospaced", Font.BOLD, 22));
        button.setBackground(new Color(0xeae4cf));
        button.setForeground(new Color(0xada998));
        button.setFocusPainted(false);
    }
}