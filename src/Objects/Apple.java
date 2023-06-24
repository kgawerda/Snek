package Objects;

import Constants.Constants;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * The Apple class represents an apple object in the game.
 * It is a subclass of BoardObject.
 */
public class Apple extends BoardObject {
    Random random;

    /**
     * Draws the apple on the screen.
     *
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        loadImage("src/resources/apple.png");
        g.drawImage(image, positionX, positionY, null);
    }

    /**
     * Checks if the apple is obstructed by any obstacle on the game board.
     *
     * @param obstacles the list of obstacles on the game board
     * @return true if the apple is obstructed, false otherwise
     */
    protected boolean checkObstacle(List<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles) {
            Rectangle obstacleBounds = obstacle.getBounds();
            if (obstacleBounds.intersects(this.getBounds()))
                return true;
        }
        return false;
    }

    /**
     * Generates a new position for the apple on the game board,
     * ensuring it is not obstructed by any obstacle.
     *
     * @param obstacles the list of obstacles on the game board
     */
    public void newApple(List<Obstacle> obstacles) {
        random = new Random();
        do {
            positionX = random.nextInt(((Constants.SCREEN_WIDTH - Constants.UNIT_SIZE) / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
            positionY = random.nextInt(((Constants.SCREEN_HEIGHT - Constants.UNIT_SIZE) / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
        } while (checkObstacle(obstacles));
    }

    /**
     * Checks if the apple collides with the snake's head, and if so,
     * increases the snake's length by 1 and generates a new apple.
     *
     * @param snake     the player's snake object
     * @param obstacles the list of obstacles on the game board
     */
    public void checkCollision(PlayerSnake snake, List<Obstacle> obstacles) {
        if ((snake.getHeadX() == this.getPositionX()) && (snake.getHeadY() == this.getPositionY())) {
            snake.setSnakeLength(snake.getSnakeLength() + 1);
            this.newApple(obstacles);
        }
    }

    /**
     * Checks if the apple's surrounding area is obstructed by any obstacle on the game board.
     *
     * @param obstacles the list of obstacles on the game board
     * @return true if the apple's surrounding area is obstructed, false otherwise
     */
    protected boolean checkObstacleSurrounding(List<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles) {
            Rectangle obstacleBounds = obstacle.getBoundsSurrounding();
            if (obstacleBounds.intersects(this.getBoundsSurrounding()))
                return true;
        }
        return false;
    }
}
