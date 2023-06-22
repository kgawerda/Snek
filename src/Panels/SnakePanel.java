package Panels;
import Constants.Constants;
import Objects.*;
import Threads.ThreadPool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class SnakePanel extends JPanel implements ActionListener {
    private boolean running = true;
    private final ThreadPool threadPool;
    private final Apple apple;
    private final Mouse mouse;
    private final PlayerSnake playerSnake;
    private final ObstacleGenerator obstacleGenerator;
    Timer timer;



    public SnakePanel(){
        apple = new Apple();
        mouse = new Mouse();
        obstacleGenerator = new ObstacleGenerator();
        playerSnake = new PlayerSnake();
        threadPool = new ThreadPool(4);
        threadPool.runTask(obstacleGenerator.createRunnable());
        apple.newApple(obstacleGenerator.getObstacles());
        mouse.newMouse(obstacleGenerator.getObstacles());

        timer = new Timer(Constants.DELAY,this);
        timer.start();
        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(playerSnake.getKeyAdapter());
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(running){
            threadPool.runTask(playerSnake);
            apple.checkCollision(playerSnake ,obstacleGenerator.getObstacles());
            mouse.checkCollision(playerSnake, obstacleGenerator.getObstacles());
            threadPool.runTask(mouse.createRunnable(playerSnake.getHeadX(),playerSnake.getHeadY(),obstacleGenerator.getObstacles()));
            running=playerSnake.checkCollisionsBoard();
            this.checkObstacleCollision();
        }
        else timer.stop();
        repaint();
    }

    public void draw(Graphics g){
        if(running){
            apple.draw(g);
            mouse.draw(g);
            obstacleGenerator.drawObstacles(g);
            playerSnake.draw(g);
            g.setColor(Color.red);
            g.setFont(new Font("Serif",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+(playerSnake.getSnakeLength()-Constants.INITIAL_SNAKE_LENGTH),(Constants.SCREEN_WIDTH-metrics.stringWidth("Score: "+(playerSnake.getSnakeLength()-Constants.INITIAL_SNAKE_LENGTH)))/2,g.getFont().getSize());
        }
        else gameOver(g);

    }


    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Serif",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+(playerSnake.getSnakeLength()-Constants.INITIAL_SNAKE_LENGTH),(Constants.SCREEN_WIDTH-metrics1.stringWidth("Score: "+(playerSnake.getSnakeLength()-Constants.INITIAL_SNAKE_LENGTH)))/2,g.getFont().getSize());
        g.setColor(Color.red);
        g.setFont(new Font("Serif",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER",(Constants.SCREEN_WIDTH-metrics.stringWidth("GAME OVER"))/2,Constants.SCREEN_HEIGHT/2);

    }
    public void checkObstacleCollision(){
        Rectangle snake = playerSnake.getBounds();
        for(Obstacle obstacle: obstacleGenerator.getObstacles()){
            Rectangle obstacleRectangle = obstacle.getBounds();
            if(snake.intersects(obstacleRectangle)) running=false;
        }
    }
    public void addSnakeObjectPropertyChangeListener(PropertyChangeListener listener)
    {
        playerSnake.addPropertyChangeListener(listener);
    }
}
