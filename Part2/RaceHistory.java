import java.util.ArrayList;
import java.util.HashMap;

public class RaceHistory {
    // Maps typist name lists the Results across all races
    private HashMap<String, ArrayList<Result>> history = new HashMap<>();

    public void addResult(Result result) {
        history.computeIfAbsent(result.getName(), k -> new ArrayList<>()).add(result);
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

    public HashMap<String, ArrayList<Result>> getAllHistory() {
        return history;
    }
}