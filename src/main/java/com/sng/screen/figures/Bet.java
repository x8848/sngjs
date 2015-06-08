package com.sng.screen.figures;

public class Bet {

    public static int commaWidth = 5;
    public static int numberWidth = 9;
    public static int numberHeight = 12;

    private int X;
    private int Y;

    public Bet(int x, int y) {
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