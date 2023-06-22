package Panels;
import Constants.Constants;
import Objects.Apple;
import Objects.MyKeyAdapter;
import Objects.PlayerSnake;
import Threads.ThreadPool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.Random;

public class SnakePanel extends JPanel implements ActionListener {
    private boolean running = true;
    private final ThreadPool threadPool;
    private final Apple apple;
    private final PlayerSnake playerSnake;
    Timer timer;



    public SnakePanel(){
        apple = new Apple();
        playerSnake = new PlayerSnake();
        threadPool = new ThreadPool(4);

        apple.newApple();
        System.out.println(apple.getPositionX());
        System.out.println(apple.getPositionY());
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
            apple.checkCollisionApple(playerSnake);
            running=playerSnake.checkCollisionsBoard();
        }

        repaint();
    }

    public void draw(Graphics g){
        if(running){
            apple.draw(g);
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
    public void addSnakeObjectPropertyChangeListener(PropertyChangeListener listener)
    {
        playerSnake.addPropertyChangeListener(listener);
    }
}
