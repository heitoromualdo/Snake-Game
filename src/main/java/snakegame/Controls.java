package snakegame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controls {
    public KeyAdapter addKeyListener(Graphics graphics) {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (graphics.isMoving) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            if (graphics.direction != 'R') {
                                graphics.direction = 'L';
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (graphics.direction != 'L') {
                                graphics.direction = 'R';
                            }
                            break;
                        case KeyEvent.VK_UP:
                            if (graphics.direction != 'D') {
                                graphics.direction = 'U';
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (graphics.direction != 'U') {
                                graphics.direction = 'D';
                            }
                            break;
                    }
                } else {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        graphics.start();
                    }
                }
            }
        };
    }

    protected void move(Graphics graphics) {
        for (int i = graphics.snakeLength; i > 0; i--) {
            graphics.snakePosX[i] = graphics.snakePosX[i - 1];
            graphics.snakePosY[i] = graphics.snakePosY[i - 1];
        }

        switch (graphics.direction) {
            case 'U' -> graphics.snakePosY[0] -= Graphics.TICK_SIZE;
            case 'D' -> graphics.snakePosY[0] += Graphics.TICK_SIZE;
            case 'L' -> graphics.snakePosX[0] -= Graphics.TICK_SIZE;
            case 'R' -> graphics.snakePosX[0] += Graphics.TICK_SIZE;
        }
    }
}
