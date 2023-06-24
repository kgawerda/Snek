package Objects;

import Constants.Constants;

import java.awt.*;
import java.util.Random;

public class Obstacle extends BoardObject {
    Random random;

    public Obstacle(int max_width, int max_height) {
        random = new Random();
        randomWidth(max_width);
        randomHeight(max_height);
        positionX = random.nextInt(((Constants.SCREEN_WIDTH / 2) / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
        positionY = random.nextInt((Constants.SCREEN_HEIGHT / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
    }

    public void draw(Graphics g) {
        loadImage("src/resources/obstacle.png");
        g.drawImage(image, positionX, positionY, width, height, null);
//        g.setColor(Color.white);
//        g.fillRect(positionX, positionY, width, height);
    }
    
    private void randomWidth(int max_size) {
        random = new Random();
        width = (random.nextInt(1, max_size)) * Constants.UNIT_SIZE;
    }

    private void randomHeight(int max_size) {
        random = new Random();
        height = (random.nextInt(1, max_size)) * Constants.UNIT_SIZE;
    }


}
