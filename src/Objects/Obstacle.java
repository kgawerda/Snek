package Objects;

import Constants.Constants;

import java.awt.*;
import java.util.Random;

/**
 * The Obstacle class represents an obstacle on the game board.
 * It extends the BoardObject class and provides methods for drawing and positioning the obstacle.
 */
public class Obstacle extends BoardObject {
    Random random;

    /**
     * Constructs a new Obstacle object with the specified maximum width and height.
     *
     * @param max_width  the maximum width of the obstacle
     * @param max_height the maximum height of the obstacle
     */
    public Obstacle(int max_width, int max_height) {
        random = new Random();
        randomWidth(max_width);
        randomHeight(max_height);
        positionX = random.nextInt(((Constants.SCREEN_WIDTH / 2) / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
        positionY = random.nextInt((Constants.SCREEN_HEIGHT / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
    }

    /**
     * Draws the obstacle on the graphics object.
     *
     * @param g the graphics object to draw on
     */
    public void draw(Graphics g) {
        loadImage("src/resources/obstacle.png");
        g.drawImage(image, positionX, positionY, width, height, null);
    }

    /**
     * Generates a random width for the obstacle within the specified maximum size.
     *
     * @param max_size the maximum size of the width
     */
    private void randomWidth(int max_size) {
        random = new Random();
        width = (random.nextInt(1, max_size)) * Constants.UNIT_SIZE;
    }

    /**
     * Generates a random height for the obstacle within the specified maximum size.
     *
     * @param max_size the maximum size of the height
     */
    private void randomHeight(int max_size) {
        random = new Random();
        height = (random.nextInt(1, max_size)) * Constants.UNIT_SIZE;
    }
}
