package Objects;

import Constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AiSnake extends PlayerSnake {
    private char direction = 'U';
    private char prevDirection = 'U';

    /**
     * Creates a Runnable object that represents the AI snake's behavior.
     * Determines the snake's direction based on the apple's position, obstacles, and the player snake.
     * Moves the snake accordingly and avoids obstacles.
     *
     * @param apple         The apple object representing the target for the snake.
     * @param obstacles     The list of obstacles on the game board.
     * @param playerSnake   The player's snake object.
     * @return A Runnable object representing the AI snake's behavior.
     */
    public Runnable createRunnable(Apple apple, List<Obstacle> obstacles, PlayerSnake playerSnake) {
        return () -> {
            prevDirection = direction;
            setDirection(apple, obstacles, playerSnake);
            prevDirection = direction;
            direction = avoidObstacles(obstacles, playerSnake);
            move();
        };
    }

    @Override
    protected void init() {
        snakeLength = Constants.INITIAL_SNAKE_LENGTH;
        //snakeLength=1;
        for (int i = 0; i < snakeLength; i++) {
            x[i] = 500 - i * Constants.UNIT_SIZE;
            y[i] = 500;
        }
    }

    private void loadImage(String filename) {
        ImageIcon tempHead = new ImageIcon(filename);
        image = tempHead.getImage();
    }

    @Override
    public void draw(Graphics g) {
        for (int i = 0; i < this.snakeLength; i++) {
            if (i == 0) {
                loadImage("src/resources/snakeHeadAi.png");
                g.drawImage(image, x[i], y[i], null);
            } else {
                loadImage("src/resources/snakeBodyAi.png");
                g.drawImage(image, x[i], y[i], null);
            }
        }
    }

    /**
     * Moves the AI snake based on its current direction.
     * Updates the snake's position and body segments accordingly.
     */
    public void move() {
        for (int i = snakeLength; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }
        switch (direction) {
            case 'U' -> y[0] = y[0] - Constants.UNIT_SIZE;
            case 'D' -> y[0] = y[0] + Constants.UNIT_SIZE;
            case 'L' -> x[0] = x[0] - Constants.UNIT_SIZE;
            case 'R' -> x[0] = x[0] + Constants.UNIT_SIZE;
        }
    }

    /**
     * Sets the direction of the AI snake based on the position of the apple, obstacles, and the player snake.
     * Determines the optimal direction to move towards the apple while avoiding obstacles and the player snake.
     *
     * @param apple         The apple object representing the target for the snake.
     * @param obstacles     The list of obstacles on the game board.
     * @param playerSnake   The player's snake object.
     */
    public void setDirection(Apple apple, List<Obstacle> obstacles, PlayerSnake playerSnake) {
        if (apple.getPositionX() > this.getHeadX() && !checkRight(obstacles, playerSnake) && prevDirection != 'L') direction = 'R';
        else if (apple.getPositionX() < this.getHeadX() && !checkLeft(obstacles, playerSnake) && prevDirection != 'R') direction = 'L';
        else if (apple.getPositionY() > this.getHeadY() && !checkDown(obstacles, playerSnake) && prevDirection != 'U') direction = 'D';
        else if (apple.getPositionY() < this.getHeadY() && !checkUp(obstacles, playerSnake) && prevDirection != 'D') direction = 'U';
    }

    /**
     * Determines the direction to avoid obstacles based on the current direction of the AI snake.
     * If the AI snake encounters an obstacle, it selects an alternative direction to avoid the obstacle.
     *
     * @param obstacles     The list of obstacles on the game board.
     * @param playerSnake   The player's snake object.
     * @return The direction to avoid obstacles.
     */
    private char avoidObstacles(List<Obstacle> obstacles, PlayerSnake playerSnake) {
        switch (direction) {
            case 'R' -> {
                if (!checkRight(obstacles, playerSnake)) return 'R';
                if (!checkUp(obstacles, playerSnake)) return 'U';
                else return 'D';
            }
            case 'L' -> {
                if (!checkLeft(obstacles, playerSnake)) return 'L';
                if (!checkUp(obstacles, playerSnake)) return 'U';
                else return 'D';
            }
            case 'D' -> {
                if (!checkDown(obstacles, playerSnake)) return 'D';
                if (!checkRight(obstacles, playerSnake)) return 'R';
                else return 'L';
            }
            case 'U' -> {
                if (!checkUp(obstacles, playerSnake)) return 'U';
                if (!checkRight(obstacles, playerSnake)) return 'R';
                else return 'L';
            }
        }
        return 'E';
    }

    /**
     * Checks if moving left will cause a collision with obstacles or the player snake.
     *
     * @param obstacles     The list of obstacles on the game board.
     * @param playerSnake   The player's snake object.
     * @return True if moving left will result in a collision, false otherwise.
     */
    private boolean checkLeft(List<Obstacle> obstacles, PlayerSnake playerSnake) {
        for (Obstacle obstacle : obstacles) {
            Rectangle obstacleRectangle = obstacle.getBounds();
            if (obstacleRectangle.contains(this.getHeadX() - Constants.UNIT_SIZE, this.getHeadY())) return true;
        }
        for (int i = 0; i < playerSnake.getSnakeLength(); i++) {
            if (this.getHeadX() - Constants.UNIT_SIZE == playerSnake.x[i] && this.getHeadY() == playerSnake.y[i]) return true;
        }
        for (int i = 1; i < this.getSnakeLength(); i++) {
            if (this.getHeadX() - Constants.UNIT_SIZE == this.x[i] && this.getHeadY() == this.y[i]) return true;
        }
        if ((this.getHeadX() - Constants.UNIT_SIZE) < 0) return true;
        return false;
    }

    /**
     * Checks if moving right will cause a collision with obstacles or the player snake.
     *
     * @param obstacles     The list of obstacles on the game board.
     * @param playerSnake   The player's snake object.
     * @return True if moving right will result in a collision, false otherwise.
     */
    private boolean checkRight(List<Obstacle> obstacles, PlayerSnake playerSnake) {
        for (Obstacle obstacle : obstacles) {
            Rectangle obstacleRectangle = obstacle.getBounds();
            if (obstacleRectangle.contains(this.getHeadX() + Constants.UNIT_SIZE, this.getHeadY())) return true;
        }
        for (int i = 0; i < playerSnake.getSnakeLength(); i++) {
            if (this.getHeadX() + Constants.UNIT_SIZE == playerSnake.x[i] && this.getHeadY() == playerSnake.y[i]) return true;
        }
        for (int i = 1; i < this.getSnakeLength(); i++) {
            if (this.getHeadX() + Constants.UNIT_SIZE == this.x[i] && this.getHeadY() == this.y[i]) return true;
        }
        if ((this.getHeadX() + Constants.UNIT_SIZE) > Constants.SCREEN_WIDTH) return true;
        return false;
    }

    /**
     * Checks if moving up will cause a collision with obstacles or the player snake.
     *
     * @param obstacles     The list of obstacles on the game board.
     * @param playerSnake   The player's snake object.
     * @return True if moving up will result in a collision, false otherwise.
     */
    private boolean checkUp(List<Obstacle> obstacles, PlayerSnake playerSnake) {
        for (Obstacle obstacle : obstacles) {
            Rectangle obstacleRectangle = obstacle.getBounds();
            if (obstacleRectangle.contains(this.getHeadX(), this.getHeadY() - Constants.UNIT_SIZE)) return true;
        }
        for (int i = 0; i < playerSnake.getSnakeLength(); i++) {
            if (this.getHeadX() == playerSnake.x[i] && this.getHeadY() - Constants.UNIT_SIZE == playerSnake.y[i]) return true;
        }
        for (int i = 1; i < this.getSnakeLength(); i++) {
            if (this.getHeadX() == this.x[i] && this.getHeadY() - Constants.UNIT_SIZE == this.y[i]) return true;
        }
        if ((this.getHeadY() - Constants.UNIT_SIZE) < 0) return true;
        return false;
    }

    /**
     * Checks if moving down will cause a collision with obstacles or the player snake.
     *
     * @param obstacles     The list of obstacles on the game board.
     * @param playerSnake   The player's snake object.
     * @return True if moving down will result in a collision, false otherwise.
     */
    private boolean checkDown(List<Obstacle> obstacles, PlayerSnake playerSnake) {
        for (Obstacle obstacle : obstacles) {
            Rectangle obstacleRectangle = obstacle.getBounds();
            if (obstacleRectangle.contains(this.getHeadX(), this.getHeadY() + Constants.UNIT_SIZE)) return true;
        }
        for (int i = 0; i < playerSnake.getSnakeLength(); i++) {
            if (this.getHeadX() == playerSnake.x[i] && this.getHeadY() + Constants.UNIT_SIZE == playerSnake.y[i]) return true;
        }
        for (int i = 1; i < this.getSnakeLength(); i++) {
            if (this.getHeadX() == this.x[i] && this.getHeadY() + Constants.UNIT_SIZE == this.y[i]) return true;
        }
        if ((this.getHeadY() + Constants.UNIT_SIZE) > Constants.SCREEN_HEIGHT) return true;
        return false;
    }
}
