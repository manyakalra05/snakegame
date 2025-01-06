import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;
import javax.sound.sampled.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    final int BOX_SIZE = 20;
    final int GRID_WIDTH = 30;
    final int GRID_HEIGHT = 20;
    Timer timer;
    LinkedList<Point> snake;
    Point food;
    int dirX = 1, dirY = 0; // Initial direction: right
    boolean gameOver = false;
    boolean wallEnabled = true;
    int score = 0;
    int speed = 100;

    public SnakeGame() {
        setPreferredSize(new Dimension(GRID_WIDTH * BOX_SIZE, GRID_HEIGHT * BOX_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        initGame();
        timer = new Timer(speed, this);
        timer.start();
    }

    void initGame() {
        snake = new LinkedList<>();
        snake.add(new Point(5, 5));
        dirX = 1; dirY = 0;
        score = 0;
        gameOver = false;
        speed = 100;
        placeFood();
        if (timer != null) {
            timer.setDelay(speed);
        }
    }

    void placeFood() {
        Random rand = new Random();
        Point p;
        do {
            p = new Point(rand.nextInt(GRID_WIDTH), rand.nextInt(GRID_HEIGHT));
        } while (snake.contains(p));
        food = p;
    }

    void playSound(String name) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource(name));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw grid
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i <= GRID_WIDTH; i++) {
            g.drawLine(i * BOX_SIZE, 0, i * BOX_SIZE, GRID_HEIGHT * BOX_SIZE);
        }
        for (int i = 0; i <= GRID_HEIGHT; i++) {
            g.drawLine(0, i * BOX_SIZE, GRID_WIDTH * BOX_SIZE, i * BOX_SIZE);
        }

        // Draw food
        g.setColor(Color.RED);
        g.fillOval(food.x * BOX_SIZE, food.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);

        // Draw snake
        g.setColor(Color.GREEN);
        for (Point p : snake) {
            g.fill3DRect(p.x * BOX_SIZE, p.y * BOX_SIZE, BOX_SIZE, BOX_SIZE, true);
        }

        // Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Score: " + score, 10, 20);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over!", 70, 200);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to Restart", 90, 240);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        Point head = snake.peekFirst();
        Point newHead = new Point(head.x + dirX, head.y + dirY);

        if (wallEnabled) {
            if (newHead.x < 0 || newHead.y < 0 || newHead.x >= GRID_WIDTH || newHead.y >= GRID_HEIGHT || snake.contains(newHead)) {
                gameOver = true;
                playSound("/gameover.wav");
                repaint();
                return;
            }
        } else {
            newHead.x = (newHead.x + GRID_WIDTH) % GRID_WIDTH;
            newHead.y = (newHead.y + GRID_HEIGHT) % GRID_HEIGHT;
            if (snake.contains(newHead)) {
                gameOver = true;
                playSound("/gameover.wav");
                repaint();
                return;
            }
        }

        snake.addFirst(newHead);

        if (newHead.equals(food)) {
            score++;
            if (score % 5 == 0 && speed > 20) {
                speed -= 10;
                timer.setDelay(speed);
            }
            placeFood();
            playSound("/eat.wav");
        } else {
            snake.removeLast(); //this is how the snake moves: add a new head, remove the tail
        }

        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        //changes snake direction, only if its not opposite the current one
        if (key == KeyEvent.VK_UP && dirY == 0) { dirX = 0; dirY = -1; }
        else if (key == KeyEvent.VK_DOWN && dirY == 0) { dirX = 0; dirY = 1; }
        else if (key == KeyEvent.VK_LEFT && dirX == 0) { dirX = -1; dirY = 0; }
        else if (key == KeyEvent.VK_RIGHT && dirX == 0) { dirX = 1; dirY = 0; }
        else if (key == KeyEvent.VK_R) { initGame(); repaint(); }
        else if (key == KeyEvent.VK_W) { wallEnabled = !wallEnabled; } //wall toggle
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String args[]) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame(); //telling JFrame to display the game inside it
        frame.add(game);
        frame.pack(); //The frame will resize itself to fit the game panel based on its preferred size.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //This line sets the default operation when the user closes the window (clicking the "X" button in the top-right corner)
        frame.setLocationRelativeTo(null); // This method centers the JFrame on the screen
        frame.setVisible(true); // Without this, the frame would be created but wouldn't appear on the screen.
    }
}

<!-- Update 2024-11-24T10:56:13+05:30 -->
<!-- Update 2025-01-18T11:24:30+05:30 -->
<!-- Update 2025-02-18T13:53:35+05:30 -->
<!-- Update 2025-03-14T07:42:36+05:30 -->
<!-- Update 2025-03-14T17:38:36+05:30 -->
<!-- Update 2025-04-10T09:30:41+05:30 -->
<!-- Update 2025-05-27T11:57:56+05:30 -->
<!-- Update 2025-06-20T16:57:00+05:30 -->
<!-- Update 2025-07-22T08:35:07+05:30 -->
<!-- Update 2025-09-23T17:07:19+05:30 -->
<!-- Update 2024-12-14T18:57:23+05:30 -->
<!-- Update 2024-12-17T14:51:25+05:30 -->
<!-- Update 2024-12-20T17:57:29+05:30 -->
<!-- Update 2024-12-24T18:42:35+05:30 -->
<!-- Update 2024-12-25T15:32:36+05:30 -->
<!-- Update 2025-01-06T13:48:41+05:30 -->
<!-- Update 2025-01-06T10:16:41+05:30 -->