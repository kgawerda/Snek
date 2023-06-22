package Objects;

import Constants.Constants;

import java.awt.*;
import java.util.Random;

public class VerticalObstacle extends BoardObject{
    Random random;
    public final int[] x =new int[Constants.SCREEN_WIDTH];
    public final int[] y =new int[Constants.SCREEN_HEIGHT];
    private int length;
    private int width;
    private char direction;
    public int getLength(){return length;}
    public int getWidth(){return width;}
    public int getHeadX(){return x[0];}
    public int getHeadY(){return y[0];}
    public char getDirection(){return direction;}

    public VerticalObstacle(){
        random = new Random();
        randomLength();
        randomWidth();
//        x[0]=random.nextInt(Constants.SCREEN_WIDTH*4/5/Constants.UNIT_SIZE)*Constants.UNIT_SIZE+Constants.SCREEN_WIDTH/5;
//        y[0]=random.nextInt(Constants.SCREEN_HEIGHT*4/5/Constants.UNIT_SIZE)*Constants.UNIT_SIZE+Constants.SCREEN_HEIGHT/5;
        x[0]=random.nextInt((Constants.SCREEN_WIDTH/Constants.UNIT_SIZE))*Constants.UNIT_SIZE;
        y[0]=random.nextInt(((Constants.SCREEN_HEIGHT/2)/Constants.UNIT_SIZE))*Constants.UNIT_SIZE;
    }
    public void draw(Graphics g){
        g.setColor(Color.white);
//        for(int i=0;i<length;i++){
//            for(int j=0;j<width;j++){
//                g.fillRect(x[i],y[j],Constants.UNIT_SIZE,Constants.UNIT_SIZE);
//            }
//        }
        g.fillRect(x[0],y[0],length,width);
    }
    private void randomDirection(){
        random = new Random();
        int randomInt = random.nextInt(5)%4;
        switch (randomInt){
            case 0 -> direction = 'U';
            case 1 -> direction = 'D';
            case 2 -> direction = 'L';
            case 3 -> direction = 'R';
        }
    }

    private void randomLength(){
        random = new Random();
        length = (random.nextInt(2)+3)*Constants.UNIT_SIZE;
    }
    private void randomWidth(){
        random = new Random();
        width = (random.nextInt(10)+1)*Constants.UNIT_SIZE;
    }


}
