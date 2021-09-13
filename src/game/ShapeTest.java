package game;

import static org.junit.jupiter.api.Assertions.*;

class ShapeTest {

    /**
     * Testing rotation of a simple block
     */

    @org.junit.jupiter.api.Test
    void rotateLeft1() {
        int[][] cordsBefore = {{0,0},{0,1}};
        int[][] cordsAfter = {{0,0},{-1,0}};
        Shape shape = new Shape(cordsBefore);
        shape.rotateLeft();
        assertArrayEquals(shape.cords,cordsAfter);
    }

    @org.junit.jupiter.api.Test
    void rotateRight1() {
        int[][] cordsBefore = {{0,0},{0,1}};
        int[][] cordsAfter = {{0,0},{1,0}};
        Shape shape = new Shape(cordsBefore);
        shape.rotateRight();
        assertArrayEquals(shape.cords,cordsAfter);
    }

    @org.junit.jupiter.api.Test
    void rotateLeft2() {
        int[][] cordsBefore = {{0,0},{0,1},{1,1}};
        int[][] cordsAfter = {{0,0},{-1,0},{-1,1}};
        Shape shape = new Shape(cordsBefore);
        shape.rotateLeft();
        assertArrayEquals(shape.cords,cordsAfter);
    }

    @org.junit.jupiter.api.Test
    void rotateRight2() {
        int[][] cordsBefore = {{0,0},{0,1},{1,1}};
        int[][] cordsAfter = {{0,0},{1,0},{1,-1}};
        Shape shape = new Shape(cordsBefore);
        shape.rotateRight();
        assertArrayEquals(shape.cords,cordsAfter);
    }
}