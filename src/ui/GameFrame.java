package ui;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("Snake Arcade");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new GamePanel());
        pack();   // IMPORTANT FIX

        setVisible(true);
    }
}
