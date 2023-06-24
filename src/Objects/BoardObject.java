package Objects;

import Constants.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * The BoardObject class represents a generic object on the game board.
 * It provides basic functionality and properties for game objects.
 */
public class BoardObject {

    protected int positionX;
    protected int positionY;
    protected Image image;

    /**
     * Loads an image from the specified file and sets it as the object's image.
     *
     * @param filename the path of the image file
     */
    protected void loadImage(String filename) {
        ImageIcon tempImage = new ImageIcon(filename);
        image = tempImage.getImage();
    }

    /**
     * Retrieves the X-coordinate of the object's position.
     *
     * @return the X-coordinate of the object's position
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Retrieves the Y-coordinate of the object's position.
     *
     * @return the Y-coordinate of the object's position
     */
    public int getPositionY() {
        return positionY;
    }

    protected int width = Constants.UNIT_SIZE;
    protected int height = Constants.UNIT_SIZE;

    /**
     * Retrieves the rectangular bounds of the object.
     *
     * @return the rectangular bounds of the object
     */
    public Rectangle getBounds() {
        return new Rectangle(positionX, positionY, width, height);
    }

    /**
     * Retrieves the rectangular bounds of the object's surrounding area.
     * The surrounding area includes a margin around the object.
     *
     * @return the rectangular bounds of the object's surrounding area
     */
    public Rectangle getBoundsSurrounding() {
        return new Rectangle(positionX - Constants.UNIT_SIZE, positionY - Constants.UNIT_SIZE, width + Constants.UNIT_SIZE, height + Constants.UNIT_SIZE);
    }
}
