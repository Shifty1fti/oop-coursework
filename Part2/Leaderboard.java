import java.util.*;

public class Leaderboard {

    // stores cumulative points per typist name
    private HashMap<String, Integer> points = new HashMap<>();
    // tracks consecutive wins for badge logic
    private HashMap<String, Integer> winStreak = new HashMap<>();
    // tracks races completed without a burnout
    private HashMap<String, Integer> burnoutFreeRaces = new HashMap<>();

    public void addResult(Result r, int totalTypists) {
        String name = r.getName();

        int earned = calculatePoints(r, totalTypists);
        points.merge(name, earned, Integer::sum);

        // update win streak
        if (r.getPosition() == 1) {
            winStreak.merge(name, 1, Integer::sum);
        } else {
            winStreak.put(name, 0);
        }

        // update burnout-free streak
        if (r.getBurnoutCount() == 0) {
            burnoutFreeRaces.merge(name, 1, Integer::sum);
        } else {
            burnoutFreeRaces.put(name, 0);
        }
    }

    private int calculatePoints(Result r, int totalTypists) {
        // position points: 1st gets totalTypists pts, last gets 1
        int positionPoints = Math.max(1, totalTypists - (r.getPosition() - 1));

        // bonus for WPM above 60
        int wpmBonus = (int) Math.max(0, (r.getWPM() - 60) / 10);

        // penalty if they burnt out at all
        int burnoutPenalty = r.getBurnoutCount() > 0 ? 1 : 0;

        return positionPoints + wpmBonus - burnoutPenalty;
    }

    public String getTitle(String name) {
        int streak = winStreak.getOrDefault(name, 0);
        int bfRaces = burnoutFreeRaces.getOrDefault(name, 0);
        int pts = points.getOrDefault(name, 0);

        if (streak >= 3)   return "Speed Demon";
        if (bfRaces >= 5)  return "Iron Fingers";
        if (pts >= 20)     return "Elite Typist";
        if (pts >= 10)     return "Rising Star";
        return "Rookie";
    }

    public int getPoints(String name) {
        return points.getOrDefault(name, 0);
    }

    // returns names sorted by points descending
    public ArrayList<String> getRanking() {
        ArrayList<String> names = new ArrayList<>(points.keySet());
        names.sort((a, b) -> points.get(b) - points.get(a));
        return names;
    }
}