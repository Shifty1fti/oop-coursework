import java.awt.*;

/**
 * Typist class which is used as an object with all the attributes that need to be tracked being the symbol, name, accuracy, progress, the state of burnout, and how long they have been burned.
 * There are getter and setter methods which are used to encapsulate the data and privatise how it is handled
 *
 * @author (Iftikhar Khan)
 * @version (26/04/26)
 */
public class Typist
{

    // Fields of class Typist
    // Hint: you will need six fields. Think carefully about their types.
    // One of them tracks how far along the passage the typist has reached.
    // Another tracks whether the typist is currently burnt out.
    // A third tracks HOW MANY turns of burnout remain (not just whether they are burnt out).
    // The remaining three should be fairly obvious.

    private char typistSymbol;
    private String typistName;
    private double typistAccuracy;
    private int currentProgress;
    private boolean isBurned;
    private int burnTurns;
    private boolean ifMistype; // attribute that checks on each turn if the typist has entered an incorrect input
    private double burnoutModifier;
    private Color colour; // assigning colour to typist for the text




    // Constructor of class Typist
    /**
     * Constructor for objects of class Typist.
     * Creates a new typist with a given symbol, name, and accuracy rating.
     *
     * @param typistSymbol  a single Unicode character representing this typist (e.g. '①', '②', '③')
     * @param typistName    the name of the typist (e.g. "TURBOFINGERS")
     * @param typistAccuracy the typist's accuracy rating, between 0.0 and 1.0
     */
    public Typist(char typistSymbol, String typistName, double typistAccuracy)
    {
        this.typistSymbol = typistSymbol;
        this.typistName = typistName;
        this.typistAccuracy = typistAccuracy;
        this.currentProgress = 0;
        this.isBurned = false;
        this.burnTurns = 0;
        this.ifMistype = false;
        this.burnoutModifier = 1.0;
    }


    // Methods of class Typist

    /**
     * Sets this typist into a burnout state for a given number of turns.
     * A burnt-out typist cannot type until their burnout has worn off.
     *
     * @param turns the number of turns the burnout will last
     */
    public void burnOut(int turns)
    {
        this.isBurned = true;
	    this.burnTurns = Math.max(1, (int)(turns * this.burnoutModifier));
    }

    /**
     * Reduces the remaining burnout counter by one turn.
     * When the counter reaches zero, the typist recovers automatically.
     * Has no effect if the typist is not currently burnt out.
     */
    public void recoverFromBurnout()
    {
        if (this.isBurned) {
            this.burnTurns--;
            if (burnTurns == 0) {
                this.isBurned = false;
            }
        }
    }

    /**
     * Sets the burnout modifier for this typist.
     * Modifies how long burnout effects last.
     *
     * @param modifier the multiplier for burnout duration
     */
    public void setBurnoutModifier(double modifier)
    {
        if (modifier < 0.2) {
            modifier = 0.2;
        }
        if (modifier > 3.0) {
            modifier = 3.0;
        }

        this.burnoutModifier = modifier;
    }

    /**
     * Returns the typist's accuracy rating.
     *
     * @return accuracy as a double between 0.0 and 1.0
     */
    public double getAccuracy()
    {
        return this.typistAccuracy;
    }

    /**
     * Returns the typist's current progress through the passage.
     * Progress is measured in characters typed correctly so far.
     * Note: this value can decrease if the typist mistypes.
     *
     * @return progress as a non-negative integer
     */
    public int getProgress()
    {
        return this.currentProgress;
    }

    /**
     * Returns the name of the typist.
     *
     * @return the typist's name as a String
     */
    public String getName()
    {
        return this.typistName;
    }

    /**
     * Returns the character symbol used to represent this typist.
     *
     * @return the typist's symbol as a char
     */
    public char getSymbol()
    {
        return this.typistSymbol; 
    }

    /**
     * Returns the number of turns of burnout remaining.
     * Returns 0 if the typist is not currently burnt out.
     *
     * @return burnout turns remaining as a non-negative integer
     */
    public int getBurnoutTurnsRemaining()
    {
        if (!this.isBurned) {
            return 0;
        }
        return this.burnTurns;
    }

    /**
     * Resets the typist to their initial state, ready for a new race.
     * Progress returns to zero, burnout is cleared entirely.
     */
    public void resetToStart()
    {
        this.isBurned = false;
        this.burnTurns = 0;
        this.currentProgress = 0;
        this.ifMistype = false;
    }

    /**
     * Returns true if this typist is currently burnt out, false otherwise.
     *
     * @return true if burnt out
     */
    public boolean isBurntOut()
    {
        return this.isBurned;
    }


    // Sets mistype value to true when accessed
    public void markMistype () {
        this.ifMistype = true;
    }

    // Returns the boolean state of the Typist mistype
    public boolean hasMistype () {
        return this.ifMistype;
    }

    // Sets mistype value to false when accessed
    public void clearMistype() {
        this.ifMistype = false;
    }

    /**
     * Advances the typist forward by one character along the passage.
     * Should only be called when the typist is not burnt out.
     */
    public void typeCharacter()
    {
        if(!this.isBurned) {
        this.currentProgress ++;
        }
    }

    /**
     * Moves the typist backwards by a given number of characters (a mistype).
     * Progress cannot go below zero — the typist cannot slide off the start.
     *
     * @param amount the number of characters to slide back (must be positive)
     */
    public void slideBack(int amount)
    {
        if (amount <= 0) {
            return;
        }

        this.currentProgress -= amount;

        if (this.currentProgress < 0) { // Logic which clamps progress to 0 if negative
            this.currentProgress = 0;
        }
    }

    /**
     * Sets the accuracy rating of the typist.
     * Values below 0.0 should be set to 0.0; values above 1.0 should be set to 1.0.
     *
     * @param newAccuracy the new accuracy rating
     */
    public void setAccuracy(double newAccuracy)
    {
        if (newAccuracy < 0.0) { // Logic which clamps accuracy between 0 and 1
            newAccuracy = 0.0;
        }
        else if (newAccuracy > 1.0) {
            newAccuracy = 1.0;
        }

        newAccuracy = Math.round(newAccuracy * 100.0) / 100.0; // Rounds value to 2 decimal places

        this.typistAccuracy = newAccuracy;
    }

    /**
     * Sets the symbol used to represent this typist.
     *
     * @param newSymbol the new symbol character
     */
    public void setSymbol(char newSymbol)
    {
        this.typistSymbol = newSymbol;
    }

    public double getBurnoutModifier() {
        return this.burnoutModifier;
    }


    // setter method that assigns variable to colour
    public void setColour(Color colour) {
        this.colour = colour;
    }

    // getter method that retrieves colour from object
    public Color getColour() {
        return this.colour;
    }

}
