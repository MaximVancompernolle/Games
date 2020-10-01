import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {

    GamePanel panel;

    GameFrame() {

        panel = new GamePanel();

        this.add(panel);
        this.setTitle("Pong Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBackground(Color.black);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}