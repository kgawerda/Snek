package Frames;

import Panels.SnakePanel;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        // TODO :here we should add 'restart' button 
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
