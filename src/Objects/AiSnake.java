package Objects;

import Constants.Constants;

import java.awt.*;
import java.util.List;

public class AiSnake extends PlayerSnake {
    private char direction = 'U';
    private char prevDirection = 'U';

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

    @Override
    public void draw(Graphics g) {
        for (int i = 0; i < this.snakeLength; i++) {
            if (i == 0) {
                g.setColor(Color.orange);
                g.fillRect(x[i], y[i], Constants.UNIT_SIZE, Constants.UNIT_SIZE);
            } else {
                g.setColor(Color.red);
                g.fillRect(x[i], y[i], Constants.UNIT_SIZE, Constants.UNIT_SIZE);
            }
        }
    }


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

    public void setDirection(Apple apple, List<Obstacle> obstacles, PlayerSnake playerSnake) {
        if (apple.getPositionX() > this.getHeadX() && !checkRight(obstacles, playerSnake) && prevDirection != 'L') direction = 'R';

        else if (apple.getPositionX() < this.getHeadX() && !checkLeft(obstacles, playerSnake) && prevDirection != 'R') direction = 'L';

        else if (apple.getPositionY() > this.getHeadY() && !checkDown(obstacles, playerSnake) && prevDirection != 'U') direction = 'D';

        else if (apple.getPositionY() < this.getHeadY() && !checkUp(obstacles, playerSnake) && prevDirection != 'D') direction = 'U';
    }

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
