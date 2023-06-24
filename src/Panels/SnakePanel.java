package Panels;

import Constants.Constants;
import Objects.*;
import Threads.ThreadPool;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;
import java.util.List;


public class SnakePanel extends JPanel implements ActionListener {
    private boolean running = true;
    private boolean runningAi = true;
    private ThreadPool threadPool;
    private Apple apple;
    private Mouse mouse;
    private PlayerSnake playerSnake;
    private ObstacleGenerator obstacleGenerator;
    private AiSnake aiSnake;
    Timer timer;
    private JButton restartButton;
    private JButton scoresButton;
    private JLabel scoresLabel;
    private boolean written = false;
    private long startTime;
    private boolean showScore = false;


    public void init() {
        startTime = System.currentTimeMillis();
        timer = new Timer(Constants.DELAY, this);
        timer.start();
        apple = new Apple();
        mouse = new Mouse();
        obstacleGenerator = new ObstacleGenerator();
        playerSnake = new PlayerSnake();
        aiSnake = new AiSnake();
        threadPool = new ThreadPool(4);
        threadPool.runTask(obstacleGenerator.createRunnable());
        apple.newApple(obstacleGenerator.getObstacles());
        mouse.newMouse(obstacleGenerator.getObstacles());
        this.addKeyListener(playerSnake.getKeyAdapter());
    }

    public SnakePanel() {
        init();
        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setLayout(null);

        initializeRestartButton();
        initializeScoresButton();
        initializeScoresLabel();

        this.add(restartButton);
        this.add(scoresButton);
        this.add(scoresLabel);
    }

    private void initializeRestartButton() {
        restartButton = new JButton("Restart");
        restartButton.setBounds(Constants.SCREEN_WIDTH / 2 - 100, Constants.SCREEN_HEIGHT / 2 + 200, 100, 50);
        restartButton.addActionListener(e -> restartGame());
        restartButton.setVisible(false);
    }

    private void initializeScoresButton() {
        scoresButton = new JButton("Show Scores");
        scoresButton.setBounds(Constants.SCREEN_WIDTH / 2 + 10, Constants.SCREEN_HEIGHT / 2 + 200, 120, 50);
        scoresButton.addActionListener(e -> {
            showScore = true;
            repaint();
            showScores();
            restartButton.setVisible(false);
            scoresButton.setVisible(false);
        });
        scoresButton.setVisible(false);
    }

    private void initializeScoresLabel() {
        scoresLabel = new JLabel();
        scoresLabel.setBounds(Constants.SCREEN_WIDTH / 2 - 200, 100, 400, 400);
        scoresLabel.setForeground(Color.white);
        scoresLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        scoresLabel.setVerticalAlignment(SwingConstants.TOP);
        scoresLabel.setHorizontalAlignment(SwingConstants.LEFT);
        scoresLabel.setVisible(false);
    }

    private void restartGame() {
        init();
        written = false;
        running = true;
        runningAi = true;
        showScore = false;
        restartButton.setVisible(false);
        scoresButton.setVisible(false);
        scoresLabel.setVisible(false);
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
            if (running) running = playerSnake.checkCollisionSnake(aiSnake);
            if (runningAi) {
                runningAi = aiSnake.checkCollisionSnake(playerSnake);
                runningAi = aiSnake.checkCollisionsBoard();
            }
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

    public void saveDataToFile(int score, int aiscore) {
        long currentTime = System.currentTimeMillis();
        long durationTime = (currentTime - startTime);
        String directoryName = "Scores";
        String fileName = "gameScores";
        String formattedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        String data = "DateOfGame: " + formattedDate + ", Player score: " + score + ", AI score: " + aiscore + ", Duration: " + durationTime + "milliseconds" + "\n";

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gameOver(Graphics g) {


        if (!showScore) {
            g.setColor(Color.red);
            g.setFont(new Font("Serif", Font.BOLD, 40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Score: " + (playerSnake.getSnakeLength() - Constants.INITIAL_SNAKE_LENGTH), (Constants.SCREEN_WIDTH - metrics1.stringWidth("Score: " + (playerSnake.getSnakeLength() - Constants.INITIAL_SNAKE_LENGTH))) / 2, g.getFont().getSize());
            g.setColor(Color.red);
            g.setFont(new Font("Serif", Font.BOLD, 75));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("GAME OVER", (Constants.SCREEN_WIDTH - metrics.stringWidth("GAME OVER")) / 2, Constants.SCREEN_HEIGHT / 2);

        }
        restartButton.setVisible(true);
        scoresButton.setVisible(true);
        if (!written) {
            saveDataToFile(playerSnake.getSnakeLength() - Constants.INITIAL_SNAKE_LENGTH, aiSnake.getSnakeLength() - Constants.INITIAL_SNAKE_LENGTH);
            written = true;

        }
    }


    public void checkObstacleCollision() {
        Rectangle snake = playerSnake.getBounds();
        for (Obstacle obstacle : obstacleGenerator.getObstacles()) {
            Rectangle obstacleRect = obstacle.getBounds();
            if (snake.intersects(obstacleRect)) {
                running = false;
            }
        }
    }

    public void checkAiObstacleCollision() {
        Rectangle snake = aiSnake.getBounds();
        for (Obstacle obstacle : obstacleGenerator.getObstacles()) {
            Rectangle obstacleRect = obstacle.getBounds();
            if (snake.intersects(obstacleRect)) {
                runningAi = false;
            }
        }
    }

    private void showScores() {
        File scoreFile = new File("Scores/gameScores.txt");

        try {
            List<String> scores = Files.readAllLines(scoreFile.toPath());
            List<ScoreEntry> scoreEntries = new ArrayList<>();

            for (String score : scores) {
                String[] scoreData = score.split(", ");
                String[] dateOfGame = scoreData[0].split(": ");
                String[] playerScore = scoreData[1].split(": ");
                int playerScoreValue = Integer.parseInt(playerScore[1].trim());
                scoreEntries.add(new ScoreEntry(dateOfGame[1], playerScoreValue));
            }

            scoreEntries.sort(Comparator.comparingInt(ScoreEntry::playerScore).reversed());

            StringBuilder scoresText = new StringBuilder("<html><body>");

            int count = 0;
            for (int i = 0; i < scoreEntries.size() && count < 10; i++) {
                ScoreEntry entry = scoreEntries.get(i);
                scoresText.append(i + 1).append(". DateOfGame: ").append(entry.scoreData()).append(", Player score: ").append(entry.playerScore()).append("<br>");
                count++;
            }
            repaint();
            scoresText.append("</body></html>");
            scoresLabel.setText(scoresText.toString());

            scoresLabel.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    record ScoreEntry(String scoreData, int playerScore) {
    }
}