package game;

import java.awt.*;
import java.util.Random;

public class Shape {

    public int[][] cords;
    public Color color;
    public int x;
    public int y;

    public int[][] getCords() {
        return cords;
    }

    public Shape(int[][] cords) {
        this.cords = cords;
        Random r = new Random();
        this.color = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
        this.x = 140;
        this.y = 0;
    }

    public void rotateLeft() {
        for(int i=0; i<cords.length; i++) {
            cords[i] = rotateVertex(cords[i],0);
        }
    }

    public void rotateRight() {
        for(int i=0; i<cords.length; i++) {
            cords[i] = rotateVertex(cords[i],1);
        }
    }

    public void moveLeft() {
        this.x = x - 10;
    }

    public void moveRight() {
        this.x = x + 10;
    }

    public void down() {
        this.y = y + 10;
    }

    /**
     * Rotates a single vertex about the origin (0,0)
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
