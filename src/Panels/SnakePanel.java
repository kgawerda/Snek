package Panels;

import Constants.Constants;
import Objects.*;
import Threads.ThreadPool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;


public class SnakePanel extends JPanel implements ActionListener {
    private boolean running = true;
    private boolean runningAi = true;
    private final ThreadPool threadPool;
    private final Apple apple;
    private final Mouse mouse;
    private final PlayerSnake playerSnake;
    private final ObstacleGenerator obstacleGenerator;
    private final AiSnake aiSnake;
    Timer timer;


    public SnakePanel() {
        apple = new Apple();
        mouse = new Mouse();
        obstacleGenerator = new ObstacleGenerator();
        playerSnake = new PlayerSnake();
        aiSnake = new AiSnake();
        threadPool = new ThreadPool(4);
        threadPool.runTask(obstacleGenerator.createRunnable());
        apple.newApple(obstacleGenerator.getObstacles());
        mouse.newMouse(obstacleGenerator.getObstacles());

        timer = new Timer(Constants.DELAY, this);
        timer.start();
        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(playerSnake.getKeyAdapter());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            threadPool.runTask(playerSnake);
            if (runningAi) threadPool.runTask(aiSnake.createRunnable(apple, obstacleGenerator.getObstacles(), playerSnake));
            apple.checkCollision(aiSnake, obstacleGenerator.getObstacles());
            mouse.checkCollision(aiSnake, obstacleGenerator.getObstacles());
            apple.checkCollision(playerSnake, obstacleGenerator.getObstacles());
            mouse.checkCollision(playerSnake, obstacleGenerator.getObstacles());
            threadPool.runTask(mouse.createRunnable(playerSnake.getHeadX(), playerSnake.getHeadY(), obstacleGenerator.getObstacles()));
            running = playerSnake.checkCollisionsBoard();
            //runningAi=aiSnake.checkCollisionsBoard();
            this.checkObstacleCollision();
            this.checkAiObstacleCollision();
        } else timer.stop();
        repaint();
    }

    public void draw(Graphics g) {
        if (running) {
            apple.draw(g);
            mouse.draw(g);
            obstacleGenerator.drawObstacles(g);
            playerSnake.draw(g);
            if (runningAi) aiSnake.draw(g);
            g.setColor(Color.red);
            g.setFont(new Font("Serif", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + (playerSnake.getSnakeLength() - Constants.INITIAL_SNAKE_LENGTH),
                    (Constants.SCREEN_WIDTH - metrics.stringWidth("Score: " + (playerSnake.getSnakeLength() - Constants.INITIAL_SNAKE_LENGTH))) / 2, g.getFont().getSize());
        } else gameOver(g);

    }

    public void saveDataToFile(int score) {
        String directoryName = "Scores";
        String fileName = new SimpleDateFormat("ddMMyyyy").format(new Date());
        String formattedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        String data = "DateOfGame: " + formattedDate + ", Score: " + score + "\n";

        try {
            File directory = new File(directoryName);
            if (!directory.exists()) {
                boolean directoryCreated = directory.mkdir();
                if (directoryCreated) {
                    System.out.println("Scores directory created.");
                } else {
                    System.out.println("Failed to create Scores directory.");
                }
            }

            File file = new File(directory, fileName + ".txt");
            FileWriter writer = new FileWriter(file, true);
            writer.write(data);
            writer.close();
            System.out.println("Data saved to file: " + fileName + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Serif", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + (playerSnake.getSnakeLength() - Constants.INITIAL_SNAKE_LENGTH), (Constants.SCREEN_WIDTH - metrics1.stringWidth("Score: " + (playerSnake.getSnakeLength() - Constants.INITIAL_SNAKE_LENGTH))) / 2, g.getFont().getSize());
        g.setColor(Color.red);
        g.setFont(new Font("Serif", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (Constants.SCREEN_WIDTH - metrics.stringWidth("GAME OVER")) / 2, Constants.SCREEN_HEIGHT / 2);

        // TODO : save data to file and add restart button
        // append data to file, do not overwrite it
        // save it by datetime

        int score = playerSnake.getSnakeLength() - Constants.INITIAL_SNAKE_LENGTH;


        saveDataToFile(score);
    }

    public void checkObstacleCollision() {
        Rectangle snake = playerSnake.getBounds();
        for (Obstacle obstacle : obstacleGenerator.getObstacles()) {
            Rectangle obstacleRectangle = obstacle.getBounds();
            if (snake.intersects(obstacleRectangle)) running = false;
        }
    }

    public void checkAiObstacleCollision() {
        Rectangle snake = aiSnake.getBounds();

        for (Obstacle obstacle : obstacleGenerator.getObstacles()) {
            Rectangle obstacleRectangle = obstacle.getBounds();
            if (snake.intersects(obstacleRectangle)) runningAi = false;
        }
    }

    // TODO: check usage of this method
    public void addSnakeObjectPropertyChangeListener(PropertyChangeListener listener) {
        playerSnake.addPropertyChangeListener(listener);
    }
}
