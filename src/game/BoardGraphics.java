package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import static game.Board.scale;

public class BoardGraphics extends JPanel implements ActionListener {

    /**
     * Sets the game speed (bigger number equals slower game).
     */
    static int speed = 2;

    protected static int width;
    protected static int height;

    Timer timer = new Timer(1, this);
    int countdown = speed;
    ArrayList<Shape> onBoard = new ArrayList<>();
    static boolean[][] full;
    Shape currentShape;
    int points = 0;

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
        full = new boolean[width/scale][height/scale];
        currentShape = new Shape();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        Graphics2D g2D = (Graphics2D) g;
        width = getWidth();
        height = getHeight();

        /*
        g2D.setFont(new Font("Helvetica", Font.PLAIN,30));
        g2D.setColor(Color.YELLOW);
        g2D.drawString("Best",30,30);
        g2D.setColor(Color.MAGENTA);
        g2D.drawString("TETRIS",85,50);
        g2D.setColor(Color.GREEN);
        g2D.drawString("Ever",200,50);
         */

        g2D.setColor(Color.WHITE);
        g2D.fillRect(0, getHeight()-scale+11, getWidth()*scale, scale);
        g2D.fillRect(0, 0, scale-5, getHeight()*scale);
        g2D.fillRect(getWidth()-scale-4, 0, scale+4, getHeight()*scale);
        paintShape(g2D, currentShape);
        for(Shape shape: onBoard) {
            paintShape(g2D, shape);
        }
        //todo box of points

        boolean onFloor = false;
        int lowest = 0;
        for(int i=0; i<4; i++) {
            if(currentShape.getCords()[i][1]*scale + currentShape.y > lowest) {
                lowest = currentShape.getCords()[i][1]*scale + currentShape.y;
            }
        }
        if(lowest>height-2*scale) {
            onFloor = true;
            onBoard.add(currentShape);
            currentShape = new Shape();
        }

        boolean stop = false;
        if(!onFloor) {
            for(Shape shape: onBoard) {
                for(int i=0; i<4; i++) {
                    for(int j=0; j<4; j++) {
                        if (currentShape.getCords()[i][0]*scale + currentShape.x == shape.getCords()[j][0]*scale + shape.x
                                && currentShape.getCords()[i][1]*scale + currentShape.y + scale == shape.getCords()[j][1]*scale + shape.y) {
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

        //saving current cords of all blocks on board
        for(int i=0; i<full.length; i++) {
            for(int j=0; j<full[0].length; j++) {
                full[i][j] = false;
            }
        }
        /*
        for(Shape shape: onBoard) {
            for(int k=0; k<4; k++) {
                int x = shape.getCords()[k][0] + shape.x%scale -3;
                int y = shape.getCords()[k][1] + shape.y%scale -3;
                full[x][y] = true;
            }
        }
        checkLines();

         */
    }

    /**
     * Paints a single tetris block on board.
     * @param g2D graphics
     * @param shape block to paint
     */
    private void paintShape(Graphics2D g2D, Shape shape) {
        for(int i=0; i<4; i++) {
            g2D.setColor(shape.color.brighter());
            g2D.fillRect(shape.getCords()[i][0]*scale +shape.x, shape.getCords()[i][1]*scale +shape.y, scale,scale);
            g2D.setColor(Color.BLACK);
            g2D.drawRect(shape.getCords()[i][0]*scale +shape.x, shape.getCords()[i][1]*scale +shape.y, scale,scale);
        }
    }

    /**
     * Removes full lines and counts points.
     */
    public static void checkLines() {
        for(int i=0; i<full.length; i++) {
            boolean fullRow = true;
            for(int j=0; j<full[0].length; j++) {
                if(!full[i][j]) {
                    fullRow = false;
                    break;
                }
            }
            if(fullRow) {
                System.out.println("FULL");

                //todo removing full row
                //todo counting points
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        countdown--;
        if(countdown == 0) {
            countdown = speed;
            currentShape.down();
        }
        repaint();
    }
}
