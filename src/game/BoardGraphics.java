package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BoardGraphics extends JPanel implements ActionListener {

    Timer timer = new Timer(1, this);
    int countdown = 20;
    ArrayList<Shape> onBoard = new ArrayList<>();
    Shape currentShape;

    /**
     * Moves and rotates blocks.
     * @param direction type on a move
     */
    public void move(int direction) {
        switch(direction) {
            case 0 -> currentShape.moveLeft();
            case 1 -> currentShape.moveRight();
            case 2 -> currentShape.rotateLeft();
            case 3 -> currentShape.rotateRight();
        }
    }

    public BoardGraphics() {
        timer.start();
        currentShape = new Shape();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setFont(new Font("Helvetica", Font.PLAIN,30));
        g2D.setColor(Color.YELLOW);
        g2D.drawString("Best",20,50);
        g2D.setColor(Color.MAGENTA);
        g2D.drawString("TETRIS",85,50);
        g2D.setColor(Color.GREEN);
        g2D.drawString("Ever",200,50);
        g2D.setColor(Color.WHITE);
        g2D.fillRect(0,450,getWidth(),10);
        g2D.fillRect(0,80,10,380);
        g2D.fillRect(270,80,10,380);
        paintShape(g2D, currentShape);
        for(Shape shape: onBoard) {
            paintShape(g2D, shape);
        }

        boolean onFloor = false;
        int lowest = 0;
        for(int i=0; i<4; i++) {
            if(currentShape.getCords()[i][1]*10 + currentShape.y > lowest) {
                lowest = currentShape.getCords()[i][1]*10 + currentShape.y;
            }
        }
        if(lowest==440) {
            onFloor = true;
            onBoard.add(currentShape);
            currentShape = new Shape();
        }

        boolean stop = false;
        if(!onFloor) {
            for(Shape shape: onBoard) {
                for(int i=0; i<4; i++) {
                    for(int j=0; j<4; j++) {
                        if (currentShape.getCords()[i][0]*10 + currentShape.x == shape.getCords()[j][0]*10 + shape.x
                                && currentShape.getCords()[i][1]*10 + currentShape.y + 10 == shape.getCords()[j][1]*10 + shape.y) {
                            onBoard.add(currentShape);
                            currentShape = new Shape();
                            stop = true;
                            break;
                        }
                    }
                    if(stop) break;
                }
                if(stop) break;
            }
        }
    }

    /**
     * Paints a single tetris block on board.
     * @param g2D graphics
     * @param shape block to paint
     */
    private void paintShape(Graphics2D g2D, Shape shape) {
        for(int i=0; i<4; i++) {
            g2D.setColor(shape.color.brighter());
            g2D.fillRect(shape.getCords()[i][0]*10 +shape.x, shape.getCords()[i][1]*10 +shape.y, 10,10);
            g2D.setColor(Color.BLACK);
            g2D.drawRect(shape.getCords()[i][0]*10 +shape.x, shape.getCords()[i][1]*10 +shape.y, 10,10);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        countdown--;
        if(countdown == 0) {
            countdown = 5; //sets the game speed
            currentShape.down();
        }
        repaint();
    }
}
