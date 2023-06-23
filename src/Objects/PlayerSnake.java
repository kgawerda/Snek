package Objects;

import Constants.Constants;
import Panels.SnakePanel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PlayerSnake implements Runnable {
    protected final int[] x = new int[Constants.SCREEN_WIDTH];
    protected final int[] y = new int[Constants.SCREEN_HEIGHT];

    public int getHeadX() {
        return x[0];
    }

    public int getHeadY() {
        return y[0];
    }

    protected int snakeLength;

    public int getSnakeLength() {
        return snakeLength;
    }

    protected final PropertyChangeSupport changes;
    protected final MyKeyAdapter keyAdapter = new MyKeyAdapter();

    public MyKeyAdapter getKeyAdapter() {
        return keyAdapter;
    }

    public Rectangle getBounds() {
        return new Rectangle(getHeadX(), getHeadY(), Constants.UNIT_SIZE, Constants.UNIT_SIZE);
    }

    public PlayerSnake() {
        changes = new PropertyChangeSupport(this);
        init();
    }

    protected void init() {
        snakeLength = Constants.INITIAL_SNAKE_LENGTH;
        for (int i = 0; i < snakeLength; i++) {
            x[i] = 100 - i * Constants.UNIT_SIZE;
            y[i] = 100;
        }
    }

    public void setSnakeLength(int length) {
        int old = this.snakeLength;
        this.snakeLength = length;
        changes.firePropertyChange("snakeLength", length, this.snakeLength);
    }

    @Override
    public void run() {
        for (int i = snakeLength; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }
        switch (keyAdapter.getDirection()) {
            case 'U' -> y[0] = y[0] - Constants.UNIT_SIZE;
            case 'D' -> y[0] = y[0] + Constants.UNIT_SIZE;
            case 'L' -> x[0] = x[0] - Constants.UNIT_SIZE;
            case 'R' -> x[0] = x[0] + Constants.UNIT_SIZE;
        }
    }

    public boolean checkCollisionsBoard() {
        for (int i = this.snakeLength; i > 1; i--) {
            if ((this.getHeadX() == this.x[i]) && (this.getHeadY() == this.y[i])) {
                //System.out.println("Snake collision");
                return false;
            }
        }
        if (this.getHeadX() < 0) return false;
        if (this.getHeadX() > Constants.SCREEN_WIDTH) return false;
        if (this.getHeadY() < 0) return false;
        if (this.getHeadY() > Constants.SCREEN_HEIGHT) return false;

        return true;
    }

    public void draw(Graphics g) {
        for (int i = 0; i < snakeLength; i++) {
            if (i == 0) {
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], Constants.UNIT_SIZE, Constants.UNIT_SIZE);
            } else {
                g.setColor(Color.cyan);
                g.fillRect(x[i], y[i], Constants.UNIT_SIZE, Constants.UNIT_SIZE);
            }
        }
    }

    // TODO: check if this is needed
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    
}
