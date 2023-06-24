package Objects;

import Constants.Constants;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Apple extends BoardObject {
    Random random;


    public void draw(Graphics g) {
        loadImage("src/resources/apple.png");
        g.drawImage(image, positionX, positionY, null);

    }

    protected boolean checkObstacle(List<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles) {
            Rectangle obstacleBounds = obstacle.getBounds();
            if (obstacleBounds.intersects(this.getBounds()))
                return true;
        }
        return false;
    }

    public void newApple(List<Obstacle> obstacles) {
        random = new Random();
        do {
            positionX = random.nextInt(((Constants.SCREEN_WIDTH - Constants.UNIT_SIZE) / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
            positionY = random.nextInt(((Constants.SCREEN_HEIGHT - Constants.UNIT_SIZE) / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
        } while (checkObstacle(obstacles));
    }

    public void checkCollision(PlayerSnake snake, List<Obstacle> obstacles) {
        if ((snake.getHeadX() == this.getPositionX()) && (snake.getHeadY() == this.getPositionY())) {
            snake.setSnakeLength(snake.getSnakeLength() + 1);
            this.newApple(obstacles);
        }
    }

    protected boolean checkObstacleSurrounding(List<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles) {
            Rectangle obstacleBounds = obstacle.getBoundsSurrounding();
            if (obstacleBounds.intersects(this.getBoundsSurrounding()))
                return true;
        }
        return false;
    }
}