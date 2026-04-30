import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

// class which is used to run the other pages
public class MyFrame extends JFrame {

    private SettingScreen settingScreen;
    private RaceScreen raceScreen;
    private GameSettings settings;
    private TypistScreen typistScreen;

    public MyFrame() {
        setSize(2880, 1800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Typeracer");

        // assigns an icon to the program
        ImageIcon image = new ImageIcon("image.png");
        setIconImage(image.getImage());

        settings = new GameSettings();

        // method called which originally is run
        showSettings();
        setVisible(true);
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

        ArrayList<Typist> typists = typistScreen.buildTypists();

        raceScreen = new RaceScreen(settings,typists, () -> showSettings());

        setContentPane(raceScreen);

        revalidate();
        repaint();
    }
}