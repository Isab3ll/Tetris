package game;

import javax.swing.*;

public class Board extends JFrame {

    private Shape[] shapes = new Shape[7];

    public Board() {
        shapes[0] = new Shape(new int[][]{{0,0},{0,1},{0,2},{0,3}}); //|
        shapes[1] = new Shape(new int[][]{{0,0},{1,0},{2,0},{1,1}}); //T
        shapes[2] = new Shape(new int[][]{{0,0},{0,1},{0,2},{1,0}}); //L
        shapes[3] = new Shape(new int[][]{{0,0},{0,1},{0,2},{1,2}}); //upside L
        shapes[4] = new Shape(new int[][]{{0,0},{1,0},{1,1},{2,1}}); //zigzag 1
        shapes[5] = new Shape(new int[][]{{0,0},{0,1},{1,1},{1,2}}); //zigzag 2
        shapes[6] = new Shape(new int[][]{{0,0},{0,1},{1,0},{1,1}}); //square
    }

    private class Shape {

        /**
         * Table of coordinates for the shape
         */
        public int[][] cords;

        public Shape(int[][] cords) {
            this.cords = cords;
        }

        public void rotateLeft() {
            //for(int i=0; i<void)
        }

        public void rotateRight() {
            //
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
            mV[2][0] = v[2];
            int[][] mW = new int[3][1]; //matrix with cords after rotation

            //setting the rotation angle
            double alpha = 0;
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

            //multiplying the matrix (w=t*v)
            for(int i=0; i<3; i++) {
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
}
