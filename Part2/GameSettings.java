import java.util.Random;

public class GameSettings {
    private int playerAmount;
    private String passageType;

    private boolean autocorrect;
    private boolean caffeine;
    private boolean night;

    private static final String[] shortPassage = {
        "The quick brown fox jumped over the lazy fence.",
        "Never put off until tomorrow what can be done today.",
        "The best way to defeat your enemy is to become their friend.",
        "Even mistakes made are lessons learned.",
        "Do fewer things. Do them better. Know why you're doing them.",
        "Save when you can and not when you have to."
    };

    private static final String[] mediumPassage = {
        "What you are aware of you are in control of. What you are not aware of is in control of you.",
        "Get up! Do not lie there thinking. Nothing good every came from that. Get up! Begin your day!",
        "Creativity is allowing yourself to make mistakes. Art is knowing which ones to keep.",
        "Don't look for the next opportunity. The one you have in hand is the opportunity.",
        "Keeping one's distance from an ignorant person is equivalent to keeping company with a wise man.",
        "Having knowledge but lacking the power to express it clearly is no better than never having any ideas at all.",
        "You will never be happier than you expect. To change your happiness, change your expectation.",
        "There will come a time when you believe everything is finished. That will be the beginning",
        "Without courage we cannot practice any other virtue with consistency. We can't be kind, true, merciful, generous, or honest."
    };

    private static final String[] longPassage = {
        "When someone tells you something is wrong, they're almost always right. When someone tells you how to fix it, they're almost always wrong. This applies to both writing and life.",
        "Your visions will become clear only when you can look into your own heart. Who looks outside, dreams; who looks inside, awakens.",
        "Never have your wallet with you onstage. It's bad luck. You shouldn't play the piano with money in your pocket. Play like you need the money.",
        "Courage is not simply one of the virtues, but the form of every virtue at the testing point, which means at the point of highest reality.",
        "What looks like a talent gap is often a focus gap. The 'all star' is often an average to above average performer who spends more time working on what is important and less time on distractions. The talent is staying focused",
        "Never speak of yourself to others; make them talk about themselves instead; therein lies the whole art of pleasing. Everybody knows it, and everyone forgets it."
    };

    private String customPassage = "Never put off until tomorrow what can be done today";

    private String passage;

    public GameSettings() {
        playerAmount = 2;
        passageType = "Short";

        autocorrect = false;
        caffeine = false;
        night = false;
    }

    public int getAmount() {
        return playerAmount;
    }

    public String getType() {
        return passageType;
    }

    public boolean getAutocorrect() {
        return autocorrect;
    }

    public boolean getCaffeine() {
        return caffeine;
    }

    public boolean getNight() {
        return night;
    }

    public void setAmount(int amount) {
        this.playerAmount = amount;
    }

    public void setType(String type) {
        this.passageType = type;
    }

    public void setAutocorrect(boolean autocorrect) {
        this.autocorrect = autocorrect;
    }

    public void setCaffeine(boolean caffeine) {
        this.caffeine = caffeine;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    public String generate() {
        Random random = new Random();

        if (passageType.equals("Short")) {
            return shortPassage[random.nextInt(shortPassage.length)];
        } 
        else if (passageType.equals("Medium")) {
            return mediumPassage[random.nextInt(mediumPassage.length)];
        } 
        else if (passageType.equals("Long")) {
            return longPassage[random.nextInt(longPassage.length)];
        }

        else if (passageType.equals("Custom")) {
            return customPassage;
        }

        return "";
        
    }

    public void setCustom(String text) {
        this.customPassage = text;
    }

    public String getCustom() {
        return this.customPassage;
    }

    public void setPassage(String text) {
        this.passage= text;
    }

    public String getPassage() {
        return this.passage;
    }

}