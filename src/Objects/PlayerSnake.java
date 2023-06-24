package Objects;

import Constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeSupport;

/**
 * The PlayerSnake class represents the player-controlled snake in the game.
 * It manages the snake's position, length, movement, and collision detection.
 */
public class PlayerSnake implements Runnable {
    protected final int[] x = new int[Constants.SCREEN_WIDTH];
    protected final int[] y = new int[Constants.SCREEN_HEIGHT];

    /**
     * Retrieves the X coordinate of the snake's head.
     *
     * @return the X coordinate of the snake's head
     */
    public int getHeadX() {
        return x[0];
    }

    /**
     * Retrieves the Y coordinate of the snake's head.
     *
     * @return the Y coordinate of the snake's head
     */
    public int getHeadY() {
        return y[0];
    }

    protected int snakeLength;

    protected Image image;

    /**
     * Retrieves the length of the snake.
     *
     * @return the length of the snake
     */
    public int getSnakeLength() {
        return snakeLength;
    }

    protected final PropertyChangeSupport changes;
    protected final MyKeyAdapter keyAdapter = new MyKeyAdapter();

    /**
     * Retrieves the key adapter associated with the snake.
     *
     * @return the key adapter associated with the snake
     */
    public MyKeyAdapter getKeyAdapter() {
        return keyAdapter;
    }

    /**
     * Retrieves the bounding rectangle of the snake's head.
     *
     * @return the bounding rectangle of the snake's head
     */
    public Rectangle getBounds() {
        return new Rectangle(getHeadX(), getHeadY(), Constants.UNIT_SIZE, Constants.UNIT_SIZE);
    }

    /**
     * Constructs a new PlayerSnake object.
     */
    public PlayerSnake() {
        changes = new PropertyChangeSupport(this);
        init();
    }

    /**
     * Initializes the snake's position and length.
     */
    protected void init() {
        snakeLength = Constants.INITIAL_SNAKE_LENGTH;
        for (int i = 0; i < snakeLength; i++) {
            x[i] = 100 - i * Constants.UNIT_SIZE;
            y[i] = 100;
        }
    }

    /**
     * Sets the length of the snake.
     *
     * @param length the new length of the snake
     */
    public void setSnakeLength(int length) {
        this.snakeLength = length;
        changes.firePropertyChange("snakeLength", length, this.snakeLength);
    }

    /**
     * Executes the main logic of the snake's movement.
     * Updates the positions of each segment of the snake based on the direction of movement.
     */
    @Override
    public void run() {
        for (int i = snakeLength; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }
        switch (keyAdapter.getDirection()) {
            case 'U' -> y[0] = y[0] - Constants.UNIT_SIZE;
            case 'D' -> y[0] = y[0] + Constants.UNIT_SIZE;
            case 'L' -> x[0] = x[0] - Constants.UNIT_SIZE;
            case 'R' -> x[0] = x[0] + Constants.UNIT_SIZE;
        }
    }

    /**
     * Checks if the snake collides with the boundaries of the game board.
     *
     * @return true if the snake is within the board boundaries, false otherwise
     */
    public boolean checkCollisionsBoard() {
        for (int i = this.snakeLength; i > 1; i--) {
            if ((this.getHeadX() == this.x[i]) && (this.getHeadY() == this.y[i])) {
                return false;
            }
        }
        if (this.getHeadX() < 0) return false;
        if (this.getHeadX() > Constants.SCREEN_WIDTH) return false;
        if (this.getHeadY() < 0) return false;
        if (this.getHeadY() > Constants.SCREEN_HEIGHT) return false;

        return true;
    }

    /**
     * Checks if the snake collides with another snake.
     *
     * @param enemySnake the other snake to check collision against
     * @return true if there is no collision, false if there is a collision
     */
    public boolean checkCollisionSnake(PlayerSnake enemySnake) {
        for (int i = 0; i < enemySnake.getSnakeLength(); i++) {
            if (this.getHeadX() == enemySnake.x[i] && this.getHeadY() == enemySnake.y[i]) return false;
        }
        return true;
    }

    /**
     * Loads an image file and assigns it to the snake's head or body segment.
     *
     * @param filename the filename of the image to load
     */
    private void loadImage(String filename) {
        ImageIcon tempHead = new ImageIcon(filename);
        image = tempHead.getImage();
    }

    /**
     * Draws the snake on the graphics object.
     *
     * @param g the graphics object to draw on
     */
    public void draw(Graphics g) {
        for (int i = 0; i < snakeLength; i++) {
            if (i == 0) {
                loadImage("src/resources/snakeHead.png");
                g.drawImage(image, x[i], y[i], null);
            } else {
                loadImage("src/resources/snakeBody.png");
                g.drawImage(image, x[i], y[i], null);
            }
        }
    }
}
