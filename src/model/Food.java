package model;

import java.awt.Point;
import java.util.Random;

public class Food {

    private Point position;
    private Random random = new Random();

    public Food() {
        spawn();
    }

    public void spawn() {
        int x = random.nextInt(30); // 600 / 20
        int y = random.nextInt(30);
        position = new Point(x, y);
    }

    public Point getPosition() {
        return position;
    }
}
