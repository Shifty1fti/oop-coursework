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

    // Accuracy thresholds for mistype and burnout events
    // (Ty tuned these values "by feel". They may need adjustment.)
    private static final double MISTYPE_BASE_CHANCE = 0.3;
    private static final int    SLIDE_BACK_AMOUNT   = 2;
    private static final int    BURNOUT_DURATION     = 3;

    public TypingRace(int passageLength)
    {
        this.passageLength = passageLength;
        this.typists = new ArrayList<>();
    }

    public void advance() { // loops through and advances every typist passed 
        for (Typist t: typists) {
            advanceTypist(t);
        }
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

        // Attempt to type a character
        if (Math.random() < theTypist.getAccuracy())
        {
            theTypist.typeCharacter();
        }

        // Mistype check — the probability should reflect the typist's accuracy
        if (Math.random() < (1 - theTypist.getAccuracy()) * MISTYPE_BASE_CHANCE)
        {
            theTypist.slideBack(SLIDE_BACK_AMOUNT);
            theTypist.markMistype();
        }

        // Burnout check — pushing too hard increases burnout risk
        // (probability scales with accuracy squared, capped at ~0.05)
        if (Math.random() < (0.05 * theTypist.getAccuracy() * theTypist.getAccuracy()))
        {
            theTypist.burnOut(BURNOUT_DURATION);
        }
    }

    public boolean isFinished() {
        for (Typist t: typists) {
            if (t.getProgress() >= passageLength) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Typist> getTypists() {
        return typists;
    }

    public int getLength() {
        return passageLength;
    }

    public void reset() {
        for (Typist t: typists) {
            t.resetToStart();
        }
    }
}
