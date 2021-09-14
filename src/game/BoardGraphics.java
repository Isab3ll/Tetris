package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BoardGraphics extends JPanel implements ActionListener {

    Timer timer = new Timer(1, this);
    int countdown = 20; //sets the game speed
    JLabel logo;

    ArrayList<Shape> onBoard = new ArrayList<>();
    Shape currentShape;

    public void move(int direction) {
        switch(direction) {
            case 0 -> currentShape.moveLeft();
            case 1 -> currentShape.moveRight();
            case 2 -> currentShape.rotateLeft();
            case 3 -> currentShape.rotateRight();
        }
    }

    public BoardGraphics() {
        /*
        //todo game logo
        label = new JLabel(new ImageIcon("tetris.jpg"));
        label.setSize(50,300);
        label.setLocation(10,10);
        this.setLayout(null);
        this.add(label);
         */
        timer.start();
        currentShape = new Shape();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(Color.WHITE);
        g2D.fillRect(0,450,290,10);
        g2D.fillRect(0,100,10,360);
        g2D.fillRect(270,100,10,360);
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

        for(Shape shape: onBoard) {
            boolean stop = false;
            if(!onFloor) {
                for(int i=0; i<4; i++) {
                    for(int j=0; j<4; j++) {
                        if (currentShape.getCords()[i][0]*10 + currentShape.x == shape.getCords()[j][0]*10 + shape.x
                                && currentShape.getCords()[i][1]*10 + currentShape.y + 10 == shape.getCords()[j][1]*10 + shape.y) {
                            onBoard.add(currentShape);
                            currentShape = new Shape();
                            stop = true;
                        }
                        if(stop) break;
                    }
                    if(stop) break;
                }
            }
        }

//        todo some additional nice graphics

//        g2D.setColor(Color.MAGENTA);
//        g2D.setStroke(new BasicStroke(20));
//        g2D.drawLine(0,0,200,200);

//        int[] xP = {50,100,150,225};
//        int[] yP = {150,275,200,350};
//        int nP = xP.length;
//        g2D.setColor(Color.CYAN);
//        g2D.drawPolyline(xP,yP,nP);

//        g2D.setFont(new Font("Helvetica",Font.ITALIC,25));
//        g2D.setColor(Color.RED);
//        g2D.drawString("best tetris ever",100,100);

    }

    private void paintShape(Graphics2D g2D, Shape shape) {
        for(int i=0; i<4; i++) {
            g2D.setColor(shape.color);
            g2D.fillRect(shape.getCords()[i][0]*10 +shape.x, shape.getCords()[i][1]*10 +shape.y, 10,10);
            g2D.setColor(Color.BLACK);
            g2D.drawRect(shape.getCords()[i][0]*10 +shape.x, shape.getCords()[i][1]*10 +shape.y, 10,10);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        countdown--;
        if(countdown == 0) {
            countdown = 5;
            currentShape.down();
        }
        repaint();
    }
}
