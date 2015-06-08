package com.sng.screen.figures;

public class Stack {

    public static int commaWidth = 5;
    public static int numberWidth = 10;
    public static int numberHeight = 12;

    private int X;
    private int Y;

    public Stack(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}