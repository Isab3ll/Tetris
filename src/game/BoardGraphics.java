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
        currentShape = new Shape();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        Graphics2D g2D = (Graphics2D) g;
        width = getWidth();
        height = getHeight();
        full = new boolean[22][10];

        paintBoard(g2D);
        boolean blockIsSet;
        if(!blockOnFloor()) {
            blockIsSet = blockOnBlock();
        }
        else blockIsSet = true;

        if(blockIsSet) {
            saveBoard();
            checkLines();
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
            g2D.fillRect(shape.getPosition()[i][0]*scale, shape.getPosition()[i][1]*scale, scale, scale);
            g2D.setColor(Color.BLACK);
            g2D.drawRect(shape.getPosition()[i][0]*scale, shape.getPosition()[i][1]*scale, scale, scale);
        }
    }

    /**
     * Paints the game board.
     * @param g2D graphics
     */
    private void paintBoard(Graphics2D g2D) {
        g2D.setColor(Color.WHITE);
        g2D.fillRect(0, getHeight()-scale+10, getWidth()*scale-10, scale);
        g2D.fillRect(0, 0, scale, getHeight()*scale);
        g2D.fillRect(getWidth()-scale+1, 0, scale, getHeight()*scale);
        paintShape(g2D, currentShape);
        for(Shape shape: onBoard) {
            paintShape(g2D, shape);
        }
        //todo box of points
    }

    /**
     * Checks if the current block is on the floor and adds new block if needed.
     * @return true if the block is on floor
     */
    private boolean blockOnFloor() {
        int lowest = 0;
        for(int i=0; i<4; i++) {
            if(currentShape.getPosition()[i][1]*scale > lowest) {
                lowest = currentShape.getPosition()[i][1]*scale;
            }
        }
        if(lowest>height-2*scale) {
            onBoard.add(currentShape);
            currentShape = new Shape();
            return true;
        }
        return false;
    }

    /**
     * Checks if the current block is on another block and adds new block if needed.
     * @return true if the block is on another block
     */
    private boolean blockOnBlock() {
        for(Shape shape : onBoard) {
            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 4; j++) {
                    if (currentShape.getPosition()[i][0] * scale == shape.getPosition()[j][0] * scale
                            && currentShape.getPosition()[i][1] * scale + scale == shape.getPosition()[j][1] * scale) {
                        onBoard.add(currentShape);
                        currentShape = new Shape();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Saves current position of all the blocks on board.
     */
    private void saveBoard() {
        for(int i=0; i<full.length; i++) {
            for(int j=0; j<full[0].length; j++) {
                full[i][j] = false;
            }
        }
        for(Shape shape: onBoard) {
            for(int k=0; k<4; k++) {
                int x = shape.getPosition()[k][0];
                int y = shape.getPosition()[k][1];
                full[y+1][x-1] = true;
            }
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
