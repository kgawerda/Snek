package Objects;

import Constants.Constants;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Apple extends BoardObject{
    Random random;
    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fillOval(positionX,positionY,Constants.UNIT_SIZE,Constants.UNIT_SIZE);
    }

    protected boolean checkObstacle(List<BoardObject> obstacles){
        for(BoardObject obstacle : obstacles){
            Rectangle obstacleBounds = obstacle.getBounds();
            if(obstacleBounds.intersects(this.getBounds()))
                return true;
        }
        return false;
    }
    public void newApple(/*ist<BoardObject> obstacles*/){
        random = new Random();
        //do{
//            positionX=randomNumber(0,(Constants.SCREEN_WIDTH/Constants.UNIT_SIZE)*Constants.UNIT_SIZE);
//            positionY=randomNumber(0,(Constants.SCREEN_HEIGHT/Constants.UNIT_SIZE)*Constants.UNIT_SIZE);
            positionX=random.nextInt((Constants.SCREEN_WIDTH/Constants.UNIT_SIZE))*Constants.UNIT_SIZE;
            positionY=random.nextInt((Constants.SCREEN_WIDTH/Constants.UNIT_SIZE))*Constants.UNIT_SIZE;
        //}while(checkObstacle(obstacles));
    }

    public void checkCollisionApple(PlayerSnake snake/*, List<BoardObject> obstacles*/){
        if((snake.getHeadX()==this.getPositionX())&&(snake.getHeadY()==this.getPositionY())){
            snake.setSnakeLength(snake.getSnakeLength()+1);
            this.newApple(/*obstacles*/);
        }
    }
}
