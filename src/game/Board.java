package game;

import javax.swing.*;

public class Board extends JFrame {

    private Shape[] shapes = new Shape[7];

    public Board() {
        //defining all the shapes
        shapes[0] = new Shape(new int[][]{{0,0},{0,1},{0,2},{0,3}}); //|
        shapes[1] = new Shape(new int[][]{{0,0},{1,0},{2,0},{1,1}}); //T
        shapes[2] = new Shape(new int[][]{{0,0},{0,1},{0,2},{1,0}}); //L
        shapes[3] = new Shape(new int[][]{{0,0},{0,1},{0,2},{1,2}}); //upside L
        shapes[4] = new Shape(new int[][]{{0,0},{1,0},{1,1},{2,1}}); //zigzag 1
        shapes[5] = new Shape(new int[][]{{0,0},{0,1},{1,1},{1,2}}); //zigzag 2
        shapes[6] = new Shape(new int[][]{{0,0},{0,1},{1,0},{1,1}}); //square
    }

}
