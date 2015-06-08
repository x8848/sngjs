package com.sng.screen.figures;

public class Cards {

    private int X;
    private int Y;

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public static final int width = 16;
    public static final int height = 33;

    public Cards(int x, int y) {
        this.X = x;
        this.Y = y;
    }
}