package Objects;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ObstacleGenerator {
    private List<Obstacle> obstacles;

    public ObstacleGenerator(){
        obstacles = new ArrayList<Obstacle>();
        generateObstacles();
    }
    private void generateObstacles(){
        Obstacle tmp = new Obstacle(10,3);
        obstacles.add(tmp);
        tmp = new Obstacle(3,10);
        obstacles.add(tmp);
        tmp = new Obstacle(3,10);
        obstacles.add(tmp);
    }
    public void drawObstacles(Graphics g){
        for(Obstacle obstacle: obstacles) obstacle.draw(g);
    }
    public List<Obstacle> getObstacles(){return obstacles;}
}
