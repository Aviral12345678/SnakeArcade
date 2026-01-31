package model;

import java.awt.Point;
import java.util.LinkedList;

public class Snake {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private LinkedList<Point> body;
    private Direction direction = Direction.RIGHT;

    public Snake() {
        reset();
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move() {
        Point head = body.getFirst();
        Point newHead = new Point(head);

        switch (direction) {
            case UP -> newHead.y--;
            case DOWN -> newHead.y++;
            case LEFT -> newHead.x--;
            case RIGHT -> newHead.x++;
        }

        body.addFirst(newHead);
        body.removeLast();
    }

    public void grow() {
        Point head = body.getFirst();
        body.addFirst(new Point(head));
    }

    public boolean hitsItself() {
        Point head = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void reset() {
        body = new LinkedList<>();
        body.add(new Point(5, 5));
        body.add(new Point(4, 5));
        body.add(new Point(3, 5));
        direction = Direction.RIGHT;
    }
}
