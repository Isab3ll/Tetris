package game;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JFrame {

    /**
     * Defining all the shapes
     */
    public static Shape[] shapes = {
        new Shape(new int[][]{{0,0},{0,1},{0,2},{0,3}}), //|
        new Shape(new int[][]{{0,0},{1,0},{2,0},{1,1}}), //T
        new Shape(new int[][]{{0,0},{0,1},{0,2},{1,0}}), //L
        new Shape(new int[][]{{0,0},{0,1},{0,2},{1,2}}), //upside L
        new Shape(new int[][]{{0,0},{1,0},{1,1},{2,1}}), //zigzag 1
        new Shape(new int[][]{{0,0},{0,1},{1,1},{1,2}}), //zigzag 2
        new Shape(new int[][]{{0,0},{0,1},{1,0},{1,1}}), //square
    };

    BoardGraphics graphics = new BoardGraphics();

    public Board() {
        this.setSize(300,500);
        this.setLocationRelativeTo(null); //centering
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
