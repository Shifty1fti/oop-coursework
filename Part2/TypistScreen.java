import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

// class which handles the individual typist screen 
public class TypistScreen extends JPanel {

    private GameSettings settings;
    private Runnable onStart;
    private ArrayList<TypistSettings> settingsList;

    private JComboBox<String> select;
    
    private JTextField nameField;
    private JTextField symbolField;

    private JButton colourButton;
    private JPanel colourPreview;
    private Color selectedColour;

    private JComboBox<String> styleBox;
    private JComboBox<String> keyboardBox;

    private JCheckBox wristBox;
    private JCheckBox energyBox;
    private JCheckBox headphonesBox;

    private JButton saveButton;

    // constructor method which applies all gui interactions 
    public TypistScreen(GameSettings settings, Runnable onStart) {
        this.settings = settings;
        this.onStart = onStart;
        this.settingsList = new ArrayList<>(); 

        setBackground(new Color(0xeae4cf));
        setLayout(new BorderLayout());

        // gridbaglayout to vertically and horizontally position
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(0xeae4cf));

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(new Color(0xeae4cf));

        wrapper.setPreferredSize(new Dimension(800, 500));
        wrapper.setMaximumSize(new Dimension(800, Integer.MAX_VALUE));

        // assigns all rows into wrapper 
        wrapper.add(selectRow());
        wrapper.add(nameRow());
        wrapper.add(symbolRow());
        wrapper.add(colourRow());
        wrapper.add(styleRow());
        wrapper.add(keyboardRow());
        wrapper.add(settingsRow());
        wrapper.add(buttonRow());
        wrapper.add(startRow());

        center.add(wrapper);
        add(center, BorderLayout.CENTER);

        // generate number of typists based on amount passed from gamesettings
        generateTypists();

    }

    // method that adds gui dropdown to change typist
    private JPanel selectRow() {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(0xeae4cf));

        JLabel label = new JLabel("Select Typist:");
        label.setFont(new Font("Monospaced", Font.BOLD, 22));
        label.setForeground(new Color(0xada998));

        select = new JComboBox<>();
        select.addActionListener(e -> loadSelected());
        select.setForeground(new Color(0xada998));

        row.add(label, BorderLayout.WEST);
        row.add(select, BorderLayout.CENTER);

        return row;
    }

    // method that allows the person to add a name in the gui
    private JPanel nameRow() {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(0xeae4cf));

        JLabel label = new JLabel("Typist Name:");
        label.setFont(new Font("Monospaced", Font.BOLD, 22));
        label.setForeground(new Color(0xada998));

        nameField = new JTextField();
        nameField.setForeground(new Color(0xada998));

        row.add(label, BorderLayout.WEST);
        row.add(nameField, BorderLayout.CENTER);

        return row;
    }

    // method that allows the person to add a symbol in the gui
    private JPanel symbolRow() {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(0xeae4cf));

        JLabel label = new JLabel("Symbol:");
        label.setFont(new Font("Monospaced", Font.BOLD, 22));
        label.setForeground(new Color(0xada998));

        symbolField = new JTextField();
        symbolField.setForeground(new Color(0xada998));

        row.add(label, BorderLayout.WEST);
        row.add(symbolField, BorderLayout.CENTER);

        return row;
    }

    // method that allows the person to pick a colour in the gui
    private JPanel colourRow() {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(0xeae4cf));

        JLabel label = new JLabel("Color: ");
        label.setFont(new Font("Monospaced", Font.BOLD, 22));
        label.setForeground(new Color(0xada998));

        colourButton = new JButton("Choose Colour: ");
        colourButton.setFont(new Font("Monospaced", Font.BOLD, 22));
        colourButton.setBackground(new Color(0xeae4cf));
        colourButton.setForeground(new Color(0xada998));

        colourPreview = new JPanel();
        colourPreview.setPreferredSize(new Dimension(50, 50));
        colourPreview.setBackground(new Color(0x7d7c7a));
        
        selectedColour = new Color(0xada998);

        colourButton.addActionListener(e -> {
            Color choice = JColorChooser.showDialog(this, "Choose Typist Colour", selectedColour);

            if (choice != null) {
                selectedColour = choice;
                colourPreview.setBackground(choice);
            }
        });

        JPanel right = new JPanel();
        right.setBackground(new Color(0xeae4cf));
        right.add(colourButton);
        right.add(colourPreview);

        row.add(label, BorderLayout.WEST);
        row.add(right, BorderLayout.CENTER);

        return row;
    }

    // method that allows the person to pick a style in the gui
    private JPanel styleRow() {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(0xeae4cf));

        JLabel label = new JLabel("Style:");
        label.setFont(new Font("Monospaced", Font.BOLD, 22));
        label.setForeground(new Color(0xada998));

        styleBox = new JComboBox<>(new String[]{
            "Touch Typist", "Hunt & Peck", "Phone Thumbs", "Voice-to-Text"
        });
        styleBox.setForeground(new Color(0xada998));

        row.add(label, BorderLayout.WEST);
        row.add(styleBox, BorderLayout.CENTER);

        return row;
    }

    // method that allows the person to pick a keyboard type in the gui
    private JPanel keyboardRow() {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(0xeae4cf));

        JLabel label = new JLabel("Keyboard:");
        label.setFont(new Font("Monospaced", Font.BOLD, 22));
        label.setForeground(new Color(0xada998));

        keyboardBox = new JComboBox<>(new String[]{
            "Mechanical", "Membrane", "Touchscreen", "Stenography"
        });
        keyboardBox.setForeground(new Color(0xada998));

        row.add(label, BorderLayout.WEST);
        row.add(keyboardBox, BorderLayout.CENTER);

        return row;
    }

    // method that contains checkboxes for the settings  in the gui
    private JPanel settingsRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(new Color(0xeae4cf));

        Font font = new Font("Monospaced", Font.BOLD, 22);

        wristBox = new JCheckBox("Wrist Support");
        energyBox = new JCheckBox("Energy Drink");
        headphonesBox = new JCheckBox("Headphones");

        wristBox.setFont(font);
        energyBox.setFont(font);
        headphonesBox.setFont(font);

        wristBox.setForeground(new Color(0xada998));
        energyBox.setForeground(new Color(0xada998));
        headphonesBox.setForeground(new Color(0xada998));

        wristBox.setBackground(new Color(0xeae4cf));
        energyBox.setBackground(new Color(0xeae4cf));
        headphonesBox.setBackground(new Color(0xeae4cf));

        row.add(wristBox);
        row.add(energyBox);
        row.add(headphonesBox);

        return row;
    }

    // method that is used to add a button that allows the user to save a persons information in the gui
    private JPanel buttonRow() {
        JPanel row = new JPanel();
        row.setBackground(new Color(0xeae4cf));

        saveButton = new JButton("Save Typist");
        saveButton.setFont(new Font("Monospaced", Font.BOLD, 22));
        saveButton.setBackground(new Color(0xeae4cf));
        saveButton.setForeground(new Color(0xada998));

        saveButton.addActionListener(e -> saveTypist());

        row.add(saveButton);

        return row;
    }

    private JPanel startRow() {
        JPanel row = new JPanel();
        row.setBackground(new Color(0xeae4cf));

        JButton startButton = new JButton("Start Race");
        startButton.setFont(new Font("Monospaced", Font.BOLD, 22));
        startButton.setBackground(new Color(0xeae4cf));
        startButton.setForeground(new Color(0xada998));

        startButton.addActionListener(e -> {
            saveTypist();
            onStart.run();
        });

        row.add(startButton);
        return row;
    }


    // method which contains logic to apply a certain typists information to the screen  that is previously saved
    private void loadSelected () {
        int i = select.getSelectedIndex();
        
        if (i < 0) {
            return;
        }

        TypistSettings s = settingsList.get(i);

        nameField.setText(s.getTypistName());
        symbolField.setText(String.valueOf(s.getSymbol()));

        selectedColour = s.getColour();
        colourPreview.setBackground(selectedColour);

        styleBox.setSelectedItem(s.getTypingStyle());
        keyboardBox.setSelectedItem(s.getKeyboardType());
        wristBox.setSelected(s.isWristSupport());
        energyBox.setSelected(s.isEnergyDrink());
        headphonesBox.setSelected(s.isHeadphones());
    }

    // method that saves typists information
    private void saveTypist() {
        int i = select.getSelectedIndex();

        if (i < 0) {
            return;
        }

        TypistSettings s = settingsList.get(i);

        String symbolText = symbolField.getText();
        if (symbolText.length() == 1) {
            s.setSymbol(symbolText.charAt(0));
        }

        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            name = "Typist " + (i+1);
        }
        
        s.setTypistName(name);
        s.setColour(selectedColour);
        s.setTypingStyle((String) styleBox.getSelectedItem());
        s.setKeyboardType((String) keyboardBox.getSelectedItem());
        s.setWristSupport(wristBox.isSelected());
        s.setEnergyDrink(energyBox.isSelected());
        s.setHeadphones(headphonesBox.isSelected());

        select.removeItemAt(i);
        select.insertItemAt(name, i);
        select.setSelectedIndex(i);

    }

    // method that creates an arraylist of typists retrieved from settings
    public ArrayList<Typist> buildTypists() {
        ArrayList<Typist> list = new ArrayList<>();

        for (TypistSettings s: settingsList) {
            list.add(s.createTypist());
        }

        return list;
    }

    // method that creates a new typing race and passes the list of typists created
    public TypingRace createRace(int passageLength) {
        ArrayList<Typist> list = buildTypists();
        return new TypingRace(passageLength, settings, list);
    }

    // method that initiailises the base typists for the screen
    public void generateTypists() {
        int count = settings.getAmount();

        settingsList.clear();
        select.removeAllItems();

        for(int i = 0; i < count; i++) {
            String name = "Typist " + (i + 1);

            TypistSettings s = new TypistSettings(
                name,
                "Touch Typist",
                "Mechanical",
                (char) ('A' + i),
                new Color(0x7d7c7a),
                false,
                false,
                false
            );

            settingsList.add(s);
            select.addItem(name);
        }

        if (count > 0) {
            select.setSelectedIndex(0);
            loadSelected();
        }
    }
}