import java.awt.*;


public class TypistSettings {
    private String typingStyle;
    private String keyboardType;
    private char symbol;
    private Color colour;

    private boolean wristSupport;
    private boolean energyDrink;
    private boolean headphones;

    public TypistSettings (String typingStyle, String keyboardType, char symbol, Color colour, boolean wristSupport, boolean energyDrink, boolean headphones) {
        this.typingStyle = typingStyle;
        this.keyboardType = keyboardType;
        this.symbol = symbol;
        this.colour = colour;
        this.wristSupport = wristSupport;
        this.energyDrink = energyDrink;
        this.headphones = headphones;
    }

    

}