package Objects;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The MyKeyAdapter class is a custom KeyAdapter that handles keyboard events for controlling the game.
 * It tracks the direction in which the user intends to move the snake.
 */
public class MyKeyAdapter extends KeyAdapter {
    private char direction = 'R';

    /**
     * Invoked when a key is pressed.
     * Updates the direction based on the key pressed by the user.
     *
     * @param e the KeyEvent object representing the key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                if (direction != 'R') direction = 'L';
            }
            case KeyEvent.VK_RIGHT -> {
                if (direction != 'L') direction = 'R';
            }
            case KeyEvent.VK_UP -> {
                if (direction != 'D') direction = 'U';
            }
            case KeyEvent.VK_DOWN -> {
                if (direction != 'U') direction = 'D';
            }
        }
    }

    /**
     * Retrieves the current direction.
     *
     * @return the current direction
     */
    public char getDirection() {
        return direction;
    }
}
