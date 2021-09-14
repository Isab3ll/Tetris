package game;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JFrame {

    BoardGraphics graphics = new BoardGraphics();

    public Board() {
        this.setSize(295,500);
        this.setTitle("Best TETRIS Ever");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch(keyCode) {
                    case KeyEvent.VK_LEFT -> graphics.move(0);
                    case KeyEvent.VK_RIGHT -> graphics.move(1);
                    case KeyEvent.VK_UP -> graphics.move(2);
                    case KeyEvent.VK_DOWN -> graphics.move(3);
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        this.add(graphics);
        this.setVisible(true);
    }

}
