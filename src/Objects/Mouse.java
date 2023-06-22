package Objects;

import Constants.Constants;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.abs;

public class Mouse extends Apple{
    public Mouse(){}

    private void moveRight(){
        if((positionX + Constants.UNIT_SIZE) < Constants.SCREEN_WIDTH) positionX += Constants.UNIT_SIZE;
    }
    private void moveLeft(){
        if((positionX - Constants.UNIT_SIZE) > 0) positionX -= Constants.UNIT_SIZE;
    }
    private void moveUp(){
        if((positionY + Constants.UNIT_SIZE) < Constants.SCREEN_HEIGHT) positionY += Constants.UNIT_SIZE;
    }
    private void moveDown(){
        if((positionY - Constants.UNIT_SIZE) > 0) positionY -= Constants.UNIT_SIZE;
    }

    private void moveRandom(){
        random = new Random();
        int randomInt = random.nextInt(5)%4;
        switch (randomInt) {
            case 0 -> moveUp();
            case 1 -> moveDown();
            case 2 -> moveLeft();
            case 3 -> moveRight();
        }
    }

    public void newMouse(/*ist<BoardObject> obstacles*/){
        random = new Random();
        //do{
//            positionX=randomNumber(0,(Constants.SCREEN_WIDTH/Constants.UNIT_SIZE)*Constants.UNIT_SIZE);
//            positionY=randomNumber(0,(Constants.SCREEN_HEIGHT/Constants.UNIT_SIZE)*Constants.UNIT_SIZE);
        positionX=random.nextInt(((Constants.SCREEN_WIDTH-8*Constants.UNIT_SIZE)/Constants.UNIT_SIZE))*Constants.UNIT_SIZE+4*Constants.UNIT_SIZE;
        positionY=random.nextInt(((Constants.SCREEN_HEIGHT-8*Constants.UNIT_SIZE)/Constants.UNIT_SIZE))*Constants.UNIT_SIZE+4*Constants.UNIT_SIZE;
        //}while(checkObstacle(obstacles));
    }
    public Runnable createRunnable(int snakeX,int snakeY){
        return new Runnable() {
            @Override
            public void run() {
                chase(snakeX, snakeY);
            }
        };
    }

    private void chase(int snakeX,int snakeY){
        random = new Random();
        if(random.nextInt(10)%2==1) moveRandom();
        if(positionX-snakeX>0 && abs(positionX-snakeX)<4*Constants.UNIT_SIZE && positionY==snakeY) moveRight();
        if(positionX-snakeX<0 && abs(positionX-snakeX)<4*Constants.UNIT_SIZE && positionY==snakeY) moveLeft();
        if(positionY-snakeY>0 && abs(positionY-snakeY)<4*Constants.UNIT_SIZE && positionX==snakeX) moveUp();
        if(positionY-snakeY<0 && abs(positionY-snakeY)<4*Constants.UNIT_SIZE && positionX==snakeX) moveDown();
    }


    public void draw(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(positionX,positionY,Constants.UNIT_SIZE,Constants.UNIT_SIZE);
    }
}
