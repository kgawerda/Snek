package Objects;
import Constants.Constants;
import Panels.SnakePanel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PlayerSnake implements Runnable{
    private final int[] x =new int[Constants.SCREEN_WIDTH];
    private final int[] y =new int[Constants.SCREEN_HEIGHT];
    public int getHeadX(){return x[0];}
    public int getHeadY(){return y[0];}
    private int snakeLength;
    public int getSnakeLength(){return snakeLength;}
    private final PropertyChangeSupport changes;
    private final MyKeyAdapter keyAdapter = new MyKeyAdapter();
    public MyKeyAdapter getKeyAdapter(){return keyAdapter;}
    public Rectangle getBounds()
    {
        return new Rectangle(getHeadX(), getHeadY(), Constants.UNIT_SIZE, Constants.UNIT_SIZE);
    }

    public PlayerSnake(){
        changes= new PropertyChangeSupport(this);
        init();
    }

    private void init(){
        snakeLength=Constants.INITIAL_SNAKE_LENGTH;
        for (int i=0;i<snakeLength;i++){
            x[i]=100 - i*Constants.UNIT_SIZE;
            y[i]=100;
        }
    }

    public void setSnakeLength(int length){
        int old = this.snakeLength;
        this.snakeLength = length;
        changes.firePropertyChange("snakeLength", length,this.snakeLength);
    }

    @Override
    public void run(){
        for(int i=snakeLength;i>0;i--){
            x[i]=x[(i-1)];
            y[i]=y[(i-1)];
        }
        switch (keyAdapter.getDirection()) {
            case 'U' -> y[0] = y[0] - Constants.UNIT_SIZE;
            case 'D' -> y[0] = y[0] + Constants.UNIT_SIZE;
            case 'L' -> x[0] = x[0] - Constants.UNIT_SIZE;
            case 'R' -> x[0] = x[0] + Constants.UNIT_SIZE;
        }
    }

    public boolean checkCollisionsBoard(){
        for(int i=snakeLength;i>1;i--){
//            System.out.println("HeadX");
//            System.out.println(getHeadX());
//            System.out.println("HeadY");
//            System.out.println(getHeadY());
//            System.out.println("Xi");
//            System.out.println(x[i]);
//            System.out.println("Yi");
//            System.out.println(y[i]);
            if((getHeadX()==x[i])&&(getHeadY()==y[i])){
                System.out.println("Snake collision");
                return false;
            }
        }
        if(getHeadX()<0) return false;
        if(getHeadX()>Constants.SCREEN_WIDTH) return false;
        if(getHeadY()<0) return false;
        if(getHeadY()>Constants.SCREEN_HEIGHT) return false;

        return true;
    }

    public void draw(Graphics g){
        for(int i=0;i<snakeLength;i++){
            if(i==0){
                g.setColor(Color.green);
                g.fillRect(x[i],y[i],Constants.UNIT_SIZE,Constants.UNIT_SIZE);
            }
            else{
                g.setColor(Color.cyan);
                g.fillRect(x[i],y[i],Constants.UNIT_SIZE,Constants.UNIT_SIZE);
            }
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        changes.addPropertyChangeListener(listener);
    }

}
