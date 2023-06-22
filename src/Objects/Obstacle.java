package Objects;

import Constants.Constants;

import java.awt.*;
import java.util.Random;

public class Obstacle extends BoardObject {
    Random random;
    private int length;
    private int width;

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public Obstacle(int max_length, int max_width) {
        random = new Random();
        randomLength(max_length);
        randomWidth(max_width);
        positionX = random.nextInt(((Constants.SCREEN_WIDTH / 2) / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
        positionY = random.nextInt((Constants.SCREEN_HEIGHT / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(positionX, positionY, length, width);
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

    private void randomLength(int max_size) {
        random = new Random();
        length = (random.nextInt(max_size) + 1) * Constants.UNIT_SIZE;
    }

    private void randomWidth(int max_size) {
        random = new Random();
        width = (random.nextInt(max_size) + 1) * Constants.UNIT_SIZE;
    }


}
