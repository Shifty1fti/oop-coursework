import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

// class which is used to run the other pages
public class MyFrame extends JFrame {

    private StartupScreen startupScreen;
    private SettingScreen settingScreen;
    private RaceScreen raceScreen;
    private ResultScreen resultScreen;
    private GameSettings settings;
    private TypistScreen typistScreen;
    private ArrayList<Typist> currentTypists;
    private ArrayList<Result> currentResults;
    private RaceHistory raceHistory = new RaceHistory();

    public MyFrame() {
        setSize(2880, 1800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Typeracer");

        // assigns an icon to the program
        ImageIcon image = new ImageIcon("image.png");
        setIconImage(image.getImage());

        settings = new GameSettings();
        

        // method called which originally is run
        showStartup();
        setVisible(true);
    }

    public void showStartup() {
        startupScreen = new StartupScreen(
            () -> showSettings(),
            () -> showLeaderboard(),
            () -> System.exit(0)
        );

        setContentPane(startupScreen);
        revalidate();
        repaint();
    }

    public void showLeaderboard() {
        showResults();
    }

    // runs settings screen
    public void showSettings() {
        settingScreen = new SettingScreen(settings, () -> showTypists());

        setContentPane(settingScreen);

        revalidate();
        repaint();
    }

    // runs typist screen
    public void showTypists() {
        typistScreen = new TypistScreen(settings, () -> showRace());

        setContentPane(typistScreen);
        revalidate();
        repaint();
    }

    // runs race screen
    public void showRace() {

        settings.setPassage(settings.generate());

        currentTypists = typistScreen.buildTypists();

        raceScreen = new RaceScreen(settings, currentTypists, () -> showResults());

        setContentPane(raceScreen);

        revalidate();
        repaint();
    }

    // displays race results
        public void showResults() {
        currentResults = raceScreen.getResults();

        for (Result r : currentResults) {
            raceHistory.addResult(r, currentResults.size());  // CHANGE: pass size
        }

        resultScreen = new ResultScreen(currentResults, raceHistory, () -> showStartup());
        setContentPane(resultScreen);
        revalidate();
        repaint();
    }
}