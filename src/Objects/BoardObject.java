package Objects;

import Constants.Constants;

import javax.swing.*;
import java.awt.*;

public class BoardObject {

    protected int positionX;
    protected int positionY;

    protected Image image;


    protected void loadImage(String filename) {
        ImageIcon tempFruitImage = new ImageIcon(filename);
        image = tempFruitImage.getImage();
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    protected int width = Constants.UNIT_SIZE;
    protected int height = Constants.UNIT_SIZE;

    public BoardObject() {
    }

    public Rectangle getBounds() {
        return new Rectangle(positionX, positionY, width, height);
    }

    public Rectangle getBoundsSurrounding() {
        return new Rectangle(positionX - Constants.UNIT_SIZE, positionY - Constants.UNIT_SIZE, width + Constants.UNIT_SIZE, height + Constants.UNIT_SIZE);
    }
}
