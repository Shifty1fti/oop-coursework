import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MyFrame extends JFrame {

    public MyFrame() {
        this.setSize(2880, 1800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Typeracer");

        ImageIcon image = new ImageIcon("image.png"); // creates image icon
        this.setIconImage(image.getImage()); // loads icon and sets
        this.getContentPane().setBackground(new Color(0xeae4cf));
        this.setVisible(true);
    }
}
