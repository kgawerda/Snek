package Objects;

import Constants.Constants;

import java.awt.*;

public class BoardObject {

    protected int positionX;
    protected int positionY;
    public int getPositionX() { return positionX; }
    public int getPositionY() { return positionY; }
    protected int width = Constants.UNIT_SIZE;
    protected int height = Constants.UNIT_SIZE;
    public BoardObject(){}
    protected int randomNumber(int min, int max)
    {
        return (min + (int) (Math.random() * ((max - min) + 1))) * 10;
    }
    public Rectangle getBounds()
    {
        return new Rectangle(positionX, positionY, width, height);
    }
}
