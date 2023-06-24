package Frames;

import Panels.SnakePanel;

import javax.swing.*;

/**
 * The GameFrame class represents the main frame/window for the Snake game.
 * It extends JFrame to create and manage the game window.
 */
public class GameFrame extends JFrame {

    /**
     * Constructs a new GameFrame object.
     * Initializes the game frame and adds a SnakePanel to it.
     */
    public GameFrame() {
        SnakePanel panel = new SnakePanel();
        this.add(panel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
