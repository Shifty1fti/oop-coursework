import java.util.ArrayList;

/**
 * A typing race simulation. Three typists race to complete a passage of text,
 * advancing character by character — or sliding backwards when they mistype.
 *
 *
 * @author TyPosaurus
 * @version 0.7 (the other 0.3 is left as an exercise for the reader)
 */
public class TypingRace
{
    private int passageLength;   // Total characters in the passage to type
    private ArrayList<Typist> typists; // Typist attributes which store all 3 typists
    private GameSettings settings;
    private int turn;
    

    // Accuracy thresholds for mistype and burnout events
    // (Ty tuned these values "by feel". They may need adjustment.)
    private static final double MISTYPE_BASE_CHANCE = 0.3;
    private static final int    SLIDE_BACK_AMOUNT   = 2;
    private static final int    BURNOUT_DURATION     = 3;

    public TypingRace(int passageLength, GameSettings settings, ArrayList<Typist> typists)
    {
        this.passageLength = passageLength;
        this.typists = typists;
        this.settings = settings;
        this.turn = 0;
    }

    public void advance() { // loops through and advances every typist passed 
        for (Typist t: typists) {
            advanceTypist(t);
        }
        turn++;
    }

    private void advanceTypist(Typist theTypist)
    {
        theTypist.clearMistype(); // Resets state of mistype in every turn

        if (theTypist.isBurntOut())
        {
            // Recovering from burnout — skip this turn
            theTypist.recoverFromBurnout();
            return;
        }

        double accuracy = theTypist.getAccuracy();
        int slideBack = SLIDE_BACK_AMOUNT;

        // logic which handles caffeine boost
        if (settings.getCaffeine() && turn <= 10) {
            accuracy *= 1.2;
            if (accuracy > 1.0) {
                accuracy = 1.0;
            }
        }

        if (settings.getNight()) {
            accuracy *= 0.9;
        }

        if (settings.getAutocorrect()) {
            slideBack = SLIDE_BACK_AMOUNT / 2;
        }

        // Attempt to type a character
        if (Math.random() < accuracy)
        {
            theTypist.typeCharacter();
        }

        // Mistype check — the probability should reflect the typist's accuracy
        if (Math.random() < (1 - accuracy) * MISTYPE_BASE_CHANCE)
        {
            theTypist.slideBack(slideBack);
            theTypist.markMistype();
        }

        // Burnout check — pushing too hard increases burnout risk
        // (probability scales with accuracy squared, capped at ~0.05)

        double burnoutChance = 0.05 * accuracy * accuracy;

        // logic which handles aftermath of caffeine burnout
        if (settings.getCaffeine() && turn > 10) {
            burnoutChance *= 2;
        }

        if (Math.random() < burnoutChance)
        {
            theTypist.burnOut(BURNOUT_DURATION);
        }
    }

    // logic which checks if any single typist has won
    public boolean isFinished() {
        for (Typist t: typists) {
            if (t.getProgress() >= passageLength) {
                return true;
            }
        }
        return false;
    }

    // logic which returns the arraylist of typist
    public ArrayList<Typist> getTypists() {
        return typists;
    }

    // logic which returns the total passage length
    public int getLength() {
        return passageLength;
    }

    // logic which resets every typist back to the start
    public void reset() {
        for (Typist t: typists) {
            t.resetToStart();
        }
    }
}
