package Objects;

import Constants.Constants;

import java.awt.*;
import java.util.List;

public class AiSnake extends PlayerSnake {
    private char direction;

    public Runnable createRunnable(Apple apple, List<Obstacle> obstacles, PlayerSnake playerSnake) {
        return new Runnable() {
            @Override
            public void run() {
                for (int i = snakeLength; i > 0; i--) {
                    x[i] = x[(i - 1)];
                    y[i] = y[(i - 1)];
                }
                moveTowardsApple(apple, obstacles, playerSnake);
            }
        };
    }

    @Override
    protected void init() {
        snakeLength = Constants.INITIAL_SNAKE_LENGTH;
        for (int i = 0; i < snakeLength; i++) {
            x[i] = 500 - i * Constants.UNIT_SIZE;
            y[i] = 500;
        }
    }

    public Rectangle getBoundsSurrounding() {
        return new Rectangle(getHeadX() - Constants.UNIT_SIZE, getHeadY() - Constants.UNIT_SIZE, 3 * Constants.UNIT_SIZE, 3 * Constants.UNIT_SIZE);
    }

    public boolean checkCloseCollisions(List<Obstacle> obstacles, PlayerSnake playerSnake) {
        for (Obstacle obstacle : obstacles) {
            Rectangle obstacleRectangle = obstacle.getBoundsSurrounding();
            if (this.getBoundsSurrounding().intersects(obstacleRectangle)) return true;
        }
//        for(int i=snakeLength;i>1;i--){
//            if(((this.getHeadX()+1==x[i])||(this.getHeadX()-1==x[i]))&&((this.getHeadY()+1==y[i])||this.getHeadY()-1==y[i])){
//                System.out.println("Snake collision");
//                return true;
//            }
//        }
//        for(int i=0;i<playerSnake.getSnakeLength();i++){
//            Rectangle snakeRect = new Rectangle(x[i],y[i],Constants.UNIT_SIZE, Constants.UNIT_SIZE);
//            if(this.getBoundsSurrounding().intersects(snakeRect)) return true;
//        }
        return false;
    }

    public boolean checkNext(List<Obstacle> obstacles, PlayerSnake playerSnake) {
        for (Obstacle obstacle : obstacles) {
            Rectangle obstacleRectangle = obstacle.getBounds();
            if (direction == 'R') {
                return obstacleRectangle.contains(x[0] + Constants.UNIT_SIZE, y[0]);
            }
            if (direction == 'L') {
                return obstacleRectangle.contains(x[0] - Constants.UNIT_SIZE, y[0]);
            }
            if (direction == 'U') {
                return obstacleRectangle.contains(x[0], y[0] - Constants.UNIT_SIZE);
            }
            if (direction == 'D') {
                return obstacleRectangle.contains(x[0], y[0] + Constants.UNIT_SIZE);
            }
        }
        return false;
    }

    public void moveTowardsApple(Apple apple, List<Obstacle> obstacles, PlayerSnake playerSnake) {
        if (apple.getPositionX() > this.getHeadX()) {
            if (checkCloseCollisions(obstacles, playerSnake)) {
                y[0] = y[0] - Constants.UNIT_SIZE;
                direction = 'U';
            } else {
                x[0] = x[0] + Constants.UNIT_SIZE;
                direction = 'R';
            }
        } else if (apple.getPositionX() < this.getHeadX()) {
            if (checkCloseCollisions(obstacles, playerSnake)) {
                y[0] = y[0] - Constants.UNIT_SIZE;
                direction = 'U';
            } else {
                x[0] = x[0] - Constants.UNIT_SIZE;
                direction = 'L';
            }
        } else if (apple.getPositionY() > this.getHeadY()) {
            if (checkCloseCollisions(obstacles, playerSnake)) {
                x[0] = x[0] + Constants.UNIT_SIZE;
                direction = 'R';
            } else {
                y[0] = y[0] + Constants.UNIT_SIZE;
                direction = 'D';
            }
        } else if (apple.getPositionY() < this.getHeadY()) {
            if (checkCloseCollisions(obstacles, playerSnake)) {
                x[0] = x[0] + Constants.UNIT_SIZE;
                direction = 'R';
            } else {
                y[0] = y[0] - Constants.UNIT_SIZE;
                direction = 'U';
            }
        }
    }

}
