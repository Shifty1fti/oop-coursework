public class Result {

    private String name;
    private int position;
    private double wpm;
    private double accuracy;
    private double accuracyChange;
    private double startingAccuracy;
    private int burnoutCount;
    private double timeTaken;
    private Typist typist;

    public Result(String name, int position, double wpm, double accuracy, double accuracyChange, int burnoutCount, double timeTaken) {
        this.name = name;
        this.position = position;
        this.wpm = wpm;
        this.accuracy = accuracy;
        this.accuracyChange = accuracyChange;
        this.startingAccuracy = accuracy - accuracyChange;  // Calculate from final accuracy and change
        this.burnoutCount = burnoutCount;
        this.timeTaken = timeTaken;
        this.typist = null;  // Initialize typist field
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public double getWPM() {
        return wpm;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getAccuracyChange() {
        return accuracyChange;
    }

    public int getBurnoutCount() {
        return burnoutCount;
    }

    public double getTimeTaken() {
        return timeTaken;
    }

    public void setPosition(int pos) {
        this.position = pos;
    }

    public void setAccuracyChange(double change) {
        this.accuracyChange = change;
    }

    public double getStartingAccuracy() {
        return startingAccuracy;
    }

    public void setAccuracy(double acc) {
        this.accuracy = acc;
    }

    public Typist getTypist() {
        return typist;
    }

    public void setTypist(Typist typist) {
        this.typist = typist;
    }
}