import java.util.ArrayList;
import java.util.HashMap;

public class RaceHistory {

    private HashMap<String, ArrayList<Result>> history = new HashMap<>();
    private Leaderboard leaderboard = new Leaderboard();  // ADD

    public void addResult(Result r, int totalTypists) {  // CHANGE signature
        history.computeIfAbsent(r.getName(), k -> new ArrayList<>()).add(r);
        leaderboard.addResult(r, totalTypists);  // ADD
    }

    public double getPersonalBestWPM(String name) {
        if (!history.containsKey(name)) return 0;
        return history.get(name).stream()
            .mapToDouble(Result::getWPM)
            .max()
            .orElse(0);
    }

    public ArrayList<Result> getHistory(String name) {
        return history.getOrDefault(name, new ArrayList<>());
    }

    public Leaderboard getLeaderboard() { return leaderboard; }  // ADD
}