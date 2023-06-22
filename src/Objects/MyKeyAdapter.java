package Objects;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyAdapter extends KeyAdapter {
    private char direction = 'R';
    @Override
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> {
                if (direction != 'R') direction = 'L';
            }
            case KeyEvent.VK_D -> {
                if (direction != 'L') direction = 'R';
            }
            case KeyEvent.VK_W -> {
                if (direction != 'D') direction = 'U';
            }
            case KeyEvent.VK_S -> {
                if (direction != 'U') direction = 'D';
            }
        }
    }
    public char getDirection(){return direction;}
}