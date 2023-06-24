package Objects;

import Constants.Constants;

import java.awt.*;
import java.util.Random;
import java.util.List;

import static java.lang.Math.abs;

/**
 * The Mouse class represents a mouse object in the game.
 * It is a subclass of Apple.
 */
public class Mouse extends Apple {

    /**
     * Creates a new instance of Mouse.
     */
    public Mouse() {
    }

    private void moveRight(List<Obstacle> obstacles) {
        if ((positionX + Constants.UNIT_SIZE) < Constants.SCREEN_WIDTH && !checkObstacleSurrounding(obstacles))
            positionX += Constants.UNIT_SIZE;
    }

    private void moveLeft(List<Obstacle> obstacles) {
        if ((positionX - Constants.UNIT_SIZE) > 0 && !checkObstacleSurrounding(obstacles))
            positionX -= Constants.UNIT_SIZE;
    }

    private void moveUp(List<Obstacle> obstacles) {
        if ((positionY + Constants.UNIT_SIZE) < Constants.SCREEN_HEIGHT && !checkObstacleSurrounding(obstacles))
            positionY += Constants.UNIT_SIZE;
    }

    private void moveDown(List<Obstacle> obstacles) {
        if ((positionY - Constants.UNIT_SIZE) > 0 && !checkObstacleSurrounding(obstacles))
            positionY -= Constants.UNIT_SIZE;
    }

    private void moveRandom(List<Obstacle> obstacles) {
        random = new Random();
        int randomInt = random.nextInt(5) % 4;
        switch (randomInt) {
            case 0 -> moveUp(obstacles);
            case 1 -> moveDown(obstacles);
            case 2 -> moveLeft(obstacles);
            case 3 -> moveRight(obstacles);
        }
    }

    /**
     * Generates a new position for the mouse on the game board,
     * ensuring it is not obstructed by any obstacle.
     *
     * @param obstacles the list of obstacles on the game board
     */
    public void newMouse(List<Obstacle> obstacles) {
        random = new Random();
        do {
            positionX = random.nextInt(((Constants.SCREEN_WIDTH - 8 * Constants.UNIT_SIZE) / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE + 4 * Constants.UNIT_SIZE;
            positionY = random.nextInt(((Constants.SCREEN_HEIGHT - 8 * Constants.UNIT_SIZE) / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE + 4 * Constants.UNIT_SIZE;
        } while (checkObstacle(obstacles));
    }

    /**
     * Creates a new Runnable object that represents the mouse's behavior of chasing the snake.
     *
     * @param snakeX    the X-coordinate of the snake's position
     * @param snakeY    the Y-coordinate of the snake's position
     * @param obstacles the list of obstacles on the game board
     * @return a new Runnable object
     */
    public Runnable createRunnable(int snakeX, int snakeY, List<Obstacle> obstacles) {
        return new Runnable() {
            @Override
            public void run() {
                chase(snakeX, snakeY, obstacles);
            }
        };
    }

    private void chase(int snakeX, int snakeY, List<Obstacle> obstacles) {
        random = new Random();
        if (random.nextInt(10) % 2 == 1) moveRandom(obstacles);
        if (positionX - snakeX > 0 && abs(positionX - snakeX) < 4 * Constants.UNIT_SIZE && positionY == snakeY)
            moveRight(obstacles);
        if (positionX - snakeX < 0 && abs(positionX - snakeX) < 4 * Constants.UNIT_SIZE && positionY == snakeY)
            moveLeft(obstacles);
        if (positionY - snakeY > 0 && abs(positionY - snakeY) < 4 * Constants.UNIT_SIZE && positionX == snakeX)
            moveUp(obstacles);
        if (positionY - snakeY < 0 && abs(positionY - snakeY) < 4 * Constants.UNIT_SIZE && positionX == snakeX)
            moveDown(obstacles);
    }

    /**
     * Draws the mouse on the screen.
     *
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        loadImage("src/resources/mice.png");
        g.drawImage(image, positionX, positionY, null);
    }
}
