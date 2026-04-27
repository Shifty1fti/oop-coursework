import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MyFrame extends JFrame {

    MyFrame() {
        
        this.setSize(2880, 1800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits out of application
        this.setTitle("Typeracer"); // assigns title name as "Typeracer"
        this.setVisible(true); // Makes frame visible

        ImageIcon image = new ImageIcon("image.png"); // creates image icon
        this.setIconImage(image.getImage()); // loads icon and sets

        this.getContentPane().setBackground(new Color(0xeae4cf));
        }
        
    }