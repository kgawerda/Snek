package Objects;

import Constants.Constants;

import java.awt.*;
import java.util.Random;
import java.util.List;

import static java.lang.Math.abs;

public class Mouse extends Apple {
    public Mouse() {
    }

    private void moveRight(List<Obstacle> obstacles) {
        if ((positionX + Constants.UNIT_SIZE) < Constants.SCREEN_WIDTH && !checkObstacleSurrounding(obstacles)) positionX += Constants.UNIT_SIZE;
    }

    private void moveLeft(List<Obstacle> obstacles) {
        if ((positionX - Constants.UNIT_SIZE) > 0 && !checkObstacleSurrounding(obstacles)) positionX -= Constants.UNIT_SIZE;
    }

    private void moveUp(List<Obstacle> obstacles) {
        if ((positionY + Constants.UNIT_SIZE) < Constants.SCREEN_HEIGHT && !checkObstacleSurrounding(obstacles)) positionY += Constants.UNIT_SIZE;
    }

    private void moveDown(List<Obstacle> obstacles) {
        if ((positionY - Constants.UNIT_SIZE) > 0 && !checkObstacleSurrounding(obstacles)) positionY -= Constants.UNIT_SIZE;
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

    public void newMouse(List<Obstacle> obstacles) {
        random = new Random();
        do {
            positionX = random.nextInt(((Constants.SCREEN_WIDTH - 8 * Constants.UNIT_SIZE) / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE + 4 * Constants.UNIT_SIZE;
            positionY = random.nextInt(((Constants.SCREEN_HEIGHT - 8 * Constants.UNIT_SIZE) / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE + 4 * Constants.UNIT_SIZE;
        } while (checkObstacle(obstacles));
    }

    public Runnable createRunnable(int snakeX, int snakeY, List<Obstacle> obstacles) {
        return () -> chase(snakeX, snakeY, obstacles);
    }

    private void chase(int snakeX, int snakeY, List<Obstacle> obstacles) {
        random = new Random();
        if (random.nextInt(10) % 2 == 1) moveRandom(obstacles);
        if (positionX - snakeX > 0 && abs(positionX - snakeX) < 4 * Constants.UNIT_SIZE && positionY == snakeY) moveRight(obstacles);
        if (positionX - snakeX < 0 && abs(positionX - snakeX) < 4 * Constants.UNIT_SIZE && positionY == snakeY) moveLeft(obstacles);
        if (positionY - snakeY > 0 && abs(positionY - snakeY) < 4 * Constants.UNIT_SIZE && positionX == snakeX) moveUp(obstacles);
        if (positionY - snakeY < 0 && abs(positionY - snakeY) < 4 * Constants.UNIT_SIZE && positionX == snakeX) moveDown(obstacles);
    }


    public void draw(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(positionX, positionY, Constants.UNIT_SIZE, Constants.UNIT_SIZE);
    }
}
