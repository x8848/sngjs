package com.sng.screen.figures;

public class Button {
    public static final int width = 40;
    public static final int height = 6;

    private int X;
    private int Y;

    public Button(int x, int y) {
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