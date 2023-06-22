package Objects;

import Constants.Constants;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Apple extends BoardObject {
    Random random;

    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(positionX, positionY, Constants.UNIT_SIZE, Constants.UNIT_SIZE);
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
            positionX = random.nextInt(Constants.UNIT_SIZE, (Constants.SCREEN_WIDTH / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
            positionY = random.nextInt(Constants.UNIT_SIZE, (Constants.SCREEN_HEIGHT / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
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
            Rectangle obstacleBounds = obstacle.getBounds();
            if (obstacleBounds.intersects(this.getBoundsSurrounding()))
                return true;
        }
        return false;
    }
}
