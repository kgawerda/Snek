package Objects;

import Constants.Constants;

import java.awt.*;
import java.util.Random;

public class Obstacle extends BoardObject {
    Random random;


    public int getLength() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Obstacle(int max_width, int max_height) {
        random = new Random();
        randomWidth(max_width);
        randomHeight(max_height);
        positionX = random.nextInt(((Constants.SCREEN_WIDTH / 2) / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
        positionY = random.nextInt((Constants.SCREEN_HEIGHT / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(positionX, positionY, width, height);
    }

//    private void randomDirection() {
//        random = new Random();
//        int randomInt = random.nextInt(5) % 4;
//        switch (randomInt) {
//            case 0 -> direction = 'U';
//            case 1 -> direction = 'D';
//            case 2 -> direction = 'L';
//            case 3 -> direction = 'R';
//        }
//    }

    private void randomWidth(int max_size) {
        random = new Random();
        width = (random.nextInt(1, max_size)) * Constants.UNIT_SIZE;
    }

    private void randomHeight(int max_size) {
        random = new Random();
        height = (random.nextInt(1, max_size)) * Constants.UNIT_SIZE;
    }


}
