import java.awt.*;

// class which contains the settings for each typist class
public class TypistSettings {
    
    private String name;
    private String typingStyle;
    private String keyboardType;
    private char symbol;
    private Color colour;

    private boolean wristSupport;
    private boolean energyDrink;
    private boolean headphones;

    // constructor method that creates the TypistSettings object
    public TypistSettings (String name, String typingStyle, String keyboardType, char symbol, Color colour, boolean wristSupport, boolean energyDrink, boolean headphones) {
        this.name = name;
        this.typingStyle = typingStyle;
        this.keyboardType = keyboardType;
        this.symbol = symbol;
        this.colour = colour;
        this.wristSupport = wristSupport;
        this.energyDrink = energyDrink;
        this.headphones = headphones;
    }

    // method that adjusts the accuracy and burnout value then return the typist
    public Typist createTypist() {
        double accuracy = 0.8;
        double burnoutAmount = 1.0;
        double burnoutRisk = 1.0;

        if (typingStyle.equals("Touch Typist")) {
            accuracy += 0.05;
        }
        else if (typingStyle.equals("Hunt & Peck")) {
            accuracy -= 0.1;
            burnoutRisk += 0.2;
        }
        else if (typingStyle.equals("Phone Thumbs")) {
            accuracy -= 0.05;
            burnoutAmount += 0.3;
        }
        else if (typingStyle.equals("Voice-to-Text")) {
            accuracy += 0.15;
        }

        if (keyboardType.equals("Mechanical")) {
            accuracy += 0.05;
        }

        else if (keyboardType.equals("Membrane")) {
            accuracy -= 0.02;
            burnoutAmount -= 0.15;
        }

        else if (keyboardType.equals("Touchscreen")) {
            accuracy -= 0.08;
            burnoutRisk += 0.2;
        }

        else if (keyboardType.equals("Stenography")) {
            accuracy += 0.1;
            burnoutAmount -= 0.1;
        }

        if (energyDrink) {
            accuracy += 0.05;
            burnoutAmount += 0.2;
        }

        if (headphones) {
            accuracy += 0.03;
            burnoutRisk -= 0.3;
        }

        if (wristSupport) {
            burnoutAmount *= 0.7;
        }

        accuracy = Math.max(0.1, Math.min(1.0, accuracy));
        burnoutRisk = Math.max(0.2, Math.min(2.0, burnoutRisk));
        burnoutAmount = Math.max(0.5, Math.min(2.0, burnoutAmount));

        Typist typist = new Typist(symbol, this.name, accuracy);
        typist.setBurnoutModifier(burnoutAmount);
        return typist;

    }

    // getter method that returns the typist name
    public String getTypistName() {
        return this.name;
    }

    // getter method that returns the typing style
    public String getTypingStyle() {
        return typingStyle;
    }

    // getter method that returns the keyboard type
    public String getKeyboardType() {
        return keyboardType;
    }

    // getter method that returns the symbol
    public char getSymbol() {
        return symbol;
    }

    // getter method that returns the colour
    public Color getColour() {
        return colour;
    }

    // getter method that returns the state of wrist support
    public boolean isWristSupport() {
        return wristSupport;
    }

    // getter method that returns the state of energy drink
    public boolean isEnergyDrink() {
        return energyDrink;
    }

    // getter method that returns the state of headphones
    public boolean isHeadphones() {
        return headphones;
    }

    // setter method that assigns name
    public void setTypistName(String name) {
        this.name = name;
    }

    // setter method that assigns typing style
    public void setTypingStyle(String typingStyle) {
        this.typingStyle = typingStyle;
    }

    // setter method that assigns keyboard type
    public void setKeyboardType(String keyboardType) {
        this.keyboardType = keyboardType;
    }

    // setter method that assigns symbol
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    // setter method that assigns colour
    public void setColour(Color colour) {
        this.colour = colour;
    }

    // setter method that assigns wrist support
    public void setWristSupport(boolean wristSupport) {
        this.wristSupport = wristSupport;
    }

    // setter method that assigns energy drink
    public void setEnergyDrink(boolean energyDrink) {
        this.energyDrink = energyDrink;
    }

    // setter method that assigns headphones
    public void setHeadphones(boolean headphones) {
        this.headphones = headphones;
    }
}