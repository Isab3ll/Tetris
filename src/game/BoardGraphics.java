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
    static int speed = 30;

    protected static int width;
    protected static int height;

    private Timer timer = new Timer(1, this);
    private int countdown = speed;
    private static ArrayList<Shape> onBoard = new ArrayList<>();
    private static boolean[][] full;
    private static Shape currentShape;
    private int points = 0;

    public static ArrayList<Shape> getOnBoard() {
        return onBoard;
    }

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
            case 4 -> currentShape.down();
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
        if(gameOver()) {
            showSummary(g);
        }
    }

    /**
     * Paints a single tetris block on board.
     * @param g2D graphics
     * @param shape block to paint
     */
    private void paintShape(Graphics2D g2D, Shape shape) {
        for(int i=0; i<shape.getPosition().length; i++) {
            g2D.setColor(shape.getColor().brighter());
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
        g2D.fillRect(0, height-scale*2/3, width*scale*2/3, scale);
        g2D.fillRect(0, 0, scale, height*scale);
        g2D.fillRect(width-scale+2, 0, scale, height*scale);
        paintShape(g2D, currentShape);
        for(Shape shape: onBoard) {
            paintShape(g2D, shape);
        }

        g2D.setColor(Color.RED);
        g2D.setFont(new Font("Console", Font.PLAIN, scale*2/3));
        g2D.drawString("Points: "+points, scale*8, scale);
    }

    /**
     * Checks if the current block is on the floor and adds new block if needed.
     * @return true if the block is on floor
     */
    protected static boolean blockOnFloor() {
        int lowest = 0;
        for(int i=0; i<currentShape.getPosition().length; i++) {
            if(currentShape.getPosition()[i][1]*scale > lowest) {
                lowest = currentShape.getPosition()[i][1]*scale;
            }
        }
        if(lowest > height-2*scale) {
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
    protected static boolean blockOnBlock() {
        for(Shape shape : onBoard) {
            for(int i=0; i<currentShape.getPosition().length; i++) {
                for(int j=0; j<shape.getPosition().length; j++) {
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
            for(int k=0; k<shape.getPosition().length; k++) {
                int x = shape.getPosition()[k][0];
                int y = shape.getPosition()[k][1];
                if(y>=-1) {
                    full[y+1][x-1] = true;
                }
            }
        }
    }

    /**
     * Removes full lines and counts points.
     */
    public void checkLines() {
        int combo = 0;
        for(int i=0; i<full.length; i++) {
            boolean fullRow = true;
            for(int j=0; j<full[0].length; j++) {
                if(!full[i][j]) {
                    fullRow = false;
                    break;
                }
            }
            if(fullRow) {
                removeRow(i);
                saveBoard();
                combo++;
            }
        }
        if(combo > 0) {
            if(combo == 4) points += combo*2;
            else points += combo;
            speed -= 1;
        }
    }

    /**
     * Removes given row from the board.
     * @param rowNumber number of the full row to remove
     */
    private void removeRow(int rowNumber) {
        for(int k=0; k<onBoard.size(); k++) {
            Shape shape = onBoard.get(k);
            ArrayList<int[]> correctCords = new ArrayList<>();
            for(int i=0; i<shape.getPosition().length; i++){
                int x = shape.getPosition()[i][0];
                int y = shape.getPosition()[i][1];
                if(y+1 != rowNumber) {
                    correctCords.add(new int[]{x,y});
                }
            }
            int cellsLeft = correctCords.size();
            if(cellsLeft != shape.getPosition().length) {
                int[][] newPosition = new int[cellsLeft][2];
                for(int i=0; i<cellsLeft; i++) {
                    newPosition[i] = correctCords.get(i);
                }
                shape.setPosition(newPosition);
                onBoard.set(k, shape);
            }
        }
        rowsDown(rowNumber);
    }

    /**
     * Moves all the left rows down after removing full row.
     * @param fromRow number of previously removed row
     */
    private void rowsDown(int fromRow) {
        for(int k=0; k<onBoard.size(); k++) {
            Shape shape = onBoard.get(k);
            int[][] newPosition = shape.getPosition();
            for(int i=0; i<newPosition.length; i++) {
                if(newPosition[i][1] < fromRow) {
                    newPosition[i][1] += 1;
                }
            }
            shape.setPosition(newPosition);
            onBoard.set(k, shape);
        }
    }

    /**
     * Checks if player lost the game.
     * @return true if the game is over
     */
    private boolean gameOver() {
        for(int x=0; x<full[0].length; x++) {
            if(full[0][x]) {
                timer.stop();
                return true;
            }
        }
        return false;
    }

    /**
     * Shows summary after the game is over.
     * @param g graphics
     */
    private void showSummary(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(Color.RED);
        g2D.setFont(new Font("Console", Font.PLAIN, scale));
        g2D.drawString("GAME OVER", scale*3, scale*5);

        g2D.setColor(Color.WHITE);
        g2D.setFont(new Font("Console", Font.PLAIN, scale*2/3));
        g2D.drawString("Your score: "+points, scale*4, scale*6);

        //todo ranking

        g2D.dispose();
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
