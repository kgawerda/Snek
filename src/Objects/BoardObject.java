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
    public Rectangle getBounds()
    {
        return new Rectangle(positionX, positionY, width, height);
    }
}