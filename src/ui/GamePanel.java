package ui;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Font;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import model.Snake;
import model.Food;

public class GamePanel extends JPanel {

    public static final int TILE_SIZE = 20;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    private Snake snake;
    private Food food;
    private Timer timer;

    private boolean gameOver = false;
    private boolean paused = false;
    private int score = 0;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        snake = new Snake();
        food = new Food();

        // Keyboard control (Arrow Keys + WASD)
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {

                    // UP
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        snake.setDirection(Snake.Direction.UP);
                        break;

                    // DOWN
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        snake.setDirection(Snake.Direction.DOWN);
                        break;

                    // LEFT
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        snake.setDirection(Snake.Direction.LEFT);
                        break;

                    // RIGHT
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        snake.setDirection(Snake.Direction.RIGHT);
                        break;

                    // PAUSE
                    case KeyEvent.VK_P:
                        paused = !paused;
                        break;

                    // RESTART
                    case KeyEvent.VK_R:
                        if (gameOver) restartGame();
                        break;
                }
            }
        });

        // Game loop
        timer = new Timer(150, e -> {
            if (gameOver || paused) return;

            snake.move();
            Point head = snake.getBody().getFirst();

            // Wall collision
            if (head.x < 0 || head.y < 0 ||
                head.x >= WIDTH / TILE_SIZE ||
                head.y >= HEIGHT / TILE_SIZE) {
                gameOver = true;
                repaint();
                return;
            }

            // Self collision
            if (snake.hitsItself()) {
                gameOver = true;
                repaint();
                return;
            }

            // Food collision
            if (head.equals(food.getPosition())) {
                snake.grow();
                food.spawn();
                score += 10;
                updateSpeed();
            }

            repaint();
        });

        timer.start();
        requestFocusInWindow();
    }

    private void updateSpeed() {
        int newDelay = Math.max(60, 150 - score);
        timer.setDelay(newDelay);
    }

    private void restartGame() {
        snake.reset();
        food.spawn();
        score = 0;
        gameOver = false;
        paused = false;
        timer.setDelay(150);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawFood(g);
        drawSnake(g);
        drawHUD(g);

        if (gameOver) drawGameOver(g);
        else if (paused) drawPaused(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (int x = 0; x < WIDTH; x += TILE_SIZE)
            g.drawLine(x, 0, x, HEIGHT);
        for (int y = 0; y < HEIGHT; y += TILE_SIZE)
            g.drawLine(0, y, WIDTH, y);
    }

    private void drawSnake(Graphics g) {
        g.setColor(Color.GREEN);
        for (Point p : snake.getBody()) {
            g.fillRoundRect(
                p.x * TILE_SIZE,
                p.y * TILE_SIZE,
                TILE_SIZE,
                TILE_SIZE,
                8, 8
            );
        }
    }

    private void drawFood(Graphics g) {
        g.setColor(Color.RED);
        Point p = food.getPosition();
        g.fillOval(
            p.x * TILE_SIZE,
            p.y * TILE_SIZE,
            TILE_SIZE,
            TILE_SIZE
        );
    }

    private void drawHUD(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Score: " + score, 10, 20);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("GAME OVER", 170, 280);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Final Score: " + score, 220, 320);
        g.drawString("Press R to Restart", 200, 350);
    }

    private void drawPaused(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("PAUSED", 235, 300);
    }
}
