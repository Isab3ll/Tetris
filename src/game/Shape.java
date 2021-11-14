package game;

import java.awt.*;
import java.util.Random;
import static game.Board.scale;

public class Shape {

    public Color color;
    public int[][] cords; //to rotate
    public int x; //to move left and right
    public int y; //to move down
    public int[][] position = new int[4][2];;

    public int[][] getCords() {
        return cords;
    }

    public int[][] getPosition() {
        return position;
    }

    public Shape(int[][] cords) {
        this.cords = cords;
    }

    /**
     * Represents single block on the board.
     */
    public Shape() {
        Random r = new Random();

        this.cords = types[r.nextInt(7)];
        //this.cords = types[0]; for debug

        this.color = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
        this.x = 10*scale/2;
        this.y = scale;
        updatePosition();
    }

    /**
     * All possible shapes of blocks.
     */
    public int[][][] types = {
            new int[][]{{0,-2},{0,-1},{0,0},{0,1}}, //|
            new int[][]{{0,0},{-1,0},{1,0},{0,1}}, //T
            new int[][]{{0,0},{1,0},{-1,0},{-1,1}}, //L
            new int[][]{{0,0},{1,0},{-1,0},{1,1}}, //upside L
            new int[][]{{0,0},{-1,0},{0,1},{1,1}}, //zigzag 1
            new int[][]{{0,0},{0,-1},{1,0},{1,1}}, //zigzag 2
            new int[][]{{0,0},{0,1},{1,0},{1,1}}, //square
    };

    /**
     * Rotates the block in the left direction.
     */
    public void rotateLeft() {
        int[][] afterRotation = new int[4][2];
        boolean correct = true;
        for(int i=0; i<cords.length; i++) {
            afterRotation[i] = rotateVertex(cords[i],0);
            if(afterRotation[i][0]*scale + x < scale ||
                    afterRotation[i][1]*scale + y > BoardGraphics.width-2*scale) {
                correct = false;
                break;
            }
        }
        if(correct) {
            cords = afterRotation;
            updatePosition();
        }
    }

    /**
     * Rotates the block in the right direction.
     */
    public void rotateRight() {
        int[][] afterRotation = new int[4][2];
        boolean correct = true;
        for(int i=0; i<cords.length; i++) {
            afterRotation[i] = rotateVertex(cords[i],1);
            if(afterRotation[i][0]*scale + x < scale ||
                    afterRotation[i][1]*scale + y > BoardGraphics.width-2*scale) {
                correct = false;
                break;
            }
        }
        if(correct) {
            cords = afterRotation;
            updatePosition();
        }
    }

    /**
     * Moves the block left till the edge.
     */
    public void moveLeft() {
        int leftEdge = BoardGraphics.width+scale;
        for(int i=0; i<4; i++) {
            if(this.position[i][0]*scale < leftEdge) {
                leftEdge = this.position[i][0]*scale;
            }
        }
        if(leftEdge>scale) {
            this.x = x - scale;
        }
        updatePosition();
    }

    /**
     * Moves the block right till the edge.
     */
    public void moveRight() {
        int rightEdge = 0;
        for(int i=0; i<4; i++) {
            if(this.position[i][0]*scale > rightEdge) {
                rightEdge = this.position[i][0]*scale ;
            }
        }
        if(rightEdge<BoardGraphics.width-2*scale) {
            this.x = x + scale;
        }
        updatePosition();
    }

    /**
     * Moves the block down.
     */
    public void down() {
        this.y = y + 5;
        updatePosition();
    }

    /**
     * Saves current position of the block.
     */
    private void updatePosition() {
        for(int i=0; i<4; i++) {
            this.position[i][0] = cords[i][0] + x/scale;
            this.position[i][1] = cords[i][1] + y/scale;
        }
    }

    /**
     * Rotates a single vertex about the origin (0,0).
     * @param v coordinates before the rotation
     * @param d direction - 0 if left, 1 if right
     * @return coordinates after the rotation
     */
    private int[] rotateVertex(int[] v, int d) {
        int[][] mV = new int[3][1]; //matrix with original v cords
        mV[0][0] = v[0];
        mV[1][0] = v[1];
        mV[2][0] = 0;
        int[][] mW = new int[3][1]; //matrix with cords after rotation

        double alpha = 0; //setting the rotation angle
        if(d==0) alpha = Math.PI/2;
        else if(d==1) alpha = - Math.PI/2;
        else throw new IllegalArgumentException("Direction not allowed");

        double[][] t = new double[3][3]; //matrix of the transition
        t[0][0] = Math.cos(alpha);
        t[1][0] = Math.sin(alpha);
        t[2][0] = 0;
        t[0][1] = - Math.sin(alpha);
        t[1][1] = Math.cos(alpha);
        t[2][1] = 0;
        t[0][2] = 0;
        t[1][2] = 0;
        t[2][2] = 1;

        for(int i=0; i<3; i++) { //multiplying the matrix (w=t*v)
            double r = t[i][0]*mV[0][0] + t[i][1]*mV[1][0] + t[i][2]*mV[2][0];
            r = Math.round(r*1000);
            r = r/1000;
            mW[i][0] = (int) r;
        }
        int[] w = new int[2];
        w[0] = mW[0][0];
        w[1] = mW[1][0];
        return w;
    }
}
