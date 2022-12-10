package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graphics extends JPanel implements ActionListener {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final int TICK_SIZE = 50;
    public static final int BOARD_SIZE = (WIDTH * HEIGHT) / (TICK_SIZE * TICK_SIZE);
    int[] snakePosX = new int[BOARD_SIZE];
    int[] snakePosY = new int[BOARD_SIZE];
    public final Font font = new Font("TimesRoman", Font.BOLD, 30);
    public int snakeLength;
    public Food food;
    public int foodEaten;
    public char direction = 'R';
    public boolean isMoving = false;
    public final Timer timer = new Timer(150, this);
    Controls control = new Controls();

    public Graphics() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(control.addKeyListener(this));
        start();
    }

    protected void start() {
        System.out.println("dentro do start");
        snakePosX = new int[BOARD_SIZE];
        snakePosY = new int[BOARD_SIZE];
        snakeLength = 2;
        foodEaten = 0;
        direction = 'R';
        isMoving = true;
        spawnFood();
        timer.start();
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        if (isMoving) {
            g.setColor(Color.decode("#DC143C"));
            g.fillOval(food.getPosX(), food.getPosY(), TICK_SIZE, TICK_SIZE);

            g.setColor(Color.decode("#006400"));
            g.fillRect(snakePosX[0], snakePosY[0], TICK_SIZE, TICK_SIZE);

            g.setColor(Color.GREEN);
            for (int i = 1; i < snakeLength; i++) {
                g.fillRect(snakePosX[i], snakePosY[i], TICK_SIZE, TICK_SIZE);
            }
        } else {
            String scoreText = String.format("Score: " + foodEaten);
            String playAgainText = String.format("Press any key to play again!");
            g.setColor(Color.GREEN);
            g.setFont(font);
            g.drawString(scoreText, (WIDTH - getFontMetrics(g.getFont()).stringWidth(scoreText)) / 2, (HEIGHT - getFontMetrics(g.getFont()).stringWidth(playAgainText)) / 2);
            g.drawString(playAgainText, (WIDTH - getFontMetrics(g.getFont()).stringWidth(playAgainText)) / 2, HEIGHT / 2);
        }
    }

    protected void spawnFood() {
        food = new Food();
    }

    protected void eatFood() {
        if ((snakePosX[0] == food.getPosX()) && (snakePosY[0] == food.getPosY())) {
            snakeLength++;
            foodEaten++;
            spawnFood();
        }
    }

    protected void collisionTest() {
        for (int i = snakeLength; i > 0; i--) {
            if ((snakePosX[0] == snakePosX[i]) && (snakePosY[0] == snakePosY[i])) {
                isMoving = false;
                break;
            }
        }
        if (snakePosX[0] < 0 || snakePosX[0] > WIDTH - TICK_SIZE || snakePosY[0] < 0 || snakePosY[0] > HEIGHT - TICK_SIZE) {
            isMoving = false;
        }

        if (!isMoving) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isMoving) {
            control.move(this);
            collisionTest();
            eatFood();
        }
        repaint();
    }
}
