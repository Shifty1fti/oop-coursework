import java.awt.*;
import javax.swing.*;


// class that deals with layout and basic logic of settings page
public class SettingScreen extends JPanel {

    private GameSettings settings;

    private JSlider playerSlider;
    private JComboBox<String> passageCombo;
    private JTextArea passageArea;
    private JCheckBox autocorrectBox;
    private JCheckBox caffeineBox;
    private JCheckBox nightBox;
    

    // constructor method
    public SettingScreen(GameSettings settings, Runnable onStart) {
        this.settings = settings;    

        setBackground(new Color(0xeae4cf));
        setLayout(new BorderLayout());
        
        // grid bag layout that assigns to middle in vertical and horizontal position
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(0xeae4cf));

        // wrapper used to cap the size of each row
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(new Color(0xeae4cf));

        wrapper.setPreferredSize(new Dimension(800, 500));
        wrapper.setMaximumSize(new Dimension(800, Integer.MAX_VALUE));

        wrapper.add(playerRow());
        wrapper.add(passageRow());
        wrapper.add(passageText());
        wrapper.add(settingsRow());

        // button created which submits the information using action listener
        JButton submit = new JButton("Start Race");
        submit.setFont(new Font("Monospaced", Font.BOLD, 22));
        submit.setBackground(new Color(0xeae4cf));
        submit.setForeground(new Color(0xada998));

        submit.addActionListener(e -> {
            saveSetting();

            if (passageCombo.getSelectedItem().equals("Custom")) {
                settings.setPassage(passageArea.getText());
            }

            onStart.run();
        });

        wrapper.add(submit);

        center.add(wrapper);
        add(center, BorderLayout.CENTER);

        passageCombo.addActionListener(e -> updatePassage());
        updatePassage(); // initialise display
    }

    // method that uses setter methods to assign values to settings object
    public void saveSetting() {
        settings.setAmount(playerSlider.getValue());
        settings.setType((String) passageCombo.getSelectedItem());

        if (passageCombo.getSelectedItem().equals("Custom")) {
            settings.setCustom(passageArea.getText());
        }
        settings.setAutocorrect(autocorrectBox.isSelected());
        settings.setCaffeine(caffeineBox.isSelected());
        settings.setNight(nightBox.isSelected());
    }

    // method that creates the player row and feature
    private JPanel playerRow() {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(0xeae4cf));

        JLabel label = new JLabel("Players (2-6): ");
        label.setFont(new Font("Monospaced", Font.BOLD, 22));
        label.setForeground(new Color(0xada998));

        playerSlider = new JSlider(2, 6, settings.getAmount());
        playerSlider.setMajorTickSpacing(1);
        playerSlider.setPaintLabels(true);
        playerSlider.setBackground(new Color(0xeae4cf));
        playerSlider.setForeground(new Color(0xada998));
        row.add(label, BorderLayout.WEST);
        row.add(playerSlider, BorderLayout.CENTER);

        return row;
    }

    // method that creates the text row and option to choose between short medium long or custom
    private JPanel passageRow() {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(0xeae4cf));

        JLabel label = new JLabel("Passage Type: ");
        label.setFont(new Font("Monospaced", Font.BOLD, 22));
        label.setForeground(new Color(0xada998));

        passageCombo = new JComboBox<>(new String[]{"Short", "Medium", "Long", "Custom"});
        passageCombo.setSelectedItem(settings.getType());
        passageCombo.setForeground(new Color(0xada998));

        row.add(label, BorderLayout.WEST);
        row.add(passageCombo, BorderLayout.CENTER);

        return row;
    }

    // method that displays the text that will be used for the game
    private JPanel passageText() {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(0xeae4cf));

        JLabel label = new JLabel("Passage Text");
        label.setFont(new Font("Monospaced", Font.BOLD, 22));
        label.setForeground(new Color(0xada998));

        passageArea = new JTextArea(4, 40);
        passageArea.setLineWrap(true);
        passageArea.setWrapStyleWord(true);
        passageArea.setFont(new Font("Monospaced", Font.BOLD, 22));
        passageArea.setForeground(new Color(0xada998));

        passageArea.setEditable(false);

        JScrollPane scroll = new JScrollPane(passageArea);

        row.add(label, BorderLayout.NORTH);
        row.add(scroll, BorderLayout.CENTER);

        return row;
    }

    // method that contains options for autocorrect caffeine mode and night shift
    private JPanel settingsRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(new Color(0xeae4cf));

        autocorrectBox = new JCheckBox("Autocorrect", settings.getAutocorrect());
        caffeineBox = new JCheckBox("Caffeine Mode", settings.getCaffeine());
        nightBox = new JCheckBox("Night Shift", settings.getNight());

        Font font = new Font("Monospaced", Font.BOLD, 22);


        autocorrectBox.setFont(font);
        caffeineBox.setFont(font);
        nightBox.setFont(font);
        autocorrectBox.setForeground(new Color(0xada998));
        caffeineBox.setForeground(new Color(0xada998));
        nightBox.setForeground(new Color(0xada998));

        autocorrectBox.setBackground(new Color(0xeae4cf));
        caffeineBox.setBackground(new Color(0xeae4cf));
        nightBox.setBackground(new Color(0xeae4cf));

        row.add(autocorrectBox);
        row.add(caffeineBox);
        row.add(nightBox);

        return row;
    }

    // method that updates the passage and assigns to settings
    private void updatePassage() {
        String currentType = settings.getType();
        String newType = (String) passageCombo.getSelectedItem();

        // Save custom text before leaving Custom mode
        if (currentType.equals("Custom") && !newType.equals("Custom")) {
            settings.setCustom(passageArea.getText());
        }

        settings.setType(newType);

        if (newType.equals("Custom")) {
            passageArea.setEditable(true);
            passageArea.setText(settings.getCustom());
        } else {
            passageArea.setEditable(false);
            String preview = settings.generate();
            passageArea.setText(preview);
            settings.setPassage(preview);
        }
    }
}