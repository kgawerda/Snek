package Objects;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The ObstacleGenerator class is responsible for generating and managing obstacles in the game.
 * It provides methods to generate obstacles, draw them on the graphics object, and retrieve the list of obstacles.
 */
public class ObstacleGenerator {
    private final List<Obstacle> obstacles;

    /**
     * Creates a new Runnable object that generates obstacles.
     *
     * @return a Runnable object that generates obstacles
     */
    public Runnable createRunnable() {
        return this::generateObstacles;
    }

    /**
     * Constructs a new ObstacleGenerator object.
     */
    public ObstacleGenerator() {
        obstacles = new ArrayList<>();
    }

    /**
     * Generates obstacles and adds them to the list of obstacles.
     */
    private void generateObstacles() {
        Obstacle tmp = new Obstacle(10, 3);
        obstacles.add(tmp);
        tmp = new Obstacle(3, 10);
        obstacles.add(tmp);
        tmp = new Obstacle(3, 10);
        obstacles.add(tmp);
    }

    /**
     * Draws the obstacles on the graphics object.
     *
     * @param g the graphics object to draw on
     */
    public void drawObstacles(Graphics g) {
        for (Obstacle obstacle : obstacles) obstacle.draw(g);
    }

    /**
     * Retrieves the list of obstacles.
     *
     * @return the list of obstacles
     */
    public List<Obstacle> getObstacles() {
        return obstacles;
    }
}
